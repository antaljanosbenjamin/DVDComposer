package hu.smiths.dvdcomposer.model;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import hu.smiths.dvdcomposer.model.algorithm.Algorithm;
import hu.smiths.dvdcomposer.model.algorithm.GreedyAlgorithm;
import hu.smiths.dvdcomposer.model.exceptions.InvalidResultException;

@RunWith(Parameterized.class)
public class GeneratingTest {

	Model model;

	Algorithm algorithm;

	int testCount;

	boolean addFirst;
	Long firstSize;
	
	boolean addSecond;
	Long secondSize;
	
	boolean addThird;
	Long thirdSize;
	
	boolean addFourth;
	Long fourthSize;
	
	boolean addFifth;
	Long fifthSize;

	List<Integer> sizeAndCount;

	@Rule
	public TemporaryFolder firstFolder = new TemporaryFolder();

	@Rule
	public TemporaryFolder secondFolder = new TemporaryFolder();

	@Rule
	public TemporaryFolder thirdFolder = new TemporaryFolder();

	@Rule
	public TemporaryFolder fourthFolder = new TemporaryFolder();

	@Rule
	public TemporaryFolder fifthFolder = new TemporaryFolder();

	// @formatter:off
	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ 1, true, 25000L, true, 30000L, true, 35000L, true, 40000L, true, 5000L,
						Arrays.asList(new Integer[] { 35000, 10, 55000, 1, 65000, 1 }) },
				{ 2, true, 5000L, true, 40000L, true, 35000L, true, 30000L, true, 25000L,
						Arrays.asList(new Integer[] { 35000, 10, 55000, 1, 65000, 1 }) }

		});
	}
	// @formatter:on

	@Before
	public void before() throws FileNotFoundException, IOException {
		model = new ConcreteModel();
		algorithm = new GreedyAlgorithm();
		model.setAlgorithm(algorithm);
		
		if (addFirst) {
			setFolderToSpecificSize(firstFolder, firstSize);
			model.addFolder(firstFolder.getRoot());
		}
		if (addSecond) {
			setFolderToSpecificSize(secondFolder, secondSize);
			model.addFolder(secondFolder.getRoot());
		}
		if (addThird) {
			setFolderToSpecificSize(thirdFolder, thirdSize);
			model.addFolder(thirdFolder.getRoot());
		}
		if (addFourth) {
			setFolderToSpecificSize(fourthFolder, fourthSize);
			model.addFolder(fourthFolder.getRoot());
		}
		if (addFifth) {
			setFolderToSpecificSize(fifthFolder, fifthSize);
			model.addFolder(fifthFolder.getRoot());
		}

		for (int i = 0; i < sizeAndCount.size(); i += 2) {
			int size = sizeAndCount.get(i);
			int count = sizeAndCount.get(i + 1);
			if (size == -1)
				model.addDiscGroup(DiscGroup.createInfinite("Disk_" + size, (long) size));
			else
				model.addDiscGroup(DiscGroup.createFinite("Disk_" + size, (long) size, count));
		}
	}

	public GeneratingTest(int testCount, boolean addFirst, Long firstSize, boolean addSecond, Long secondSize,
			boolean addThird, Long thirdSize, boolean addFourth, Long fourthSize, boolean addFifth, Long fifthSize,
			List<Integer> sizeAndCount) throws FileNotFoundException, IOException {

		this.testCount = testCount;
		
		this.addFirst =  addFirst;
		this.firstSize =  firstSize;
		
		this.addSecond =  addSecond;
		this.secondSize =  secondSize;
		
		this.addThird =  addThird;
		this.thirdSize =  thirdSize;
		
		this.addFourth =  addFourth;
		this.fourthSize =  fourthSize;
		
		this.addFifth =  addFifth;
		this.fifthSize =  fifthSize;
		
		this.sizeAndCount = sizeAndCount;
		
	}

	public void setFolderToSpecificSize(TemporaryFolder folder, Long size) throws FileNotFoundException, IOException {
		RandomAccessFile contentFile = new RandomAccessFile(folder.newFile("content"), "rw");
		contentFile.setLength(size);
		contentFile.close();
	}

	@Test
	public void multipleTests() throws InvalidResultException {
		switch (testCount) {
		case 1:
			firstTest();
			break;
		case 2:
			secondTest();
			break;
		}
	}

	public void firstTest() throws InvalidResultException {
		Result result = model.generateResult();
		assertTrue(result.getDiscs().size() == 3);
	}

	public void secondTest() throws InvalidResultException {
		Result result = model.generateResult();
		assertTrue(result.getDiscs().size() == 3);
	}
}
