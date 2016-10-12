package hu.smiths.dvdcomposer.application;

import hu.smiths.dvdcomposer.utils.JarLoader;

public class MainApplication {

	public static void main(String[] args) {
		JarLoader loader = new JarLoader();
		loader.load();		

		System.out.println("Succes!");
	}

}
