package hu.smiths.dvdcomposer.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InfiniteDiscGroupasHaveMoreThanTest {

	private String groupName;

	private Long groupSizeInBytes;

	private List<Integer> testValuesForCount;

	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] { { "BlueRay", 25000000L,
				Arrays.asList(new Integer[] { 100, 200, 700, 800, 1000000, 1000000000, 2000000000 }) } });
	}

	public InfiniteDiscGroupasHaveMoreThanTest(String groupName, Long groupSizeInBytes,
			List<Integer> testValuesForCount) {
		this.groupName = groupName;
		this.groupSizeInBytes = groupSizeInBytes;
		this.testValuesForCount = testValuesForCount;
	}

	@Test
	public void hasAtLeastTests() {
		DiscGroup group = DiscGroup.createInfinite(groupName, groupSizeInBytes);
		for (int i = 0; i < testValuesForCount.size(); i++) {
			assertEquals(Boolean.TRUE, group.haveMoreThan(testValuesForCount.get(i)));
		}

	}
}
