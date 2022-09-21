package com.sicredi.assembly.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sicredi.assembly.theme.VoteTheme;
import com.sicredi.assembly.vote.Vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "VOTE_SESSION")
public class VoteSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "VOTE_THEME_ID", referencedColumnName = "id")
    private VoteTheme voteTheme;

    private LocalDateTime votingOpenTime;

    private LocalDateTime votingCloseTime;

    @JsonIgnore
    @OneToMany(mappedBy = "voteSession")
    private final List<Vote> votes = new ArrayList<>();

    public VoteSession() {
    }

    public VoteSession(VoteTheme theme, LocalDateTime votingOpenTime, LocalDateTime votingCloseTime) {
        this.voteTheme = theme;
        this.votingOpenTime = votingOpenTime;
        this.votingCloseTime = votingCloseTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteTheme getVoteTheme() {
        return voteTheme;
    }

    public LocalDateTime getVotingOpenTime() {
        return votingOpenTime;
    }

    public LocalDateTime getVotingCloseTime() {
        return votingCloseTime;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        VoteSession that = (VoteSession) o;
        return id.equals(that.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }

}
