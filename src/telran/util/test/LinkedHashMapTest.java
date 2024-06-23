package telran.util.test;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.LinkedHashMap;
import telran.util.Map.Entry;


class LinkedHashMapTest extends AbstractMapTest {
	
	@BeforeEach
	@Override
	void setUp() {
		map = new LinkedHashMap<>();
		super.setUp();
	}
	
	@Override
	@Test
	protected void keysSetTest() {
		Integer[] expected = Arrays.copyOf(keys, NUMBER_OF_ELEMENTS);
		Integer[] actual = map.keySet().stream().toArray(Integer[]::new);
		assertArrayEquals(expected, actual);
	}
	
	@Override
	@Test
	protected void valuesTest() {
		Integer[] expected = Arrays.copyOf(values, NUMBER_OF_ELEMENTS);
		Integer[] actual = map.values().stream().toArray(Integer[]::new);
		assertArrayEquals(expected, actual);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Test
	protected void entrySetTest() {
		Entry<Integer, Integer>[] expected = new Entry[NUMBER_OF_ELEMENTS];
		for( int i = 0; i < NUMBER_OF_ELEMENTS; i++ ) {
			expected[ i ] = new Entry<Integer, Integer>( keys[i], values[i]);
		}
		Entry<Integer, Integer>[] actual = map.entrySet().stream().toArray(Entry[]::new);
		assertArrayEquals(expected, actual);
	}
	
}
