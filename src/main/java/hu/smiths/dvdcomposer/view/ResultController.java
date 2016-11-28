package hu.smiths.dvdcomposer.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import hu.smiths.dvdcomposer.model.ModelManager;
import hu.smiths.dvdcomposer.model.Result;
import hu.smiths.dvdcomposer.model.exceptions.CannotCreateISOFile;
import hu.smiths.dvdcomposer.model.exceptions.InvalidResultException;
import hu.smiths.dvdcomposer.utils.ISOOptions;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

@SuppressWarnings("restriction")
public class ResultController extends ModelController {

	private Result result;
	
	@FXML
	TreeView<File> treeView;
	@FXML
	TextField isoPrefix;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			result = ModelManager.getModel().generateResult();
		} catch (InvalidResultException e) {
			e.printStackTrace();
			showAlert(AlertType.ERROR, "Not enough space on the drives, please adjust the parameters!");
		}
		fillTree();
	}
	
	private void fillTree() {
		TreeItem<File> root = new TreeItem<>(new File(""));
		result.getDiscs().forEach(disc -> {
			TreeItem<File> discItem = new TreeItem<>(new File(disc.getGroup().getName()));
			root.getChildren().add(discItem);
			discItem.setExpanded(true);
			disc.getFolders().forEach(folder -> {
				TreeItem<File> item = new TreeItem<>(folder);
				discItem.getChildren().add(item);
			});
		});
		root.setExpanded(true);
		
		
		treeView.setRoot(root);
	}
	
	public void prev(ActionEvent event) {
		SceneManager.getInstance().changeScene("/fxml/algorithmChooserView.fxml");
	}
	
	public void generateIso(ActionEvent event) {
		if (isoPrefix.getText().equals("")){
			showAlert(AlertType.WARNING, "Prefix is needed to generate the ISO(s)!");
			return;
		}
		ISOOptions options = new ISOOptions();
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Open output location");
		File selectedFolder = chooser.showDialog(SceneManager.getInstance().getPrimaryStage());
		
		Task<Void> task = new Task<Void>() {
		    @Override
		    protected Void call() throws Exception {
		    	try {
					result.generateISOFiles(options);
					Platform.runLater(() ->showAlert(AlertType.INFORMATION, "ISO generation complete!"));
				} catch (CannotCreateISOFile e) {
					e.printStackTrace();
				}
				return null;
		    }
		};
		
		if (selectedFolder != null) {
			options.pathToTargetDirectory = selectedFolder.getPath();
			options.prefix = isoPrefix.getText();
			new Thread(task).start();
			System.out.println("asfasf");
		}
	}
	
}
