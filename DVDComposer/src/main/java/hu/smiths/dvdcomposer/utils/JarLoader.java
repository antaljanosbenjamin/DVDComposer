package hu.smiths.dvdcomposer.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class JarLoader {

	public void load() {
		File jar = new File("algorithm.jar");
		try {
			URL url = jar.toURI().toURL();

			URLClassLoader child = new URLClassLoader(new URL[] { url }, this.getClass().getClassLoader());
			Class<?> classToLoad = Class.forName("dummy.AlgorithmClass", true, child);
			Method method = classToLoad.getDeclaredMethod ("getSimple");
			Object instance = classToLoad.newInstance();
			
			Object result = method.invoke(instance);
			System.out.println("Method invoke returned with: " + result);
			
		} catch (IOException | IllegalArgumentException | SecurityException | IllegalAccessException
				| ClassNotFoundException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
