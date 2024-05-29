/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Bilancio;
import it.polito.tdp.itunes.model.Model;
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

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	Album a =cmbA1.getValue();
    	if (a== null) {
    		txtResult.setText("Scegli un album");
    		return;
    	}
    	List <Bilancio> bilancio = model.getAdiacenti(a);
    	txtResult.setText("Stampa successori dell'album: "+a+"\n");
    	for (Bilancio b : bilancio) {
    		txtResult.appendText(b+ "\n");
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	String input = txtX.getText();
    	if (input == null) {
    		txtResult.setText("Inserisci un valore ");
    		return;
    	}
    	
    	int n=0;
 
    	try {
    		n = Integer.parseInt(input);
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserisci un numero");
    	}
    	Album a1 = cmbA1.getValue();
    	Album a2 = cmbA2.getValue();
    	if (a1 == null || a2 ==null) {
    		txtResult.setText("Scegli i due album");
    		return;
    	}
    	
    	List <Album> cammino = model.getPercorso(a1, a2, n);
    	if (cammino.isEmpty()) {
    		txtResult.setText("Non esistono cammini");
    		return;
    	}
    	txtResult.appendText("Stampa il cammino tra "+ a1 + " e "+ a2+ "\n");
    	for (Album a : cammino) {
    		txtResult.appendText(a+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String input = txtN.getText();
    	
    	if (input == null) {
    		txtResult.setText("Inserisci un valore ");
    		return;
    	}
    	
    	int n=0;
    	
    	try {
    		n = Integer.parseInt(input);
    		
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserisci un numero");
    	}
    	
    	model.creaGrafo(n);
    	
		txtResult.setText("Grafo creto!"+"\n"+ "#Vertici: "+ model.getNumVertici() + "\n"+ "#Archi: "+ model.getNumArchi());
    	
		cmbA1.getItems().addAll(model.getAlbum());
		cmbA2.getItems().addAll(model.getAlbum());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        
        
    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
