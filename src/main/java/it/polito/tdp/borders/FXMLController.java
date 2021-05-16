
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML
    private ComboBox<Country> boxStati;
    
    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private Button btnRaggiungibili;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	int anno;
    	
    	try {
    		
    		anno = Integer.parseInt(txtAnno.getText());
    		
    	} catch (NumberFormatException e) {
    		txtResult.setText("Errore nel inserimento dell'anno");
    		return;
    	}
    	
    	if (anno<1816 || anno>2016) {
			txtResult.setText("Inserire un anno compreso tra il 1816 e il 2016");
			return;
    	}
    
    	this.model.createGraph(anno);
    	
    	txtResult.setText("Lista di paesi connessi nell'anno inserito:\n");
    	for (Country c : this.model.getGrafo().vertexSet()) {
    		txtResult.appendText(c.toString() +", numero di connessioni: " + this.model.getGrafo().degreeOf(c) + "\n");
    	}
    	txtResult.appendText("Nel grafico sono presenti " + this.model.getConnectedComponents() + " componenti connesse.");
    	
    	this.boxStati.setDisable(false);
    	this.boxStati.getItems().addAll(this.model.getCountries());
    	this.btnRaggiungibili.setDisable(false);

    }
    
    @FXML
    void doStatiRaggiungibili(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	if (this.boxStati.getValue()==null) {
    		txtResult.setText("Selezionare uno stato dal menù");
    		return;
    	}
    	
    	List<Country> risultato = this.model.getStatiRaggiungibili(this.boxStati.getValue());
    	
    	txtResult.setText("Lista degli stati raggiungibili dal paese: "+this.boxStati.getValue().toString()+"\n");
    	for (Country c : risultato) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    	txtResult.appendText("Numero totale di stati raggiungibili: "+risultato.size());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxStati != null : "fx:id=\"boxStati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRaggiungibili != null : "fx:id=\"btnRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
