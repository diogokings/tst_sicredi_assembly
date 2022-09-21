package com.sicredi.assembly.session;

import com.google.gson.Gson;
import com.sicredi.assembly.common.exception.ObjectNotFoundException;
import com.sicredi.assembly.common.exception.ResourceExceptionHandler;
import com.sicredi.assembly.theme.VoteThemeResponseDTO;
import com.sicredi.assembly.theme.VoteThemeResult;
import com.sicredi.assembly.theme.VoteThemeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        VoteSessionController.class, VoteSessionService.class, ResourceExceptionHandler.class
})
class VoteSessionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private VoteSessionService service;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void openVoteSession_givenTheme_whenNotFound_thenThrowException() throws Exception {
        String expectedErrorMessage = "ERROR: Theme not found! Searched for id: 1";
        Exception expectedException = new ObjectNotFoundException(expectedErrorMessage);
        CreateVoteSessionRequestDTO request = new CreateVoteSessionRequestDTO();

        Mockito.doThrow(expectedException).when(service)
                .createVoteSession(ArgumentMatchers.eq(1L), ArgumentMatchers.any(CreateVoteSessionRequestDTO.class));

        mockMvc.perform(post("/themes/1/open")
                        .content(requestToString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage));
    }

    @Test
    void openVoteSession_givenTheme_whenClosed_thenThrowException() throws Exception {
        String expectedErrorMessage = "ERROR: Theme is closed. No more vote sessions can be created for this theme";
        Exception expectedException = new IllegalArgumentException(expectedErrorMessage);

        Mockito.doThrow(expectedException).when(service)
                .createVoteSession(ArgumentMatchers.eq(1L), ArgumentMatchers.any(CreateVoteSessionRequestDTO.class));

        mockMvc.perform(post("/themes/1/open")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage));
    }

    @Test
    void openVoteSession_givenTheme_whenFoundAndValid_thenOpenVoteSession() throws Exception {
        Mockito.doNothing().when(service)
                .createVoteSession(ArgumentMatchers.eq(1L), ArgumentMatchers.any(CreateVoteSessionRequestDTO.class));

        mockMvc.perform(post("/themes/1/open")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    void getVoteTheme_givenRequest_whenOnHoldVoteThemeFound_thenReturnIt() throws Exception {
        Long id = 1L;

        VoteThemeResponseDTO expectedResponse = new VoteThemeResponseDTO();
        expectedResponse.setDescription("Testando");
        expectedResponse.setStatus(VoteThemeStatus.CLOSED.getDescription());
        expectedResponse.setResult(VoteThemeResult.REJECTED.getDescription());
        expectedResponse.setTotalOfPositiveVotes(14L);
        expectedResponse.setTotalOfNegativeVotes(26L);

        when(service.closeVoteSession(id))
                .thenReturn(expectedResponse);

        mockMvc.perform(delete("/themes/1/close")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.description").value("Testando"))
                .andExpect(jsonPath("$.status").value("Encerrada"))
                .andExpect(jsonPath("$.result").value("Rejeitado"))
                .andExpect(jsonPath("$.totalOfPositiveVotes").value("14"))
                .andExpect(jsonPath("$.totalOfNegativeVotes").value("26"));
    }

    private String requestToString(CreateVoteSessionRequestDTO request) {
        return new Gson().toJson(request);
    }

}
