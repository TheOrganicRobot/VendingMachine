package com.techelevator.view;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.classes.Snack;
import com.techelevator.classes.Utility;

import junit.framework.Assert;

public class UtilityTest {
	
	Utility u;
	
	@Before
	public void setup() {
		u = new Utility();
	}
	
	@Test
	public void test() {
		Map<String, Snack> map = u.readInventoryListAndPopulate();
		Assert.assertFalse(map.isEmpty());
		Assert.assertEquals(true, map.containsKey("B3"));
		Assert.assertEquals("Cloud Popcorn", map.get("A4").getProductName());
	}

}
