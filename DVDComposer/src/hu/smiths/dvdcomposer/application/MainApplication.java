package hu.smiths.dvdcomposer.application;

import hu.smiths.dvdcomposer.utils.JarLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainScene.fxml"));
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add("/css/composer.css");
        
        primaryStage.setTitle("DVD Composer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
		JarLoader loader = new JarLoader();
//		loader.load();		

		System.out.println("Succes!");
		launch(args);
	}

}
