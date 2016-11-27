package hu.smiths.dvdcomposer.view;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import hu.smiths.dvdcomposer.model.ModelManager;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;

public class FileChooserController extends ModelController {

	@FXML
	private TableView<File> tableFolderView;
	@FXML
	TableColumn<File, String> folderNameCol;
	@FXML
	TableColumn<File, String> folderPathCol;
	@FXML
	TableColumn<File, Number> folderSizeCol;

	private final ObservableList<File> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		data.addAll(ModelManager.getModel().getFolders());

		folderNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		folderPathCol
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPath().toString()));
		folderSizeCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().isDirectory() ?
				FileUtils.sizeOfDirectory(cellData.getValue()) : cellData.getValue().length()));

		tableFolderView.setItems(data);
	}

	public void openFileChooser(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Open folder");
		File selectedFolder = chooser.showDialog(SceneManager.getInstance().getPrimaryStage());
		if (selectedFolder != null) {
			data.addAll(Arrays.asList(selectedFolder.listFiles()).stream().filter(f -> f.isDirectory())
					.collect(Collectors.toSet()));
		}
	}

	public void emptyFolderList(ActionEvent event) {
		data.clear();
	}

	public void next(ActionEvent event) {
		if (!data.isEmpty()) {
			refreshModel();
			SceneManager.getInstance().changeScene("/fxml/algorithmChooserView.fxml");
		} else {
			showAlert(AlertType.WARNING, "Please select at least one folder to continue.");
		}
	}

	public void prev(ActionEvent event) {
		refreshModel();
		SceneManager.getInstance().changeScene("/fxml/mainView.fxml");
	}

	private void refreshModel() {
		Set<File> fileSet = new HashSet<>();
		data.forEach(f -> fileSet.add(f));
		ModelManager.getModel().setFolders(fileSet);
	}

	public void saveModelAction(ActionEvent event) {
		refreshModel();
		saveModel(event);
	}

	public void loadModelAction(ActionEvent event) {
		loadModel(event);
		data.setAll(ModelManager.getModel().getFolders());
	}

}
