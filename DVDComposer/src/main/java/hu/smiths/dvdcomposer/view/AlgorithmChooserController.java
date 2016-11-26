package hu.smiths.dvdcomposer.view;

import java.io.File;

import hu.smiths.dvdcomposer.model.ModelManager;
import hu.smiths.dvdcomposer.model.algorithm.OuterAlgorithm;
import hu.smiths.dvdcomposer.model.exceptions.CannotLoadAlgorithmClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class AlgorithmChooserController extends ModelController {

	@FXML
	Label choosenAlgorithmLabel;

	@FXML
	TextField classNameField;

	File selectedJar;

	public void chooseAlgorithm(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		selectedJar = chooser.showOpenDialog(SceneManager.getInstance().getPrimaryStage());
		if (selectedJar != null) {
			choosenAlgorithmLabel.setText(selectedJar.getName());
		}
	}

	private void generateAlgorithm() {
		try {
			ModelManager.getModel()
					.setAlgorithm(OuterAlgorithm.createFromJarAndClassFQN(selectedJar, classNameField.getText()));
		} catch (CannotLoadAlgorithmClass e) {
			e.printStackTrace();
		}
	}

	public void next(ActionEvent event) {
		if (classNameField != null && selectedJar != null) {
			generateAlgorithm();
			SceneManager.getInstance().changeScene("/fxml/resultView.fxml");
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert!");
			alert.setHeaderText("Missing prameter(s)! Please load the Jar, and give the class name,"
					+ " before generating the algorithm!");
			alert.showAndWait().filter(response -> response == ButtonType.OK);
		}
	}

	public void prev(ActionEvent event) {
		SceneManager.getInstance().changeScene("/fxml/mainView.fxml");
	}

	public void saveModelAction(ActionEvent event) {
		saveModel(event);
	}

	public void loadModelAction(ActionEvent event) {
		loadModel(event);
	}
}
