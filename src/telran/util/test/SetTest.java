package telran.util.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import telran.util.Set;

public abstract class SetTest extends CollectionTest {
	
	Set<Integer> set;
	
	@Override
	void setUp() {
		super.setUp();
		set = (Set<Integer>)collection;
	}
	
	@Test
	void getTest() {
		assertEquals( Integer.valueOf(1), set.get(Integer.valueOf(1)) );
		assertNull(set.get(Integer.valueOf(100000)));
	}
	
	@Override
	@Test
	void addObjectTest() {
		for ( int count = 0; count < 3; count++ )
			for( Integer i: new Integer[]{  -20, 10, 1, 100, -5, }) 
				assertFalse(collection.add(i));
		compareArrayWithSetContent( new Integer[]{  	-20, 10, 1, 100, -5, } );
		for( Integer i: new Integer[]{  -19, 11, 2, 101, -4 , }) 
			assertTrue(collection.add(i));
		compareArrayWithSetContent( new Integer[]{  	-20, 10, 1, 100, -5, 
				-19, 11, 2, 101, -4 , });
	}
	@Override
	protected Integer[] getArray() {
		return collection.stream().sorted().toArray(Integer[]::new);
	}
	protected void compareArrayWithSetContent(Integer[] expected ) {
		Arrays.sort( expected );
		assertArrayEquals(expected, getArray());
		
	}
	
	@Override
	protected void runRemoveTest(Integer[] expected, Integer removed) {
		if ( collection.stream().filter( e -> e.equals(removed)).findFirst().isPresent() ) {
			assertTrue(collection.remove(removed));
			compareArrayWithSetContent( expected );
		}
		else {
			Integer[] arrayBefore = getArray();
			Arrays.sort(arrayBefore);
			assertTrue(!collection.remove(removed));
			compareArrayWithSetContent( arrayBefore );
		}
	}
}
	
	


