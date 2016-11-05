package hu.smiths.dvdcomposer.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hu.smiths.dvdcomposer.view.extensions.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewController implements Initializable{

	@FXML
	GridPane gridPane;
	@FXML
	NumberTextField cdQuantityField;
	@FXML
	NumberTextField dvdQuantityField;
	@FXML
	NumberTextField brQuantityField;
	@FXML
	TextField otherContainerNameField;
	@FXML
	NumberTextField otherContainerSizeField;
	@FXML
	NumberTextField otherContainerQuantityField;
	@FXML
	Button newContainer;
	@FXML
	Button someButton;
	
	
	int bonusContainerNumber = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void newConatinerAction(ActionEvent event) {
    	bonusContainerNumber++;
    	System.out.println("wa");
    }

    public void someFunction(ActionEvent event) {
    	if (validate()) {
		    SceneManager.getInstance().changeScene("/fxml/fileChooser.fxml");
    	} else {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Alert!");
    		alert.setContentText("");
    		alert.setHeaderText("Missing prameter(s)! Please fill in every cell, or leave them empty");
    		alert.showAndWait().filter(response -> response == ButtonType.OK);
    	}
    }

	private boolean validate() {
		return (!otherContainerNameField.getText().isEmpty() && !otherContainerSizeField.getText().isEmpty()
					&& !otherContainerQuantityField.getText().isEmpty()) ||
				(otherContainerNameField.getText().isEmpty() && otherContainerSizeField.getText().isEmpty()
					&& otherContainerQuantityField.getText().isEmpty());
		
	}

}
