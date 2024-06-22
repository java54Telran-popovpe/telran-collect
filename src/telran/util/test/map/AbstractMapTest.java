package telran.util.test.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import telran.util.Collection;
import telran.util.Map;

abstract class  AbstractMapTest {
	Integer[] keys = { -20, 10, 1, 100, -5 };
	Integer[] values = { -5, 100, 1, 10, -5 };
	protected Map<Integer, Integer> map;
	//TODO test
	
	void setUp() {
		for( int i = 0; i < keys.length; i++ ) {
			map.put( keys[ i ], values[ i ]  );
		}
	}
	
	@Test
	void mapToCollectionsTest() {
		Integer[] expectedKeys = { -20, 10, 1, 100, -5};
		Integer[] expectedValues = { -5, 100, 1, 10, -5};
		runTest(expectedKeys, expectedValues );
	}
	
	@Test
	void getTest() {
		assertEquals(-5, map.get(-5));
		assertTrue(map.get(0) == null);
	}
	
	protected void runTest(Integer[] expectedKeys, Integer[] expectedValues) {
		Integer[] actual = map.keySet().stream().sorted().toArray(Integer[]::new);
		Arrays.sort(expectedKeys);
		assertArrayEquals(expectedKeys, actual);
		
		actual = map.values().stream().sorted().toArray(Integer[]::new);
		Arrays.sort(expectedValues);
		assertArrayEquals(expectedValues, actual);
		
	}

	@Test
	void removeTest() {
		//removing existing
		assertEquals( -5, map.remove(-20) );
		Integer[] expectedKeys = { 10, 1, 100, -5};
		Integer[] expectedValues = { 100, 1, 10, -5};
		runTest( expectedKeys, expectedValues );
		//removing unexisting
		assertTrue(map.remove(0) == null);
		runTest( expectedKeys, expectedValues );
	}
	
	@Test
	void putWithSameKey() {
		assertEquals ( 10, map.get(100) );
		assertEquals( 10, map.put(100, 0) );
		assertEquals( 0, map.get(100) );
		
	}
	
}
