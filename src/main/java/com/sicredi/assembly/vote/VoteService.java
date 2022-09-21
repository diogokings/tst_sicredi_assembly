package com.sicredi.assembly.vote;

import com.sicredi.assembly.common.exception.ObjectNotFoundException;
import com.sicredi.assembly.session.VoteSession;
import com.sicredi.assembly.session.VoteSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final VoteSessionService voteSessionService;

    private final AssociateInfo associateInfo;

    public VoteService(VoteRepository voteRepository, VoteSessionService voteSessionService, AssociateInfo associateInfo) {
        this.voteRepository = voteRepository;
        this.voteSessionService = voteSessionService;
        this.associateInfo = associateInfo;
    }

    @Transactional
    public void saveVote(Long voteThemeId, VoteRequestDTO dto) {
        VoteSession voteSession = voteSessionService.getOpenedVoteSession(voteThemeId);
        String cpf = validateAssociateVote(voteSession, dto);
        Vote vote = VoteMapper.toEntity(voteSession, cpf, dto.getVote());
        voteRepository.save(vote);
    }

    private String validateAssociateVote(VoteSession voteSession, VoteRequestDTO dto) {
        String cpf = removeSpecialCharsAndSpacesFromCPF(dto.getCpf());

        if (voteRepository.findByVoteSessionAndAssociateDocument(voteSession, cpf).isPresent()) {
            throw new IllegalArgumentException("ERROR: Associate already vote in this session.");
        }

        String status = associateInfo.getAssociate(cpf);

        if (status.equals("")) {
            throw new ObjectNotFoundException(
                    "ERROR: Associate document 'CPF' wasn't found or we had some issue while searching"
                            + ". Confirm the document number and try again.");
        }

        if (!status.equalsIgnoreCase("ABLE_TO_VOTE")) {
            throw new IllegalArgumentException("ERROR: Associate is unable to vote.");
        }

        return cpf;
    }

    private String removeSpecialCharsAndSpacesFromCPF(String cpf) {
        return cpf.replaceAll("\\W", "").trim();
    }

}
