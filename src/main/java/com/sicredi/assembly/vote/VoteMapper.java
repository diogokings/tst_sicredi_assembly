package com.sicredi.assembly.vote;

import com.sicredi.assembly.session.VoteSession;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

	public static Vote toEntity(VoteSession voteSession, String cpf, String vote) {
		Vote entity = new Vote();
		entity.setVoteSession(voteSession);
		entity.setAssociateDocument(cpf);
		entity.setVote(VoteEnum.toEnum(vote.trim().toUpperCase()));

		return entity;
	}
	
}
