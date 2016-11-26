package hu.smiths.dvdcomposer.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import hu.smiths.dvdcomposer.model.ModelManager;
import hu.smiths.dvdcomposer.model.Result;
import hu.smiths.dvdcomposer.model.exceptions.InvalidResultException;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ResultController extends ModelController {

	private Result result;
	
	@FXML
	TreeView<File> treeView;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			result = ModelManager.getModel().generateResult();
		} catch (InvalidResultException e) {
			e.printStackTrace();
		}
		fillTree();
	}
	
	private void fillTree() {
		TreeItem<File> root = new TreeItem<>(new File(result.getDiscs().iterator().next().getGroup().getName()));
		root.setExpanded(true);
		treeView.setRoot(root);
	}
	
}
