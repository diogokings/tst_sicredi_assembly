package com.sicredi.assembly.config.environment;

import com.sicredi.assembly.session.VoteSession;
import com.sicredi.assembly.session.VoteSessionRepository;
import com.sicredi.assembly.theme.VoteTheme;
import com.sicredi.assembly.theme.VoteThemeRepository;
import com.sicredi.assembly.theme.VoteThemeStatus;
import com.sicredi.assembly.vote.Vote;
import com.sicredi.assembly.vote.VoteEnum;
import com.sicredi.assembly.vote.VoteRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class TestDBService {

    private final VoteThemeRepository themeRepository;
    private final VoteRepository voteRepository;
    private final VoteSessionRepository voteSessionRepository;

    public TestDBService(VoteThemeRepository themeRepository, VoteRepository voteRepository, VoteSessionRepository voteSessionRepository) {
        this.themeRepository = themeRepository;
        this.voteRepository = voteRepository;
        this.voteSessionRepository = voteSessionRepository;
    }

    public void instantiateTestDatabase() {
        VoteTheme theme1 = new VoteTheme("TEST 1");
        VoteTheme theme2 = new VoteTheme("TEST 2");
        VoteTheme theme3 = new VoteTheme("TEST 3");

        theme1.setStatus(VoteThemeStatus.OPENED);
        theme2.setStatus(VoteThemeStatus.OPENED);
        theme3.setStatus(VoteThemeStatus.OPENED);

        themeRepository.saveAll(Arrays.asList(theme1, theme2, theme3));


        VoteSession voteSession1 = new VoteSession(theme1, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        VoteSession voteSession2 = new VoteSession(theme2, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
        VoteSession voteSession3 = new VoteSession(theme3, LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(5));

        voteSessionRepository.saveAll(Arrays.asList(voteSession1, voteSession2, voteSession3));

        Vote vote1 = new Vote(voteSession1, "54144676099", VoteEnum.YES);
        Vote vote2 = new Vote(voteSession1, "79063436033", VoteEnum.YES);
        Vote vote3 = new Vote(voteSession1, "33843211035", VoteEnum.YES);

        Vote vote4 = new Vote(voteSession2, "27646541036", VoteEnum.NO);
        Vote vote5 = new Vote(voteSession2, "60229139000", VoteEnum.NO);

        Vote vote6 = new Vote(voteSession3, "21697615007", VoteEnum.NO);
        Vote vote7 = new Vote(voteSession3, "33843211035", VoteEnum.YES);

        voteRepository.saveAll(Arrays.asList(vote1, vote2, vote3, vote4, vote5, vote6, vote7));
    }

}
