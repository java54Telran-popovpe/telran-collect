package telran.util.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import telran.util.List;

public abstract class ListTest extends CollectionTest {
	protected List<Integer> list; 
	
	@BeforeEach
	@Override
	void setUp() {
		super.setUp();
		list = (List<Integer>)collection;
	}
	
	protected void runGetTest(int i, Integer expected) {
		if( i < 0 || i >= list.size() )
			assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.get(i));
		else
			assertEquals(expected, list.get(i));
	}
	
	protected void runAddTest(int i, int toAdd, Integer[] expected) {
		if( i < 0 || i > list.size() ) {
			Integer[] before = getArray();
			assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.add(i, toAdd));
			assertArrayEquals(before, getArray());
		}
		else {
			list.add(i, toAdd);
			assertArrayEquals(expected, getArray() );
		}
	}
	
	protected void runRemoveTest(int i, Integer[] expected) {
		if( i < 0 || i >= list.size() ) {
			Integer[] before = getArray();
			assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.remove(i));
			assertArrayEquals(before, getArray());
		}
		else {
			list.remove(i);
			assertArrayEquals(expected, getArray() );
		}
	}
	
	
	@Override
	protected Integer[] getArray() {
		return list.stream().toArray(Integer[]::new);
	}
}
