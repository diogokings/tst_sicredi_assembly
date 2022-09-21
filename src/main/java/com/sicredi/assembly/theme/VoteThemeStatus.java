package com.sicredi.assembly.theme;

public enum VoteThemeStatus {

    ON_HOLD(0, "Aguardando votação"),
    OPENED(1, "Aberta"),
    CLOSED(2, "Encerrada");

    private Integer id;
    private String description;

    private VoteThemeStatus(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static VoteThemeStatus toEnum(Integer id) {
        if (id == null) {
            return null;
        }

        for (VoteThemeStatus x : VoteThemeStatus.values()) {
            if (id.equals(x.getId())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid Id: " + id);
    }

}
