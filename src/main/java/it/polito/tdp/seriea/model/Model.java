package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private Graph<Team, DefaultWeightedEdge> graph;
	private SerieADAO dao;
	
	private Map<String, Team> teamIdMap;
	
	private List<MatchResult> bestPath;
	
	
	public Model() {
		this.dao = new SerieADAO();
	}
	
	public List<Season> getSeasons() {
		return this.dao.listSeasons();
	}
	
	public List<Team> buildGraph(Season season) {
		this.graph = new SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.teamIdMap = new HashMap<String, Team>();
		List<Team> teams = this.dao.listTeams(season, this.teamIdMap);
		Graphs.addAllVertices(this.graph, teams);
		
		for(MatchResult m : this.dao.getMatchResults(season, teamIdMap)) {
			Graphs.addEdge(this.graph, m.getTeamHome(), m.getTeamAway(), m.getResult());
		}
		
		return teams;
	}
	
	public List<TeamPunti> getPunti() {
		Map<Team, TeamPunti> map = new HashMap<>();
		
		for(Team team : this.graph.vertexSet()) {
			if(!map.containsKey(team))
				map.put(team, new TeamPunti(team, 0));
			TeamPunti punti = map.get(team);
			for(DefaultWeightedEdge edge : this.graph.outgoingEdgesOf(team)) {
				if(this.graph.getEdgeWeight(edge) == 1) {
					punti.setPunti(punti.getPunti()+3);;
				} else if(this.graph.getEdgeWeight(edge) == 0) {
					punti.setPunti(punti.getPunti()+1);;
				}
			}
			for(DefaultWeightedEdge edge : this.graph.incomingEdgesOf(team)) {
				if(this.graph.getEdgeWeight(edge) == -1) {
					punti.setPunti(punti.getPunti()+3);;
				} else if(this.graph.getEdgeWeight(edge) == 0) {
					punti.setPunti(punti.getPunti()+1);;
				}
			}
		}
		
		List<TeamPunti> result = new ArrayList<TeamPunti>(map.values());
		result.sort(null);
		return result;
	}
	
	public List<MatchResult> trovaDomino(Team source) {
		this.bestPath = new ArrayList<>();
		
		List<MatchResult> parziale = new ArrayList<>();
		this.ricorsiva(parziale, source, 0);
		
		return this.bestPath;
	}

	private void ricorsiva(List<MatchResult> parziale, Team source, Integer livello) {
		if(livello > this.bestPath.size()) {
			this.bestPath = new ArrayList<>(parziale);
			if(this.bestPath.size() == this.graph.edgeSet().size())
				return;
		}
		
		if(livello == 0) {
			for(DefaultWeightedEdge edge : this.graph.outgoingEdgesOf(source)) {
				MatchResult mr = new MatchResult(source, this.graph.getEdgeTarget(edge), (int) this.graph.getEdgeWeight(edge));
				if(mr.getResult() == 1 && !parziale.contains(mr)) {
					parziale.add(mr);
					this.ricorsiva(parziale, source, livello+1);
					parziale.remove(parziale.size()-1);
				}
			}
		} else {
			Team last = parziale.get(parziale.size()-1).getTeamAway();
			for(DefaultWeightedEdge edge : this.graph.outgoingEdgesOf(last)) {
				MatchResult mr = new MatchResult(last, this.graph.getEdgeTarget(edge), (int) this.graph.getEdgeWeight(edge));
				if(mr.getResult() == 1 && !parziale.contains(mr)) {
					parziale.add(mr);
					this.ricorsiva(parziale, source, livello+1);
					parziale.remove(parziale.size()-1);
				}
			}
		}
		
	}

}
