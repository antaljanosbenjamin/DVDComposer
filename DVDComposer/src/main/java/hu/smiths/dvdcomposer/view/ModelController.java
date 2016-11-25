package hu.smiths.dvdcomposer.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import hu.smiths.dvdcomposer.model.ModelManager;
import hu.smiths.dvdcomposer.model.exceptions.CannotLoadModel;
import hu.smiths.dvdcomposer.model.exceptions.CannotSaveModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ModelController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void saveModel(ActionEvent event) {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Save model");
	    chooser.setSelectedExtensionFilter(new ExtensionFilter("Serialized files (*.ser)", "*.ser"));
	    File selectedFile = chooser.showSaveDialog(SceneManager.getInstance().getPrimaryStage());
	    
	    
		try {
			ModelManager.saveToFile(selectedFile);
		} catch (CannotSaveModel e) {
			e.printStackTrace();
		};
	}

	public void loadModel(ActionEvent event) {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open model");
	    chooser.setSelectedExtensionFilter(new ExtensionFilter("Serialized files (*.ser)", "*.ser"));
	    File selectedFile = chooser.showOpenDialog(SceneManager.getInstance().getPrimaryStage());
	    
	    try {
			ModelManager.loadAndStoreFromFile(selectedFile);
		} catch (CannotLoadModel e) {
			e.printStackTrace();
		}
	}
	
	

}
