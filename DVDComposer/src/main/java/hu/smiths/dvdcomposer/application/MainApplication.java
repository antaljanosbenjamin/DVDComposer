package hu.smiths.dvdcomposer.application;

import hu.smiths.dvdcomposer.utils.JarLoader;
import hu.smiths.dvdcomposer.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
    public void start(Stage primaryStage) throws Exception{
		SceneManager.getInstance().setPrimaryStage(primaryStage);
        primaryStage.setTitle("DVD Composer");
        SceneManager.getInstance().changeScene("/fxml/mainScene.fxml");
        
    }
	
	public static void main(String[] args) {
//		JarLoader loader = new JarLoader();
//		loader.load();		

		System.out.println("Succes!");
		launch(args);
	}

}
