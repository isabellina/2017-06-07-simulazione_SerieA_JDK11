package it.polito.tdp.seriea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.MatchResult;
import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamPunti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Season> boxSeason;

    @FXML
    private ChoiceBox<Team> boxTeam;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCarica(ActionEvent event) {
    	txtResult.clear();
    	Season season = this.boxSeason.getValue();
    	if(season == null) {
    		txtResult.appendText("Selezionare una stagione");
    		return;
    	}
    	
    	List<Team> teams = this.model.buildGraph(season);
    	List<TeamPunti> result = this.model.getPunti();
    	for(TeamPunti t : result) {
    		txtResult.appendText(t.toString()+"\n");
    	}
    	this.boxTeam.getItems().setAll(teams);
    }

    @FXML
    void handleDomino(ActionEvent event) {
    	txtResult.clear();
    	Team team = this.boxTeam.getValue();
    	if(team == null) {
    		txtResult.appendText("Selezionare una squadra");
    		return;
    	}
    	List<MatchResult> result = this.model.trovaDomino(team);
    	for(MatchResult m : result)
    		txtResult.appendText(m.toString()+"\n");
    }

    @FXML
    void initialize() {
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxTeam != null : "fx:id=\"boxTeam\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		this.boxSeason.getItems().setAll(this.model.getSeasons());
	}
}
