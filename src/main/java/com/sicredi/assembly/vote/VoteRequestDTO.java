package com.sicredi.assembly.vote;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

public class VoteRequestDTO {

    @CPF
    @NotBlank(message = "is required")
    private String cpf;

    @NotBlank(message = "is required")
    private String vote;

    public VoteRequestDTO() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

}
