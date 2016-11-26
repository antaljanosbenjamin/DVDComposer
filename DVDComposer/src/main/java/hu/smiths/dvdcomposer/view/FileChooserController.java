package hu.smiths.dvdcomposer.view;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import hu.smiths.dvdcomposer.model.ModelManager;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
		folderSizeCol.setCellValueFactory(cellData -> new SimpleLongProperty(FileUtils.sizeOfDirectory(cellData.getValue())));

		tableFolderView.setItems(data);
	}

	public void openFileChooser(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Open File");
		File selectedFolder = chooser.showDialog(SceneManager.getInstance().getPrimaryStage());
		if (selectedFolder != null) {
			data.addAll(selectedFolder.listFiles());
		}
	}

	public void emptyFolderList(ActionEvent event) {
		data.clear();
	}

	public void next(ActionEvent event) {
		refreshModel();
		SceneManager.getInstance().changeScene("/fxml/algorithmChooserView.fxml");
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
