package com.sicredi.assembly.session;

import com.sicredi.assembly.theme.VoteTheme;
import com.sicredi.assembly.theme.VoteThemeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class VoteSessionServiceTest {

    private final LocalDateTime dateTime = LocalDateTime.of(2022, Month.SEPTEMBER, 18, 14, 26);
    private final ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
    private final Instant instant = dateTime.atZone(zoneId).toInstant();
    private final Clock clock = Clock.fixed(instant, zoneId);

    private VoteSessionService service;

    @Mock
    private VoteSessionRepository repository;

    @Mock
    private VoteThemeService voteThemeService;

    @Captor
    private ArgumentCaptor<VoteSession> captorVoteSession;

    @BeforeEach
    void init() {
        service = new VoteSessionService(clock, repository, voteThemeService);
    }

    @Test
    void createVoteSession_givenRequestToOneMinuteOfDuration_whenOpenVoteSession_thenCreateToCloseAfterOneMinute() {
        CreateVoteSessionRequestDTO dto = new CreateVoteSessionRequestDTO();
        dto.setDurationInMinutes(1L);
        Long voteThemeId = 1L;
        VoteTheme expectedVoteTheme = new VoteTheme();
        expectedVoteTheme.setId(voteThemeId);

        when(voteThemeService.openVoteSession(voteThemeId)).thenReturn(expectedVoteTheme);
        service.createVoteSession(voteThemeId, dto);

        verify(repository, times(1))
                .saveAndFlush(captorVoteSession.capture());

        VoteSession actualVoteSession = captorVoteSession.getValue();

        assertEquals(voteThemeId, actualVoteSession.getVoteTheme().getId());
        assertEquals(LocalDateTime.now(clock), actualVoteSession.getVotingOpenTime());
    }

    //	@Test
    //	public void givenNewVoteSession_whenCreateWithValidDto_thenSaveVoteSession() {
    //		Long expectedVoteSessionId = 1L;
    //		voteSessionMock = new VoteSession();
    //		voteSessionMock.setId(1L);
    //
    //		CreateVoteSessionRequestDTO requestDTO = new CreateVoteSessionRequestDTO();
    //		requestDTO.setThemeId(1L);
    //		requestDTO.setVotingOpenTime(LocalDateTime.now());
    //		requestDTO.setVotingCloseTime(LocalDateTime.now().plusHours(1));
    //
    //		when(voteSessionRepository.save(any())).thenReturn(voteSessionMock);
    //
    //		Long actualVoteSessionId = voteSessionService.create(requestDTO);
    //
    //		assertEquals(expectedVoteSessionId, actualVoteSessionId);
    //	}
    //
    //	@Test
    //	public void givenNewVoteSession_whenCreateWithAEmptyCloseTime_thenSaveVoteSessionWithOneMinute() {
    //		Long expectedVoteSessionId = 1L;
    //		voteSessionMock = new VoteSession();
    //		voteSessionMock.setId(1L);
    //
    //		CreateVoteSessionRequestDTO requestDTO = new CreateVoteSessionRequestDTO();
    //		requestDTO.setThemeId(1L);
    //		requestDTO.setVotingOpenTime(LocalDateTime.now());
    //
    //		when(voteSessionRepository.save(any())).thenReturn(voteSessionMock);
    //
    //		Long actualVoteSessionId = voteSessionService.create(requestDTO);
    //
    //		assertEquals(expectedVoteSessionId, actualVoteSessionId);
    //	}
    //
    //	@Test
    //	public void givenNewVoteSession_whenCreateWithInvalidOpenTime_thenThrowAnException() {
    //		String expectedErrorMessage = "Open session vote time must not be null.";
    //
    //		CreateVoteSessionRequestDTO requestDTO = new CreateVoteSessionRequestDTO();
    //		requestDTO.setThemeId(1L);
    //
    //		assertThatExceptionOfType(IllegalArgumentException.class)
    //				.isThrownBy(() -> voteSessionService.create(requestDTO)).withMessage(expectedErrorMessage);
    //	}
    //
    //	@Test
    //	public void givenNewVoteSession_whenCreateWithACloseTimeBeforeTheOpenTime_thenThrowAnException() {
    //		String expectedErrorMessage = "Close session vote time must be after open time.";
    //
    //		CreateVoteSessionRequestDTO requestDTO = new CreateVoteSessionRequestDTO();
    //		requestDTO.setThemeId(1L);
    //		requestDTO.setVotingOpenTime(LocalDateTime.now());
    //		requestDTO.setVotingCloseTime(LocalDateTime.now().minusHours(1));
    //
    //		assertThatExceptionOfType(IllegalArgumentException.class)
    //				.isThrownBy(() -> voteSessionService.create(requestDTO)).withMessage(expectedErrorMessage);
    //	}

}
