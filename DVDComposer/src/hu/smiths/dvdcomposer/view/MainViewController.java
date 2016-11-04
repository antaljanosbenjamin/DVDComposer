package hu.smiths.dvdcomposer.view;

import java.net.URL;
import java.util.ResourceBundle;

import hu.smiths.dvdcomposer.view.extensions.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class MainViewController implements Initializable{

	@FXML
	NumberTextField cdNumberField;
	@FXML
	NumberTextField dvdNumberField;
	@FXML
	Button someButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void someFunction(ActionEvent event) {
    	if (validate()) {
    		System.out.println("CD: " + cdNumberField.getText() + " - DVD: " + dvdNumberField.getText());
    	} else {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Lol.");
    		alert.setContentText("fosarc?");
    		alert.setHeaderText("jap.");
    		alert.showAndWait().filter(response -> response == ButtonType.OK);
    	}
    }

	private boolean validate() {
		return !cdNumberField.getText().isEmpty() && !dvdNumberField.getText().isEmpty();
		
	}

}
