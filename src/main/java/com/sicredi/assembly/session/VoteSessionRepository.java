package com.sicredi.assembly.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VoteSessionRepository extends JpaRepository<VoteSession, Long> {

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = ""
            + " SELECT VS.*                             "
            + "   FROM VOTE_SESSION VS                  "
            + "  WHERE VS.VOTE_THEME_ID = :voteThemeId  ")
    Optional<VoteSession> findByVoteTheme(Long voteThemeId);

}
