package hu.smiths.dvdcomposer.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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

	OuterAlgorithm algorithm;

	File selectedJar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (algorithm != null) {
			algorithm = (OuterAlgorithm) ModelManager.getModel().getAlgorithm();
		}
	}

	public void chooseAlgorithm(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		selectedJar = chooser.showOpenDialog(SceneManager.getInstance().getPrimaryStage());
		if (selectedJar != null) {
			choosenAlgorithmLabel.setText(selectedJar.getName());
		}
	}

	public void generateAlgorithm(ActionEvent event) {
		if (classNameField != null && selectedJar != null) {
			try {
				algorithm = OuterAlgorithm.createFromJarAndClassFQN(selectedJar, classNameField.getText());
			} catch (CannotLoadAlgorithmClass e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert!");
			alert.setHeaderText("Missing prameter(s)! Please load the Jar, and give the class name,"
					+ " before generating the algorithm!");
			alert.showAndWait().filter(response -> response == ButtonType.OK);
		}
	}

	public void next(ActionEvent event) {
		refreshModel();
		SceneManager.getInstance().changeScene("/fxml/algorithChooserView.fxml");
	}

	public void prev(ActionEvent event) {
		refreshModel();
		SceneManager.getInstance().changeScene("/fxml/mainView.fxml");
	}

	private void refreshModel() {
		ModelManager.getModel().setAlgorithm(algorithm);
	}

	public void saveModelAction(ActionEvent event) {
		refreshModel();
		saveModel(event);
	}

	public void loadModelAction(ActionEvent event) {
		loadModel(event);
	}
}
