package telran.util.test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.TreeMap;


class TreeMapTest extends AbstractMapTest {
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
	protected void runTest(Integer[] expectedKeys, Integer[] expectedValues) {
		
		Integer[] actual = map.keySet().stream().toArray(Integer[]::new);
		Arrays.sort(expectedKeys);
		assertArrayEquals(expectedKeys, actual);
		
		actual = map.values().stream().sorted().toArray(Integer[]::new);
		Arrays.sort(expectedValues);
		assertArrayEquals(expectedValues, actual);
		
	}
	
	@Test
	void firstTest() {
		assertEquals( -20, treeMap.firstKey() );
		assertTrue( emptyMap.firstKey() == null);
	}
	
	@Test
	void lastTest() {
		assertEquals( 100, treeMap.lastKey() );
		assertTrue( emptyMap.firstKey() == null);
	}
	
	@Test
	void floorTest() {
		assertTrue(treeMap.floorKey(-100) == null);
		assertEquals(100, treeMap.floorKey(101));
		assertEquals(-20, treeMap.floorKey(-20));
		assertEquals(-20, treeMap.floorKey(-19));
	}
	
	@Test
	void ceilingTest() {
		assertTrue(treeMap.ceilingKey(101) == null);
		assertEquals(100, treeMap.ceilingKey(100));
		assertEquals(-20, treeMap.ceilingKey(-21));
		assertEquals(100, treeMap.ceilingKey(99));
	}
}
