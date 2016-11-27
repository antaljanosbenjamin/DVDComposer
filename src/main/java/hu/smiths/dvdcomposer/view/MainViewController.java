package hu.smiths.dvdcomposer.view;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import hu.smiths.dvdcomposer.model.ConcreteModel;
import hu.smiths.dvdcomposer.model.DiscGroup;
import hu.smiths.dvdcomposer.model.ModelManager;
import hu.smiths.dvdcomposer.view.extensions.NumberTextField;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

public class MainViewController extends ModelController {

	private final Long CD_SIZE = 734003200l;
	private final Long DVD_SIZE = 5046586573l;
	private final Long BR_SIZE = 26843545600l;
	@FXML
	Button newDiscGroupButton;
	@FXML
	TextField newDiscNameField;
	@FXML
	NumberTextField newDiscSizeField;
	@FXML
	NumberTextField newDiscQuantityField;
	@FXML
	private TableView<DiscGroup> tableView;
	@FXML
	TableColumn<DiscGroup, String> containerNameCol;
	@FXML
	TableColumn<DiscGroup, Number> containerSizeCol;
	@FXML
	TableColumn<DiscGroup, Number> containerQuantityCol;
	@FXML
	TableColumn containerInfinityCol;
	@FXML
	TableColumn<DiscGroup, DiscGroup> containerDeleteCol;

	private final ObservableList<DiscGroup> data = FXCollections.observableArrayList();

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeModel();
		data.addAll(ModelManager.getModel().getDiscGroups());

		containerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		containerSizeCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getSizeInBytes()));
		containerQuantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()));

		containerQuantityCol
				.setCellFactory(TextFieldTableCell.<DiscGroup, Number>forTableColumn(new NumberStringConverter()));
		containerQuantityCol.setOnEditCommit((CellEditEvent<DiscGroup, Number> t) -> {
			if (t.getNewValue() == null || (Long) t.getNewValue() <1l) {
				showAlert(AlertType.ERROR, "The container count has to be at least 1!");
				t.getTableView().refresh();
				return;
			}
			((DiscGroup) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCount(t.getNewValue().intValue());
			t.getTableView().refresh();
		});
		
		containerInfinityCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DiscGroup, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<DiscGroup, CheckBox> arg0) {
            	DiscGroup user = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(user.getInfinity());



                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {
                    	if(user.getInfinity()) {
                    		user.setCount(1);
                    	} else {
                            user.setCountToInfinite();
                    	}
                        arg0.getTableView().refresh();
                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }


        });
		containerDeleteCol.setCellValueFactory(
			    param -> new ReadOnlyObjectWrapper<>(param.getValue())
			);
		containerDeleteCol.setCellFactory(param -> new TableCell<DiscGroup, DiscGroup>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(DiscGroup dg, boolean empty) {
				super.updateItem(dg, empty);

				if (dg == null) {
					setGraphic(null);
					return;
				}

				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> getTableView().getItems().remove(dg));
			}
		});

		tableView.setEditable(true);
		tableView.setItems(data);

	}

	public void addNewDiscGroup(ActionEvent event) {
		if (validate()) {
			if (newDiscQuantityField.equals(0)) {
				data.add(DiscGroup.createInfinite(newDiscNameField.getText(),
						Long.parseLong(newDiscSizeField.getText())));
			} else {
				data.add(DiscGroup.createFinite(newDiscNameField.getText(), Long.parseLong(newDiscSizeField.getText()),
						Integer.parseInt(newDiscQuantityField.getText())));
			}
			newDiscNameField.clear();
			newDiscSizeField.clear();
			newDiscQuantityField.clear();
		} else {
			showAlert(AlertType.WARNING, "Missing prameter(s)! Please fill in every cell, or leave them empty");
		}
	}

	private boolean validate() {
		return (!newDiscNameField.getText().isEmpty() && !newDiscQuantityField.getText().isEmpty()
				&& !newDiscSizeField.getText().isEmpty());
	}

	public void next(ActionEvent event) {
		if ( data.isEmpty()) {
			showAlert(AlertType.ERROR, "You have to give at least one container!");
			return;
		}
		refreshModel();
		SceneManager.getInstance().changeScene("/fxml/fileChooserView.fxml");
	}

	private void initializeModel() {
		if (ModelManager.getModel() == null) {
			ModelManager.setModel(new ConcreteModel());
			ModelManager.getModel().addDiscGroup(DiscGroup.createFinite("CD", CD_SIZE, 1));
			ModelManager.getModel().addDiscGroup(DiscGroup.createFinite("DVD", DVD_SIZE, 1));
			ModelManager.getModel().addDiscGroup(DiscGroup.createFinite("BR", BR_SIZE, 1));
		}
	}

	private void refreshModel() {
		Set<DiscGroup> discSet = new HashSet<>();
		data.forEach(d -> discSet.add(d));
		ModelManager.getModel().setDiscGroups(discSet);
	}

	public void saveModelAction(ActionEvent event) {
		refreshModel();
		saveModel(event);
	}
	
	public void loadModelAction(ActionEvent event) {
		loadModel(event);
		data.setAll(ModelManager.getModel().getDiscGroups());
	}

}
