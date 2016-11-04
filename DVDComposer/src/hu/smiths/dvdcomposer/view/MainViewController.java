package hu.smiths.dvdcomposer.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{

	@FXML
	TextField wasdField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void btnSubmit(ActionEvent actionEvent) {
    }

    public void setVotingEnabledState(boolean enabled) {
    }


}
