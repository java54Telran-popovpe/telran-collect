package telran.util.test.map;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

import telran.util.LinkedHashMap;
import telran.util.Map.Entry;


class LinkedHashMapTest extends AbstractMapTest {
	
	@BeforeEach
	@Override
	void setUp() {
		map = new LinkedHashMap<>();
		super.setUp();
	}
	
	@SuppressWarnings("unchecked")
	protected void runTest(Integer[] expectedKeys, Integer[] expectedValues, Entry<Integer,Integer>[] expectedEntry) {
		Integer[] actual = map.keySet().stream().toArray(Integer[]::new);
		Arrays.sort(expectedKeys);
		
		actual = map.values().stream().toArray(Integer[]::new);
		assertArrayEquals(expectedValues, actual);
		
		Entry<Integer, Integer>[] actualEntries = map.entrySet().stream().toArray(Entry[]::new);
		assertArrayEquals(expectedEntry, actualEntries);
	}
}
