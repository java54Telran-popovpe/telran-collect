package telran.util.test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Map.Entry;
import telran.util.TreeMap;


class TreeMapTest extends AbstractMapTest {
	Integer minimum = Arrays.stream(keys).min(Comparator.naturalOrder()).orElseThrow();
	Integer maximum = Arrays.stream(keys).max(Comparator.naturalOrder()).orElseThrow();
	Integer uniqueInsideRange = new Random().
					ints(minimum, maximum + 1).
					filter( i -> Arrays.stream(keys).allMatch( key -> key != i )).findFirst().orElseThrow();
	TreeMap<Integer,Integer> treeMap;
	TreeMap<Integer,Integer> emptyMap = new TreeMap<>();
	@BeforeEach
	@Override
	void setUp() {
		map = new TreeMap<>();
		treeMap = (TreeMap<Integer,Integer>) map;
		super.setUp();
	}
	
	@Override
	@Test
	protected void keysSetTest() {
		Integer[] expected = Arrays.copyOf(keys, NUMBER_OF_ELEMENTS);
		Arrays.sort(expected);
		Integer[] actual = map.keySet().stream().toArray(Integer[]::new);
		assertArrayEquals(expected, actual);
	}
	
	@Override
	@Test
	protected void valuesTest() {
		Integer[] expected = Arrays.copyOf(values, NUMBER_OF_ELEMENTS);
		Arrays.sort(expected);
		Integer[] actual = map.values().stream().sorted().toArray(Integer[]::new);
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
		Arrays.sort(expected);
		Entry<Integer, Integer>[] actual = map.entrySet().stream().toArray(Entry[]::new);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void firstTest() {
		assertEquals(minimum, treeMap.firstKey() );
		assertNull( emptyMap.firstKey());
	}
	
	@Test
	void lastTest() {
		assertEquals( maximum, treeMap.lastKey() );
		assertNull( emptyMap.firstKey());
	}
	
	@Test
	void floorTest() {
		assertNull(treeMap.floorKey(minimum - 1 ));
		assertTrue(treeMap.floorKey(minimum ) == minimum);
		assertTrue(treeMap.floorKey(maximum + 1 ) == maximum);
		Integer floorKey = treeMap.floorKey(uniqueInsideRange);
		assertTrue(Arrays.stream(keys).allMatch( el -> !(el > floorKey && el < uniqueInsideRange)));
		assertTrue( floorKey < uniqueInsideRange );
		
	}
	
	@Test
	void ceilingTest() {
		assertNull(treeMap.ceilingKey(maximum + 1 ));
		assertTrue(treeMap.ceilingKey(maximum ) == maximum);
		assertTrue(treeMap.ceilingKey(minimum - 1 ) == minimum);
		Integer ceilingKey = treeMap.ceilingKey(uniqueInsideRange);
		assertTrue(Arrays.stream(keys).allMatch( el -> !(el < ceilingKey && el > uniqueInsideRange)));
		assertTrue( ceilingKey > uniqueInsideRange );
	}
}
