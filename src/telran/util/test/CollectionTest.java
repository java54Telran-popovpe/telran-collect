package telran.util.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

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
