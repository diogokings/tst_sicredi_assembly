package com.sicredi.assembly.vote;

public enum VoteEnum {

	NO(0, "NÃO"),
	YES(1, "SIM");

	private Integer id;
	private String vote;

	private VoteEnum(Integer id, String vote) {
		this.id = id;
		this.vote = vote;
	}

	public Integer getId() {
		return id;
	}

	public String getVote() {
		return vote;
	}

	public static VoteEnum toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (VoteEnum x : VoteEnum.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Invalid Id: " + id);
	}

	public static VoteEnum toEnum(String vote) {
		if (null == vote) {
			return null;
		}

		for (VoteEnum x : VoteEnum.values()) {
			if (vote.equals(x.getVote())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Invalid Vote: " + vote);
	}
}
