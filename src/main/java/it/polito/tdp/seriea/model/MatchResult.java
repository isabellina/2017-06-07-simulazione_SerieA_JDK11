package it.polito.tdp.seriea.model;

public class MatchResult {
	
	private Team teamHome;
	private Team teamAway;
	private Integer result;
	
	public MatchResult(Team teamHome, Team teamAway, String result) {
		super();
		this.teamHome = teamHome;
		this.teamAway = teamAway;
		if(result.equals("H"))
			this.result = 1;
		else if(result.equals("A"))
			this.result = -1;
		else
			this.result = 0;
	}
	
	public MatchResult(Team teamHome, Team teamAway, Integer result) {
		super();
		this.teamHome = teamHome;
		this.teamAway = teamAway;
		this.result = result;
	}

	public Team getTeamHome() {
		return teamHome;
	}

	public Team getTeamAway() {
		return teamAway;
	}

	public Integer getResult() {
		return result;
	}
	
	public String toString() {
		return this.teamHome + " " + this.teamAway + " | " + result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamAway == null) ? 0 : teamAway.hashCode());
		result = prime * result + ((teamHome == null) ? 0 : teamHome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchResult other = (MatchResult) obj;
		if (teamAway == null) {
			if (other.teamAway != null)
				return false;
		} else if (!teamAway.equals(other.teamAway))
			return false;
		if (teamHome == null) {
			if (other.teamHome != null)
				return false;
		} else if (!teamHome.equals(other.teamHome))
			return false;
		return true;
	}
	
	

}
