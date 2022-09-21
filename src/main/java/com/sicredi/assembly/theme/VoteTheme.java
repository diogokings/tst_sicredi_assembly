package com.sicredi.assembly.theme;

import com.sicredi.assembly.session.VoteSession;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "VOTE_THEME")
public class VoteTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Long totalOfPositiveVotes;
    private Long totalOfNegativeVotes;
    private Integer result;
    private Integer status;

    @OneToOne(mappedBy = "voteTheme")
    private VoteSession voteSession;

    public VoteTheme() {
        this(null);
    }

    public VoteTheme(String description) {
        this.status = VoteThemeStatus.ON_HOLD.getId();
        this.result = VoteThemeResult.ON_HOLD.getId();
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Long getTotalOfPositiveVotes() {
        return totalOfPositiveVotes;
    }

    public void setTotalOfPositiveVotes(Long totalOfPositiveVotes) {
        this.totalOfPositiveVotes = totalOfPositiveVotes;
    }

    public Long getTotalOfNegativeVotes() {
        return totalOfNegativeVotes;
    }

    public void setTotalOfNegativeVotes(Long totalOfNegativeVotes) {
        this.totalOfNegativeVotes = totalOfNegativeVotes;
    }

    public VoteThemeResult getResult() {
        return VoteThemeResult.toEnum(result);
    }

    public void setResult(VoteThemeResult result) {
        this.result = result.getId();
    }

    public VoteThemeStatus getStatus() {
        return VoteThemeStatus.toEnum(status);
    }

    public void setStatus(VoteThemeStatus status) {
        this.status = status.getId();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        VoteTheme voteTheme = (VoteTheme) o;
        return id.equals(voteTheme.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }

}
