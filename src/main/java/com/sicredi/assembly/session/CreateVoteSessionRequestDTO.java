package com.sicredi.assembly.session;

public class CreateVoteSessionRequestDTO {

    private Long durationInMinutes;

    public CreateVoteSessionRequestDTO() {
    }

    public Long getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Long durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

}
