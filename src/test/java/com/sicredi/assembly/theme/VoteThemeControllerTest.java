package com.sicredi.assembly.theme;

import com.google.gson.Gson;
import com.sicredi.assembly.common.exception.ResourceExceptionHandler;
import com.sicredi.assembly.common.exception.ObjectNotFoundException;
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

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        VoteThemeController.class, VoteThemeService.class, ResourceExceptionHandler.class
})
class VoteThemeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private VoteThemeService service;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createVoteTheme_givenRequest_whenNoDescription_thenThrowException() throws Exception {
        String request = requestToString(new CreateVoteThemeRequestDTO());

        mockMvc.perform(post("/themes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void createVoteTheme_givenRequest_whenValid_thenSaveItAndReturnSuccess() throws Exception {
        CreateVoteThemeRequestDTO requestDTO = new CreateVoteThemeRequestDTO();
        requestDTO.setDescription("Criando uma pauta de teste");
        String request = requestToString(requestDTO);

        when(service.createVoteTheme(Mockito.any()))
                .thenReturn(1L);

        mockMvc.perform(post("/themes/")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    void getVoteTheme_givenRequest_whenInvalidId_thenThrowException() throws Exception {
        Long id = 1L;

        doThrow(ObjectNotFoundException.class)
                .when(service).getVoteTheme(id);

        mockMvc.perform(get("/themes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    private String requestToString(CreateVoteThemeRequestDTO request) {
        return new Gson().toJson(request);
    }

}
