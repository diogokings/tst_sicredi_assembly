package com.sicredi.assembly.session;

import com.sicredi.assembly.theme.VoteThemeResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Api
@RestController
@RequestMapping("/themes")
public class VoteSessionController {

    private final VoteSessionService service;

    public VoteSessionController(VoteSessionService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Voting session opened"),
            @ApiResponse(code = 400, message = "Bad request. Wrong param"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "Open a session to associates vote in the theme/topic specified on request")
    @PostMapping(value = "/{id}/open", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> openVoteSession(@PathVariable Long id, @RequestBody CreateVoteSessionRequestDTO dto) {
        service.createVoteSession(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vote session closed"),
            @ApiResponse(code = 400, message = "Bad request. Wrong param"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @ApiOperation(value = "Close vote session for a theme and process the votes, returning the result.")
    @DeleteMapping(value = "/{id}/close")
    public ResponseEntity<VoteThemeResponseDTO> closeVoteSession(@PathVariable Long id) {
        VoteThemeResponseDTO responseDTO = service.closeVoteSession(id);
        return ResponseEntity.ok().body(responseDTO);
    }

}
