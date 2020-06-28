package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.MatchResult;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {
	
	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons" ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Season(res.getInt("season"), res.getString("description"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Team> listTeams(Season season, Map<String, Team> idMap) {
		String sql = "SELECT DISTINCT t.team AS t " + 
				"FROM teams AS t, matches AS m " + 
				"WHERE m.Season = ? AND (m.HomeTeam = t.team OR m.AwayTeam = t.team) " + 
				"ORDER BY t.team" ;
		
		List<Team> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, season.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(!idMap.containsKey(res.getString("t"))) {
					Team t = new Team(res.getString("t"));
					result.add(t);
					idMap.put(res.getString("t"), t);
				}
				
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<MatchResult> getMatchResults(Season season, Map<String, Team> idMap) {
		String sql = "SELECT m.HomeTeam AS id1, m.AwayTeam AS id2, m.FTR AS r " +
				"FROM matches AS m " +
				"WHERE m.Season = ? "+
				"GROUP BY m.HomeTeam, m.AwayTeam";
		List<MatchResult> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, season.getSeason());
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new MatchResult(idMap.get(res.getString("id1")), idMap.get(res.getString("id2")), res.getString("r")));
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}


}
