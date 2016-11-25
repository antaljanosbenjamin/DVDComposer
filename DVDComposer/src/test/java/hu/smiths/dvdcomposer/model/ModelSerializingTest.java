package hu.smiths.dvdcomposer.model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ModelSerializingTest {

	private Model model;

	private DiscGroup blueRay;

	private DiscGroup cd;

	@Rule
	public TemporaryFolder tempFolder1 = new TemporaryFolder();

	@Rule
	public TemporaryFolder tempFolder2 = new TemporaryFolder();

	@Rule
	public TemporaryFolder containerFolder = new TemporaryFolder();

	@Before
	public void before() {
		model = new ConcreteModel();
		blueRay = DiscGroup.createFinite("BlueRay", 25000000000L, 45);
		model.addDiscGroup(blueRay);
		cd = DiscGroup.createInfinite("CD", 700000000L);
		model.addDiscGroup(cd);
		model.addFolder(tempFolder1.getRoot());
		model.addFolder(tempFolder2.getRoot());
	}

	private void serializeModel(File fileToSerialize) throws IOException {
		FileOutputStream fileStreamToSerialize = new FileOutputStream(fileToSerialize);
		ObjectOutputStream streamToSerialize = new ObjectOutputStream(fileStreamToSerialize);
		streamToSerialize.writeObject(model);
		streamToSerialize.close();
	}

	private Model readModel(File fileToRead) throws IOException, ClassNotFoundException {
		FileInputStream fileStreamToRead = new FileInputStream(fileToRead);
		ObjectInputStream streamToRead = new ObjectInputStream(fileStreamToRead);
		Model readedModel = (Model) streamToRead.readObject();
		streamToRead.close();
		return readedModel;
	}
	
	private void checkReadedModelContainsAllStuff(Model readedModel){
		assertTrue(readedModel.getDiscGroups().size() >= 2);
		for(DiscGroup group : model.getDiscGroups()){
			assertTrue(readedModel.getDiscGroups().contains(group));
		}

		assertTrue(readedModel.getFolders().size() >= 2);
		for(File folder : model.getFolders()){
			assertTrue(readedModel.getFolders().contains(folder));
		}		
	}

	@Test
	public void simpleSerializeTest() throws IOException, ClassNotFoundException {
		File fileToSerialize = containerFolder.newFile();
		serializeModel(fileToSerialize);

		Model readedModel = readModel(fileToSerialize);
		
		checkReadedModelContainsAllStuff(readedModel);
	}

}
