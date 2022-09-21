package com.sicredi.assembly.vote;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class VoteController {

    private VoteService service;

    public VoteController(VoteService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vote created"),
            @ApiResponse(code = 400, message = "Bad request. Wrong param"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "Create a vote of an associate into a voting session, bound a theme/topic")
    @PostMapping(value = "/themes/{id}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> vote(@PathVariable Long id, @Valid @RequestBody VoteRequestDTO dto) {
        service.saveVote(id, dto);
        return ResponseEntity.ok().build();
    }

}
