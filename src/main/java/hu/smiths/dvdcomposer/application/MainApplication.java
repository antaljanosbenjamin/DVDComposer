package hu.smiths.dvdcomposer.application;

import hu.smiths.dvdcomposer.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
    public void start(Stage primaryStage) throws Exception{
		SceneManager.getInstance().setPrimaryStage(primaryStage);
        primaryStage.setTitle("DVD Composer");
        SceneManager.getInstance().changeScene("/fxml/mainView.fxml");
        
    }
	
	public static void main(String[] args) {
		System.out.println("Succes!");
		launch(args);
	}

}
