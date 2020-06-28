package it.polito.tdp.seriea.model;

public class TeamPunti implements Comparable<TeamPunti> {
	
	private Team team;
	private Integer punti;
	
	public TeamPunti(Team team, Integer punti) {
		super();
		this.team = team;
		this.punti = punti;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Integer getPunti() {
		return punti;
	}
	
	public void setPunti(Integer punti) {
		this.punti = punti;
	}
	
	@Override
	public int compareTo(TeamPunti o) {
		return -this.punti.compareTo(o.punti);
	}
	
	public String toString() {
		return team.toString() + " - " + punti;
	}
	
}
