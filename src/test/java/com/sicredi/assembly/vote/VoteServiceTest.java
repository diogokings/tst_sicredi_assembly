package com.sicredi.assembly.vote;

import com.sicredi.assembly.common.exception.ObjectNotFoundException;
import com.sicredi.assembly.session.VoteSession;
import com.sicredi.assembly.session.VoteSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteSessionService voteSessionService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AssociateInfo associateInfo;

    @Mock
    private Vote mockedVote;

    @Mock
    private VoteSession mockedVoteSession;

    @Captor
    private ArgumentCaptor<Vote> captorVote;

    @Test
    void saveVote_givenRequest_whenSessionNotFound_thenThrowException() {
        Long voteThemeId = 1L;
        String expectedErrorMsg = "ERROR: Vote session not found! Searched for voteThemeId: 1";
        Exception expectedException = new ObjectNotFoundException(expectedErrorMsg);

        when(voteSessionService.getOpenedVoteSession(voteThemeId))
                .thenThrow(expectedException);

        String actualErrorMsg = assertThrows(ObjectNotFoundException.class,
                () -> voteService.saveVote(voteThemeId, null)).getMessage();

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void saveVote_givenRequest_whenSessionClosed_thenThrowException() {
        Long voteThemeId = 1L;
        String expectedErrorMsg = "The voting session is already closed.";
        Exception expectedException = new IllegalArgumentException(expectedErrorMsg);

        when(voteSessionService.getOpenedVoteSession(voteThemeId))
                .thenThrow(expectedException);

        String actualErrorMsg = assertThrows(IllegalArgumentException.class,
                () -> voteService.saveVote(voteThemeId, null)).getMessage();

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void saveVote_givenRequest_whenUserAlreadyVoted_thenThrowException() {
        Long voteThemeId = 1L;
        String cpf = "01234567890";
        String userVote = "SIM";

        String expectedErrorMsg = "ERROR: Associate already vote in this session.";

        when(voteSessionService.getOpenedVoteSession(voteThemeId))
                .thenReturn(mockedVoteSession);

        when(voteRepository.findByVoteSessionAndAssociateDocument(mockedVoteSession, cpf))
                .thenReturn(Optional.of(mockedVote));

        String actualErrorMsg = assertThrows(IllegalArgumentException.class,
                () -> voteService.saveVote(voteThemeId, getMockedDTO(cpf, userVote))).getMessage();

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void saveVote_givenRequest_whenUserNotFound_thenThrowException() {
        Long voteThemeId = 1L;
        String cpf = "01234567890";
        String userVote = "SIM";

        String expectedErrorMsg = "ERROR: Associate document 'CPF' wasn't found or we had some issue while searching"
                + ". Confirm the document number and try again.";

        when(voteSessionService.getOpenedVoteSession(voteThemeId))
                .thenReturn(mockedVoteSession);

        when(voteRepository.findByVoteSessionAndAssociateDocument(mockedVoteSession, cpf))
                .thenReturn(Optional.empty());

        when(associateInfo.getAssociate(cpf))
                .thenReturn("");

        String actualErrorMsg = assertThrows(ObjectNotFoundException.class,
                () -> voteService.saveVote(voteThemeId, getMockedDTO(cpf, userVote))).getMessage();

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void saveVote_givenRequest_whenUserUnableToVote_thenThrowException() {
        Long voteThemeId = 1L;
        String cpf = "01234567890";
        String userVote = "SIM";

        String expectedErrorMsg = "ERROR: Associate is unable to vote.";

        when(voteSessionService.getOpenedVoteSession(voteThemeId))
                .thenReturn(mockedVoteSession);

        when(voteRepository.findByVoteSessionAndAssociateDocument(mockedVoteSession, cpf))
                .thenReturn(Optional.empty());

        when(associateInfo.getAssociate(cpf))
                .thenReturn("UNABLE_TO_VOTE");

        String actualErrorMsg = assertThrows(IllegalArgumentException.class,
                () -> voteService.saveVote(voteThemeId, getMockedDTO(cpf, userVote))).getMessage();

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void saveVote_givenRequest_whenUserAbleToVote_thenValidateVote() {
        Long voteThemeId = 1L;
        String cpf = "01234567890";
        String userVote = "SIM";

        when(voteSessionService.getOpenedVoteSession(voteThemeId))
                .thenReturn(mockedVoteSession);

        when(voteRepository.findByVoteSessionAndAssociateDocument(mockedVoteSession, cpf))
                .thenReturn(Optional.empty());

        when(associateInfo.getAssociate(cpf))
                .thenReturn("ABLE_TO_VOTE");

        voteService.saveVote(voteThemeId, getMockedDTO(cpf, userVote));

        verify(voteRepository, times(1)).save(captorVote.capture());

        Vote voteSaved = captorVote.getValue();

        assertEquals(mockedVoteSession, voteSaved.getVoteSession());
        assertEquals(cpf, voteSaved.getAssociateDocument());
        assertEquals(VoteEnum.YES, voteSaved.getVote());
    }

    private VoteRequestDTO getMockedDTO(String cpf, String userVote) {
        VoteRequestDTO dto = new VoteRequestDTO();
        dto.setCpf(cpf);
        dto.setVote(userVote);
        return dto;
    }

}
