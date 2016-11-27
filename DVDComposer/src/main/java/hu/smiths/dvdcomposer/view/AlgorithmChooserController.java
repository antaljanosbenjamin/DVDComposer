package hu.smiths.dvdcomposer.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import hu.smiths.dvdcomposer.model.ModelManager;
import hu.smiths.dvdcomposer.model.algorithm.GreedyAlgorithm;
import hu.smiths.dvdcomposer.model.algorithm.OuterAlgorithm;
import hu.smiths.dvdcomposer.model.exceptions.CannotLoadAlgorithmClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class AlgorithmChooserController extends ModelController {

	@FXML
	Label choosenAlgorithmLabel;
	@FXML
	CheckBox innerAlgorithm;
	@FXML
	TextField classNameField;

	File selectedJar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		innerAlgorithm.setIndeterminate(false);
		innerAlgorithm.setSelected(true);
	}

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
		if ((classNameField != null && selectedJar != null) || innerAlgorithm.isSelected()) {
			if (innerAlgorithm.isSelected()) {
				ModelManager.getModel().setAlgorithm(new GreedyAlgorithm());
			} else {
				generateAlgorithm();
			}
			SceneManager.getInstance().changeScene("/fxml/resultView.fxml");
		} else {
			showAlert(AlertType.WARNING, "Missing prameter(s)! Please load the Jar, and give the class name, "
					+ "or use the inner algorithm before generating the result!");
		}
	}

	public void prev(ActionEvent event) {
		SceneManager.getInstance().changeScene("/fxml/fileChooserView.fxml");
	}

	public void saveModelAction(ActionEvent event) {
		saveModel(event);
	}

	public void loadModelAction(ActionEvent event) {
		loadModel(event);
	}
}
