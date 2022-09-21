package com.sicredi.assembly.theme;

public enum VoteThemeResult {

    ON_HOLD(0, "Aguardando votação"),
    REJECTED(1, "Rejeitado"),
    APPROVED(2, "Aprovado"),
    TIED(3, "Empatado");

    private Integer id;
    private String description;

    private VoteThemeResult(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static VoteThemeResult toEnum(Integer id) {
        if (id == null) {
            return null;
        }

        for (VoteThemeResult x : VoteThemeResult.values()) {
            if (id.equals(x.getId())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid Id: " + id);
    }

    protected static VoteThemeResult getResult(Long positiveVotes, Long negativeVotes) {
        if (positiveVotes > negativeVotes) {
            return VoteThemeResult.APPROVED;
        }

        if (negativeVotes > positiveVotes) {
            return VoteThemeResult.REJECTED;
        }

        return VoteThemeResult.TIED;
    }

}
