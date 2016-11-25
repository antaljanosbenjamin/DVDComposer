package hu.smiths.dvdcomposer.view;

import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;

import hu.smiths.dvdcomposer.model.Disc;
import hu.smiths.dvdcomposer.model.DiscGroup;
import hu.smiths.dvdcomposer.view.extensions.NumberTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class MainViewController implements Initializable{

	private final Long CD_SIZE = 734003200l;
	private final Long DVD_SIZE = 5046586573l;
	private final Long BR_SIZE = 26843545600l;
	@FXML
	private TableView<DiscGroup> tableView;
	@FXML
	GridPane gridPane;
	@FXML
	Button newDiscGroupButton;
	@FXML
	TextField newDiscNameField;
	@FXML
	NumberTextField newDiscSizeField;
	@FXML
	NumberTextField newDiscQuantityField;
	@FXML
	TableColumn<DiscGroup, String> containerNameCol;
	@FXML
	TableColumn<DiscGroup, Number> containerSizeCol;
	@FXML
	TableColumn<DiscGroup, Number> containerQuantityCol;
	
	
	private final ObservableList<DiscGroup> data =
	        FXCollections.observableArrayList(
		            DiscGroup.createFinite("CD", CD_SIZE,0 ),
		            DiscGroup.createFinite("DVD", DVD_SIZE, 0),
		            DiscGroup.createFinite("BR", BR_SIZE,0 )
	        );
	
	int bonusContainerNumber = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	containerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    	containerSizeCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getSizeInBytes()));
    	containerQuantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()));
    	
    	containerQuantityCol.setCellFactory(TextFieldTableCell.<DiscGroup, Number>forTableColumn(new NumberStringConverter()));
		tableView.setEditable(true);
	    tableView.setItems(data);
	    
 
    }
    
    public void addNewDiscGroup(ActionEvent event) {
    	if (validate()) {
    		if (newDiscQuantityField.equals(0)) {
    		    data.add(DiscGroup.createInfinite(newDiscNameField.getText(), Long.parseLong(newDiscSizeField.getText())));
    		}else {
    		    data.add(DiscGroup.createFinite(newDiscNameField.getText(),
    		    		Long.parseLong(newDiscSizeField.getText()), Integer.parseInt(newDiscQuantityField.getText())));
    		}
    		newDiscNameField.clear();
    		newDiscSizeField.clear();
    		newDiscQuantityField.clear();
    	} else {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Alert!");
    		alert.setContentText("");
    		alert.setHeaderText("Missing prameter(s)! Please fill in every cell, or leave them empty");
    		alert.showAndWait().filter(response -> response == ButtonType.OK);
    	}
    }

	private boolean validate() {
		return (!newDiscNameField.getText().isEmpty() && !newDiscQuantityField.getText().isEmpty()
					&& !newDiscSizeField.getText().isEmpty());
	}
	
}
