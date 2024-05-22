package telran.util.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import telran.util.Collection;

public abstract class CollectionTest {
	protected Collection<Integer> collection;
	Integer[] numbers = { -20, 10, 1, 100, -5};
	
	@BeforeEach
	void setUp() {
		for(Integer num: numbers ) {
			collection.add(num);
		}
	}
	@Test
	@DisplayName(value = "Collection::size()")
	void sizeMethodTest() {
		assertEquals(5, collection.size());
	}
	
	@Test
	@DisplayName(value="Collection::contains(object)")
	void containsMethodTest() {
		assertTrue( collection.contains(10) );
		assertTrue(!collection.contains(-3));
	}
	
	@Test
	@DisplayName(value="Collection::remove(object)")
	void removeMethodTest() {
		runRemoveTest(new Integer[]{-20, 10, 100, -5}, 1);
		runRemoveTest(new Integer[]{-20, 10, 100, -5}, -4);
		runRemoveTest(new Integer[]{-20, 10, 100,}, -5);
		runRemoveTest(new Integer[]{10, 100,}, -20);
		runRemoveTest(new Integer[]{ 100,}, 10);
		runRemoveTest(new Integer[]{}, 100);
		runRemoveTest(new Integer[]{}, 100);
		assertEquals(0, collection.size());
		assertTrue(!collection.contains(100));
	}
	@Test
	@DisplayName(value="Collection::add(object)")
	void addObjectTest() {
		for ( int count = 0; count < 3; count++ )
			for( Integer i: new Integer[]{  -20, 10, 1, 100, -5, }) 
				collection.add(i);
		assertArrayEquals(new Integer[]{  	-20, 10, 1, 100, -5, 
											-20, 10, 1, 100, -5, 
											-20, 10, 1, 100, -5, 
											-20, 10, 1, 100, -5,},
				getArray());
	}
	@Test
	void iteratorTest( ) {
		runTest(numbers);
	}
	protected Integer[] getArray() {
		return collection.stream().toArray(Integer[]::new);
	}
	
	protected void runTest(Integer[] expected) {
		Integer[] actual = getArray();
		assertArrayEquals(expected, actual);
	}
	
	protected void runRemoveTest(Integer[] expected, Integer removed) {
		if ( collection.stream().filter( e -> e.equals(removed)).findFirst().isPresent() ) {
			assertTrue(collection.remove(removed));
			assertArrayEquals(expected, getArray());
		}
		else {
			Integer[] arrayBefore = getArray();
			assertTrue(!collection.remove(removed));
			assertArrayEquals(arrayBefore, getArray());
		}
	}
}
