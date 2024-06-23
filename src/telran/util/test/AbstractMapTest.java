package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import telran.util.Map;
import telran.util.Map.Entry;
import telran.util.Set;
import java.util.Random;
import java.util.function.Predicate;

abstract class  AbstractMapTest {
	
	protected static final int NUMBER_OF_ELEMENTS = 100;


	Integer[] keys = new Random()
			.ints()
			.distinct()
			.limit(NUMBER_OF_ELEMENTS)
			.boxed()
			.toArray(Integer[]::new);
	Integer[] values = new Random()
			.ints()
			.limit(NUMBER_OF_ELEMENTS)
			.boxed()
			.toArray(Integer[]::new);
			

	Integer uniqueKey= new Random().
					ints().filter(value -> { return Arrays.stream(keys).noneMatch(key -> { return key == value;}); } ).findFirst().orElseThrow();
	
	protected Map<Integer, Integer> map;
	//TODO test
	
	void setUp() {
		for( int i = 0; i < keys.length; i++ ) {
			map.put( keys[ i ], values[ i ]  );
		}
	}
	
	@Test
	protected void keysSetTest() {
		Integer[] expected = Arrays.copyOf(keys, NUMBER_OF_ELEMENTS);
		Arrays.sort(expected);
		Integer[] actual = map.keySet().stream().sorted().toArray(Integer[]::new);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	protected void valuesTest() {
		Integer[] expected = Arrays.copyOf(values, NUMBER_OF_ELEMENTS);
		Arrays.sort(expected);
		Integer[] actual = map.values().stream().sorted().toArray(Integer[]::new);
		assertArrayEquals(expected, actual);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	protected void entrySetTest() {
		Entry<Integer, Integer>[] expected = new Entry[NUMBER_OF_ELEMENTS];
		for( int i = 0; i < NUMBER_OF_ELEMENTS; i++ ) {
			expected[ i ] = new Entry<Integer, Integer>( keys[i], values[i]);
		}
		Arrays.sort(expected);
		Entry<Integer, Integer>[] actual = map.entrySet().stream().sorted().toArray(Entry[]::new);
		assertArrayEquals(expected, actual);
	}
	
	
	@Test
	void getTest() {
		int index = new Random().ints(0, NUMBER_OF_ELEMENTS).findFirst().orElseThrow();
		Integer value = values[index];
		assertEquals(value, map.get(keys[index]));
		assertTrue(map.get(uniqueKey) == null);
	}

	@Test
	void removeTest() {
		int index = new Random().ints(0, NUMBER_OF_ELEMENTS).findFirst().orElseThrow();
		Integer value = values[index];
		assertEquals(NUMBER_OF_ELEMENTS, map.entrySet().size());
		assertEquals(value, map.remove(keys[index]));
		assertEquals(NUMBER_OF_ELEMENTS - 1, map.entrySet().size());
		assertTrue(map.get(uniqueKey) == null);
	}
	
	@Test
	void putTest() {
		assertEquals(NUMBER_OF_ELEMENTS, map.entrySet().size());
		assertNull(map.put(uniqueKey, uniqueKey));
		assertEquals(NUMBER_OF_ELEMENTS+1, map.entrySet().size());
		assertEquals(uniqueKey, map.put(uniqueKey, uniqueKey+1));
		assertEquals(NUMBER_OF_ELEMENTS+1, map.entrySet().size());
		assertEquals(uniqueKey+1, map.get(uniqueKey));
	}
	
	@Test
	void getOrDefaultTest() {
		int index = new Random().ints(0, NUMBER_OF_ELEMENTS).findFirst().orElseThrow();
		Integer value = values[index];
		assertEquals(value, map.getOrDefault(keys[index], value - 1));
		assertTrue(map.getOrDefault(uniqueKey, value - 1) == value - 1);
	}
	
	@Test 
	void putIfAbsentTest() {
		int index = new Random().ints(0, NUMBER_OF_ELEMENTS).findFirst().orElseThrow();
		Integer value = values[index];
		assertEquals(value, map.putIfAbsent(keys[index], value - 1));
		assertEquals(value, map.get(keys[index]));
		assertEquals(NUMBER_OF_ELEMENTS, map.entrySet().size());
		assertNull(map.putIfAbsent(uniqueKey, value - 1));
		assertEquals(NUMBER_OF_ELEMENTS + 1, map.entrySet().size());
		assertEquals(value -1 , map.get(uniqueKey));
	}
	
	
	
	
}
