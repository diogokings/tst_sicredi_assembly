package com.sicredi.assembly.vote;

import com.google.gson.Gson;
import com.sicredi.assembly.common.exception.ObjectNotFoundException;
import com.sicredi.assembly.common.exception.ResourceExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        VoteController.class, VoteService.class, ResourceExceptionHandler.class
})
class VoteControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private VoteService service;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createVoteTheme_givenRequest_whenEmptyBody_thenThrowException() throws Exception {
        mockMvc.perform(post("/themes/1/votes")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void createVoteTheme_givenRequest_whenVoteIsEmpty_thenThrowException() throws Exception {
        String expectedErrorMessage = "ERROR: Invalid request! REASON: There are missing fields or with invalid values";
        String expectedValidationMessage = "is required";
        String request = requestToString("01234567890", " ");

        mockMvc.perform(post("/themes/1/votes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.errors.vote").value(expectedValidationMessage));
    }

    @Test
    void createVoteTheme_givenRequest_whenCpfIsInvalid_thenThrowException() throws Exception {
        String expectedErrorMessage = "ERROR: Invalid request! REASON: There are missing fields or with invalid values";
        String expectedValidationMessage = "invalid Brazilian individual taxpayer registry number (CPF)";
        String request = requestToString("02234567890", "SIM");

        mockMvc.perform(post("/themes/1/votes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.errors.cpf").value(expectedValidationMessage));
    }

    @Test
    void createVoteTheme_givenRequest_whenVoteThemeNotFound_thenThrowException() throws Exception {
        String request = requestToString("01234567890", "SIM");

        Exception objectNotFound = new ObjectNotFoundException("ERROR: Vote theme not found!");

        Mockito.doThrow(objectNotFound)
                .when(service).saveVote(eq(1L), any());

        mockMvc.perform(post("/themes/1/votes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    private String requestToString(String cpf, String vote) {
        VoteRequestDTO request = new VoteRequestDTO();
        request.setCpf(cpf);
        request.setVote(vote);

        return new Gson().toJson(request);
    }

}
