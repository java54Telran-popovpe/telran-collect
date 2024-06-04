package telran.util.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import telran.util.Set;
import telran.util.SortedSet;

class SortedSetTest extends SetTest {

	SortedSet<Integer> set;
	
	@Override
	void setUp() {
		super.setUp();
		set = (SortedSet<Integer>)collection;
	}
	
	@Override
	protected void runTest(Integer[] expected) {
		Integer[] actual = collection.stream().toArray( Integer[]::new);
		Arrays.sort(expected);
		assertArrayEquals( expected, actual );
	}
	
	@Test
	void firstTest() {
		assertEquals(Integer.valueOf(-20), set.first());
	}
	
	@Test
	void lastTest() {
		assertEquals(Integer.valueOf(100), set.last());
	}

}
