package com.sicredi.assembly.theme;

import javax.validation.constraints.NotBlank;

public class CreateVoteThemeRequestDTO {

    @NotBlank(message = "is required")
    private String description;

    public CreateVoteThemeRequestDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
