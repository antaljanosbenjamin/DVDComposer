package hu.smiths.dvdcomposer.model.algorithm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import hu.smiths.dvdcomposer.model.Disc;
import hu.smiths.dvdcomposer.model.exceptions.CannotFindValidAssignmentException;
import hu.smiths.dvdcomposer.model.exceptions.CannotLoadAlgorithmClass;

public class OuterAlgorithm implements Algorithm {

	private Algorithm algorithm;

	private OuterAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public static OuterAlgorithm createFromJarAndClassFQN(File jar, String classFQN) throws CannotLoadAlgorithmClass {
		return new OuterAlgorithm(load(jar, classFQN));
	}

	private static Algorithm load(File jar, String classFQN) throws CannotLoadAlgorithmClass {
		try {

			Class<?> loadedClass = getClassFromJar(jar, classFQN);

			return getNewAlgorithmInstance(loadedClass);

		} catch (IOException | IllegalArgumentException | SecurityException | IllegalAccessException
				| ClassNotFoundException | InstantiationException e) {
			throw new CannotLoadAlgorithmClass("Cannot load " + classFQN + " from " + jar.getAbsolutePath(), e);
		}
	}

	private static Class<?> getClassFromJar(File jar, String classFQN)
			throws MalformedURLException, ClassNotFoundException {

		URL url = jar.toURI().toURL();
		URLClassLoader loader = new URLClassLoader(new URL[] { url }, OuterAlgorithm.class.getClassLoader());
		return Class.forName(classFQN, true, loader);
	}

	private static Algorithm getNewAlgorithmInstance(Class<?> clazz)
			throws CannotLoadAlgorithmClass, InstantiationException, IllegalAccessException {

		if (Algorithm.class.isAssignableFrom(clazz)) {
			return (Algorithm) clazz.newInstance();
		} else {
			throw new CannotLoadAlgorithmClass(
					clazz.toGenericString() + " doesn't implements " + Algorithm.class.toGenericString() + "!");
		}
	}

	@Override
	public Set<Disc> generate(Input input) throws CannotFindValidAssignmentException {
		try {
			return algorithm.generate(input);
		} catch (Throwable t) {
			throw new CannotFindValidAssignmentException("The outer algorithm could not find a valid assigment!", t);
		}
	}
	
	public void changeAlgorithm(File jar, String classFQN) throws CannotLoadAlgorithmClass{
		algorithm = load(jar, classFQN);
	}
}
