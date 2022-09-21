package com.sicredi.assembly.theme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteThemeRepository extends JpaRepository<VoteTheme, Long> {

}
