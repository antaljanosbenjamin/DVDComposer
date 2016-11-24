package hu.smiths.dvdcomposer.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

public class FileChooserViewController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public void openFileChooser(ActionEvent event) {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
	    chooser.showOpenDialog(SceneManager.getInstance().getPrimaryStage());
	}
}
