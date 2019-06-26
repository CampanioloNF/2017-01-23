/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.awt.color.CMMException;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

	private int anno = 0; 
	
    @FXML
    void doCalcolaConfini(ActionEvent event) {

    	txtResult.clear();
    	
    	try {
    		
    		anno = Integer.parseInt(txtAnno.getText());
    		if(anno>2006 || anno<1816)
    			throw new NumberFormatException();
    		
    	    boxNazione.setItems(FXCollections.observableList(model.creaGrafo(anno)));
    		
    	}catch(NumberFormatException  nfe) {
    		txtResult.appendText("Si prega di inserire un numero compreso tra il 1816 e il 2006");
    	}
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    	txtResult.clear();
    	
    	if(boxNazione.getValue()!=null) {
    		
    		txtResult.appendText(model.simulate(boxNazione.getValue()));
    		
    	}
    	else {
    		txtResult.appendText("Devi scegliere una nazione");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		}
}
