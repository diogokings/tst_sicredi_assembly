package com.sicredi.assembly.vote;

import com.sicredi.assembly.session.VoteSession;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "VOTE")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "VOTE_SESSION_ID")
    private VoteSession voteSession;
    private String associateDocument; // CPF
    private Integer vote;

    public Vote() {
    }

    public Vote(VoteSession voteSession, String associateDocument, VoteEnum vote) {
        this.voteSession = voteSession;
        this.associateDocument = associateDocument;
        this.vote = vote.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteSession getVoteSession() {
        return voteSession;
    }

    public void setVoteSession(VoteSession voteSession) {
        this.voteSession = voteSession;
    }

    public String getAssociateDocument() {
        return associateDocument;
    }

    public void setAssociateDocument(String associateDocument) {
        this.associateDocument = associateDocument;
    }

    public VoteEnum getVote() {
        return VoteEnum.toEnum(vote);
    }

    public void setVote(VoteEnum vote) {
        this.vote = vote.getId();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vote that = (Vote) o;
        return Objects.equals(id, that.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }

}
