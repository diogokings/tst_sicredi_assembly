package com.sicredi.assembly.session;

import com.sicredi.assembly.common.exception.ObjectNotFoundException;
import com.sicredi.assembly.theme.VoteTheme;
import com.sicredi.assembly.theme.VoteThemeResponseDTO;
import com.sicredi.assembly.theme.VoteThemeService;
import com.sicredi.assembly.theme.VoteThemeStatus;
import com.sicredi.assembly.vote.Vote;
import com.sicredi.assembly.vote.VoteEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class VoteSessionService {

    private final Clock clock;
    private final VoteSessionRepository repository;
    private final VoteThemeService voteThemeService;

    public VoteSessionService(Clock clock, VoteSessionRepository repository, VoteThemeService voteThemeService) {
        this.clock = clock;
        this.repository = repository;
        this.voteThemeService = voteThemeService;
    }

    @Transactional
    public void createVoteSession(Long voteThemeId, CreateVoteSessionRequestDTO dto) {
        canCreateVoteSession(voteThemeId);
        VoteTheme voteTheme = voteThemeService.openVoteSession(voteThemeId);
        LocalDateTime openTime = LocalDateTime.now(clock);
        Long durationInMinutes = (null == dto || null == dto.getDurationInMinutes()) ? 1L : dto.getDurationInMinutes();
        LocalDateTime closeTime = openTime.plusMinutes(durationInMinutes);
        VoteSession entity = new VoteSession(voteTheme, openTime, closeTime);
        repository.saveAndFlush(entity);
    }

    public VoteSession getOpenedVoteSession(Long voteThemeId) {
        VoteSession voteSession = findByVoteTheme(voteThemeId);
        validateVoteSessionTime(voteSession);
        return voteSession;
    }

    public void validateVoteSessionTime(VoteSession voteSession) {
        LocalDateTime now = LocalDateTime.now(clock);

        if (null == voteSession) {
            throw new IllegalArgumentException("The voting session must be informed.");
        }

        switch (voteSession.getVoteTheme().getStatus()) {
        case OPENED:
            if (now.isBefore(voteSession.getVotingOpenTime())) {
                throw new IllegalArgumentException("The voting session start at "
                        .concat(voteSession.getVotingOpenTime().toString()));
            }

            if (now.isAfter(voteSession.getVotingCloseTime())) {
                throw new IllegalArgumentException("Session already closed.");
            }
            break;

        case ON_HOLD:
            throw new IllegalArgumentException("The voting session is not created yet.");

        case CLOSED:
            throw new IllegalArgumentException("The voting session is already closed.");

        default:
            throw new IllegalArgumentException("The voting session has an unknown status.");
        }
    }

    private VoteSession findByVoteTheme(Long voteThemeId) {
        return repository.findByVoteTheme(voteThemeId)
                .orElseThrow(() -> new ObjectNotFoundException("ERROR: Vote session not found! Searched for voteThemeId: "
                        .concat(voteThemeId.toString())));
    }

    private void canCreateVoteSession(Long voteThemeId) {
        if (repository.findByVoteTheme(voteThemeId).isPresent()) {
            throw new IllegalArgumentException("ERROR: The vote session to this theme already exists.");
        }
    }

    public VoteThemeResponseDTO closeVoteSession(Long voteThemeId) {
        VoteSession voteSession = findByVoteTheme(voteThemeId);
        if (VoteThemeStatus.CLOSED.equals(voteSession.getVoteTheme().getStatus())) {
            throw new IllegalArgumentException("The voting session is already closed.");
        }
        VoteTheme voteTheme = countVotes(voteSession);
        return voteThemeService.closeVoteTheme(voteTheme);
    }

    private VoteTheme countVotes(VoteSession voteSession) {
        Long positiveVotes = 0L;
        Long negativeVotes = 0L;

        for (Vote vote : voteSession.getVotes()) {
            if (VoteEnum.YES.equals(vote.getVote())) {
                positiveVotes = positiveVotes + 1;
            } else {
                negativeVotes = negativeVotes + 1;
            }
        }

        VoteTheme voteTheme = voteSession.getVoteTheme();

        voteTheme.setTotalOfPositiveVotes(positiveVotes);
        voteTheme.setTotalOfNegativeVotes(negativeVotes);

        return voteTheme;
    }

}
