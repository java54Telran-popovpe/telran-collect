package telran.util.test.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import telran.util.Map;
import telran.util.Map.Entry;
import telran.util.Set;

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
		@SuppressWarnings("unchecked")
		Entry<Integer, Integer>[] expectedEntries = new Entry[ expectedKeys.length];
		for( int i = 0; i < expectedKeys.length; i++ ) {
			expectedEntries[ i ] = new Entry<Integer, Integer>( expectedKeys[i], expectedValues[i]);
		}
		runTest(expectedKeys, expectedValues, expectedEntries );
	}
	
	@Test
	void getTest() {
		assertEquals(-5, map.get(-5));
		assertTrue(map.get(0) == null);
	}
	
	@SuppressWarnings("unchecked")
	protected void runTest(Integer[] expectedKeys, Integer[] expectedValues, Entry<Integer,Integer>[] expectedEntry) {
		Integer[] actual = map.keySet().stream().sorted().toArray(Integer[]::new);
		Arrays.sort(expectedKeys);
		assertArrayEquals(expectedKeys, actual);
		
		actual = map.values().stream().sorted().toArray(Integer[]::new);
		Arrays.sort(expectedValues);
		assertArrayEquals(expectedValues, actual);
		
		Entry<Integer, Integer>[] actualEntries = map.entrySet().stream().sorted().toArray(Entry[]::new);
		Arrays.sort(expectedEntry);
		assertArrayEquals(expectedEntry, actualEntries);
	}

	@Test
	void removeTest() {
		assertEquals( -5, map.remove(-20) );
		assertEquals(4l, map.entrySet().stream().count());
		assertNull(map.remove(-20) );
		assertEquals(4l, map.entrySet().stream().count());
	}
	
	@Test
	void putWithSameKey() {
		assertEquals ( 10, map.get(100) );
		assertEquals( 10, map.put(100, 0) );
		assertEquals( 0, map.get(100) );
		
	}
	
	
	
}
