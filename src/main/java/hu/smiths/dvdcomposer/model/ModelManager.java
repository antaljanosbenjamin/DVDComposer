package hu.smiths.dvdcomposer.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import hu.smiths.dvdcomposer.model.exceptions.CannotLoadModel;
import hu.smiths.dvdcomposer.model.exceptions.CannotSaveModel;

public class ModelManager {

	private static Model model;

	public static Model getModel() {
		return model;
	}

	public static void setModel(Model model) {
		ModelManager.model = model;
	}

	public static void saveToFile(File output) throws CannotSaveModel {
		if (model == null)
			throw new CannotSaveModel("Cannot save model, because it is null!");
		try (FileOutputStream fileStreamToSerializeTo = new FileOutputStream(output)) {
			try (ObjectOutputStream streamToSerializeTo = new ObjectOutputStream(fileStreamToSerializeTo)) {
				streamToSerializeTo.writeObject(model);
				streamToSerializeTo.close();
			}
		} catch (IOException e) {
			throw new CannotSaveModel("Error occurend during the saving!", e);
		}
	}

	public static Model loadAndStoreFromFile(File input) throws CannotLoadModel {
		try (FileInputStream fileStreamToReadFrom = new FileInputStream(input)) {
			try (ObjectInputStream streamToReadFrom = new ObjectInputStream(fileStreamToReadFrom)) {
				model = (Model) streamToReadFrom.readObject();
				streamToReadFrom.close();
				return model;
			}
		} catch (IOException | ClassNotFoundException e) {
			throw new CannotLoadModel("Error occurend during the loading!", e);
		}
	}
}
