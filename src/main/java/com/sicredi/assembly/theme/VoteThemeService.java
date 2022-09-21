package com.sicredi.assembly.theme;

import com.sicredi.assembly.common.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VoteThemeService {

    private final VoteThemeRepository voteThemeRepository;

    public VoteThemeService(VoteThemeRepository voteThemeRepository) {
        this.voteThemeRepository = voteThemeRepository;
    }

    public Long createVoteTheme(CreateVoteThemeRequestDTO dto) {
        if (null == dto || null == dto.getDescription() || dto.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: Vote theme not created! Description not found while processing.");
        }

        VoteTheme entity = new VoteTheme(dto.getDescription().trim());
        entity = voteThemeRepository.save(entity);

        return entity.getId();
    }

    public VoteThemeResponseDTO getVoteTheme(Long themeId) {
        VoteTheme entity = findById(themeId);
        return VoteThemeMapper.toDTO(entity);
    }

    public VoteTheme openVoteSession(Long id) {
        VoteTheme voteTheme = canVoteSessionBeOpened(id);
        voteTheme.setStatus(VoteThemeStatus.OPENED);
        return voteTheme;
    }

    private VoteTheme findById(Long id) {
        return voteThemeRepository.findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException("ERROR: Theme not found! Searched for id: ".concat(id.toString())));
    }

    private VoteTheme canVoteSessionBeOpened(Long id) {
        VoteTheme theme = findById(id);

        if (VoteThemeStatus.CLOSED.equals(theme.getStatus())) {
            throw new IllegalArgumentException("ERROR: Theme is closed. No more vote sessions can be created for this theme");
        }

        if (VoteThemeStatus.OPENED.equals(theme.getStatus())) {
            throw new IllegalArgumentException("ERROR: Vote session is already open.");
        }

        return theme;
    }

    public VoteThemeResponseDTO closeVoteTheme(VoteTheme voteTheme) {
        voteTheme.setResult(VoteThemeResult.getResult(voteTheme.getTotalOfPositiveVotes(), voteTheme.getTotalOfNegativeVotes()));
        voteTheme.setStatus(VoteThemeStatus.CLOSED);
        voteThemeRepository.save(voteTheme);
        return VoteThemeMapper.toDTO(voteTheme);
    }

}
