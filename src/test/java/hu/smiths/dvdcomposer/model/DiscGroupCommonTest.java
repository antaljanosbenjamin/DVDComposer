package hu.smiths.dvdcomposer.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DiscGroupCommonTest {

	private DiscGroup blueRayGroup;

	@Before
	public void before() {
		blueRayGroup = DiscGroup.createFinite("BlueRay", 45000000L, 45);
	}

	@Test
	public void testCreatingFiniteGroup() throws IOException {
		assertEquals("BlueRay", blueRayGroup.getName());
		assertEquals(Long.valueOf(45000000L), blueRayGroup.getSizeInBytes());
		assertEquals(Integer.valueOf(45), blueRayGroup.getCount());
	}

	@Test
	public void testEqualsMethodAndHashCode() {
		DiscGroup groupWithSameNameAndDifferentSize = DiscGroup.createFinite("BlueRay", 45000001L, 45);
		assertEquals(Boolean.FALSE, Boolean.valueOf(blueRayGroup.equals(groupWithSameNameAndDifferentSize)));

		DiscGroup groupWithSimilarNameAndSameSize = DiscGroup.createFinite("BluEray", 45000000L, 45);
		assertEquals(Boolean.FALSE, Boolean.valueOf(blueRayGroup.equals(groupWithSimilarNameAndSameSize)));

		DiscGroup sameGroupWithDifferentCount = DiscGroup.createFinite("BlueRay", 45000000L, 50);
		assertEquals(Boolean.TRUE, Boolean.valueOf(blueRayGroup.equals(sameGroupWithDifferentCount)));
		assertEquals(Boolean.TRUE, Boolean.valueOf(blueRayGroup.hashCode() == sameGroupWithDifferentCount.hashCode()));

		DiscGroup sameInfiniteGroup = DiscGroup.createInfinite("BlueRay", 45000000L);
		assertEquals(Boolean.TRUE, Boolean.valueOf(blueRayGroup.equals(sameInfiniteGroup)));
		assertEquals(Boolean.TRUE, Boolean.valueOf(blueRayGroup.hashCode() == sameInfiniteGroup.hashCode()));
	}
}
