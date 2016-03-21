package de.illilli.opendata.service.gruenflaechen.koeln;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FlaechentypEnumTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCqlPredicate() {
		String expected = "Objekttyp = 2";
		String actual = FlaechentypEnum.getById(2).cqlPredicate();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testId() {
		int expected = 2;
		int actual = FlaechentypEnum.getById(2).id();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testTyp() {
		String expected = "Kleingärten";
		String actual = FlaechentypEnum.getById(2).typ();
		Assert.assertEquals(expected, actual);
	}

}
