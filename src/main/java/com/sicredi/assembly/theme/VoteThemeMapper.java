package com.sicredi.assembly.theme;

import org.springframework.stereotype.Component;

@Component
public class VoteThemeMapper {

    public static VoteThemeResponseDTO toDTO(VoteTheme entity) {
        VoteThemeResponseDTO dto = new VoteThemeResponseDTO();
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus().getDescription());
        dto.setResult(entity.getResult().getDescription());
        dto.setTotalOfPositiveVotes(entity.getTotalOfPositiveVotes());
        dto.setTotalOfNegativeVotes(entity.getTotalOfNegativeVotes());

        return dto;
    }

}
