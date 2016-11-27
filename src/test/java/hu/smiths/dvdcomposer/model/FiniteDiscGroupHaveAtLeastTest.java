package hu.smiths.dvdcomposer.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FiniteDiscGroupHaveAtLeastTest {

	private String groupName;

	private Long groupSizeInBytes;

	private Integer count;

	private List<Integer> testValuesForCount;

	private List<Boolean> expectedReturnValues;

	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "BlueRay", 25000000L, 15, Arrays.asList(new Integer[] { 10, 14, 15, 16, 20 }),
						Arrays.asList(new Boolean[] { true, true, true, false, false }) },
				{ "DVD", 47000000L, 160000000,
						Arrays.asList(new Integer[] { 10, 100000000, 159999999, 160000000, 160000001, 280000000 }),
						Arrays.asList(new Boolean[] { true, true, true, true, false, false }) } });
	}

	public FiniteDiscGroupHaveAtLeastTest(String groupName, Long groupSizeInBytes, Integer count,
			List<Integer> testValuesForCount, List<Boolean> expectedReturnValues) {
		this.groupName = groupName;
		this.groupSizeInBytes = groupSizeInBytes;
		this.count = count;
		this.testValuesForCount = testValuesForCount;
		this.expectedReturnValues = expectedReturnValues;
	}

	@Test
	public void hasAtLeastTests() {
		DiscGroup group = DiscGroup.createFinite(groupName, groupSizeInBytes, count);
		for (int i = 0; i < testValuesForCount.size(); i++) {
			assertEquals(expectedReturnValues.get(i), group.haveAtLeast(testValuesForCount.get(i)));
		}

	}
}
