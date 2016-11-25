package hu.smiths.dvdcomposer.model;

import java.io.File;

public class ModelManager {

	private static Model model;

	public static Model getModel() {
		return model;
	}

	public static void setModel(Model model) {
		ModelManager.model = model;
	}
	
	public static void save(File output) {
		
	}
	
	public static void load(File input) {
		
	}
}
