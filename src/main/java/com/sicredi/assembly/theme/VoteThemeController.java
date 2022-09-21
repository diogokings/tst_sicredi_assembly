package com.sicredi.assembly.theme;

import com.sicredi.assembly.session.CreateVoteSessionRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api
@RestController
@RequestMapping("/themes")
public class VoteThemeController {

    private final VoteThemeService service;

    public VoteThemeController(VoteThemeService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vote theme created"),
            @ApiResponse(code = 400, message = "Bad request. Wrong param"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @ApiOperation(value = "Create a Theme (agenda for associates vote)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createVoteTheme(@Valid @RequestBody CreateVoteThemeRequestDTO dto) {
        Long id = service.createVoteTheme(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }



//
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Vote theme closed"),
//            @ApiResponse(code = 400, message = "Bad request. Wrong param"),
//            @ApiResponse(code = 500, message = "Internal server error") })
//    @ApiOperation(value = "Close a Theme and process the votes, returning the result.")
//    @PostMapping(value = "/theme/close/{id}")
//    public ResponseEntity<VoteThemeResponseDTO> closeVoteTheme(@PathVariable Long id) {
//        VoteThemeResponseDTO responseDTO = service.closeVoteTheme(id);
//        return ResponseEntity.ok().body(responseDTO);
//    }

}
