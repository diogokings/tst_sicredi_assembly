package com.sicredi.assembly.vote;

import com.sicredi.assembly.session.VoteSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	Optional<Vote> findByVoteSessionAndAssociateDocument(VoteSession voteSession, String associateDocument);

}
