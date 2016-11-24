package hu.smiths.dvdcomposer.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
	
	public static SceneManager instance;
	
	private Parent parent;
	private Stage primaryStage;
	
	protected SceneManager() {
		
	};
	
	public static SceneManager getInstance() {
		if (instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}
	
	public void changeScene(String url) {
		try {
			parent = FXMLLoader.load(getClass().getResource(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    Scene scene = new Scene(SceneManager.getInstance().getParent(), 640, 480);
        scene.getStylesheets().add("/css/composer.css");
	    primaryStage.setScene(scene);
	
	    primaryStage.show();
	}
	

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

}
