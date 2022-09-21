package com.sicredi.assembly.theme;

public class VoteThemeResponseDTO {

	private String description;
	private String status;
	private String result;
	private Long totalOfPositiveVotes;
	private Long totalOfNegativeVotes;

	public VoteThemeResponseDTO() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getTotalOfPositiveVotes() {
		return totalOfPositiveVotes;
	}

	public void setTotalOfPositiveVotes(Long totalOfPositiveVotes) {
		this.totalOfPositiveVotes = totalOfPositiveVotes;
	}

	public Long getTotalOfNegativeVotes() {
		return totalOfNegativeVotes;
	}

	public void setTotalOfNegativeVotes(Long totalOfNegativeVotes) {
		this.totalOfNegativeVotes = totalOfNegativeVotes;
	}

}
