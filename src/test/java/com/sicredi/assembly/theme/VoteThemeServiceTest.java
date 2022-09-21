package com.sicredi.assembly.theme;

import com.sicredi.assembly.common.exception.ObjectNotFoundException;
import com.sicredi.assembly.session.VoteSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteThemeServiceTest {

    @InjectMocks
    private VoteThemeService service;

    @Mock
    private VoteThemeRepository repository;

    @Test
    void createVoteTheme_givenRequest_whenNull_thenThrowException() {
        String expectedErrorMsg = "ERROR: Vote theme not created! Description not found while processing.";
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> service.createVoteTheme(null));

        assertEquals(expectedErrorMsg, actualException.getMessage());
    }

    @Test
    void createVoteTheme_givenRequest_whenDescriptionIsEmpty_thenThrowException() {
        String expectedErrorMsg = "ERROR: Vote theme not created! Description not found while processing.";
        String description = "     ";

        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> service.createVoteTheme(mockCreateVoteThemeRequestDTO(description)));

        assertEquals(expectedErrorMsg, actualException.getMessage());
    }

    @Test
    void createVoteTheme_givenRequest_whenValid_thenCreateEntity() {
        String description = "Creating a test for voting theme";
        VoteTheme expectedVoteTheme = new VoteTheme();
        expectedVoteTheme.setId(1L);

        when(repository.save(Mockito.any()))
                .thenReturn(expectedVoteTheme);

        Long actualId = service.createVoteTheme(mockCreateVoteThemeRequestDTO(description));

        assertEquals(expectedVoteTheme.getId(), actualId);
    }

    @Test
    void getVoteTheme_givenId_whenNotFound_thenThrowException() {
        String expectedErrorMsg = "ERROR: Theme not found! Searched for id: 1";
        String actualErrorMsg = assertThrows(ObjectNotFoundException.class,
                () -> service.getVoteTheme(1L)).getMessage();
        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void getVoteTheme_givenId_whenFound_thenReturnIt() {
        VoteTheme entity = new VoteTheme();
        entity.setStatus(VoteThemeStatus.ON_HOLD);
        entity.setResult(VoteThemeResult.ON_HOLD);
        entity.setTotalOfPositiveVotes(0L);
        entity.setTotalOfNegativeVotes(0L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(entity));

        VoteThemeResponseDTO dto = service.getVoteTheme(1L);

        assertEquals(entity.getStatus().getDescription(), dto.getStatus());
        assertEquals(entity.getResult().getDescription(), dto.getResult());
        assertEquals(entity.getTotalOfPositiveVotes(), dto.getTotalOfPositiveVotes());
        assertEquals(entity.getTotalOfNegativeVotes(), dto.getTotalOfNegativeVotes());
    }

    @Test
    void openVoteSession_givenTheme_whenClosed_thenThrowException() {
        String expectedErrorMsg = "ERROR: Theme is closed. No more vote sessions can be created for this theme";
        VoteTheme voteTheme = new VoteTheme();
        voteTheme.setStatus(VoteThemeStatus.CLOSED);

        when(repository.findById(1L))
                .thenReturn(Optional.of(voteTheme));

        String actualErrorMsg = assertThrows(IllegalArgumentException.class,
                () -> service.openVoteSession(1L)).getMessage();

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void openVoteSession_givenTheme_whenAlreadyOpen_thenThrowException() {
        String expectedErrorMsg = "ERROR: Vote session is already open.";
        VoteTheme voteTheme = new VoteTheme();
        voteTheme.setStatus(VoteThemeStatus.OPENED);

        when(repository.findById(1L))
                .thenReturn(Optional.of(voteTheme));

        String actualErrorMsg = assertThrows(IllegalArgumentException.class,
                () -> service.openVoteSession(1L)).getMessage();

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    @Test
    void openVoteSession_givenTheme_whenOnHold_thenOpenVoteSession() {
        VoteTheme voteTheme = new VoteTheme();
        voteTheme.setStatus(VoteThemeStatus.ON_HOLD);

        when(repository.findById(1L))
                .thenReturn(Optional.of(voteTheme));

        service.openVoteSession(1L);

        //assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    private CreateVoteThemeRequestDTO mockCreateVoteThemeRequestDTO(String description) {
        CreateVoteThemeRequestDTO dto = new CreateVoteThemeRequestDTO();
        dto.setDescription(description);
        return dto;
    }

}
