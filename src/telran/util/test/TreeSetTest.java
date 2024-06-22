package telran.util.test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.TreeSet;


public class TreeSetTest extends SortedSetTest {
	TreeSet<Integer> treeSet;
	@Override
	@BeforeEach
	void setUp() {
		collection = new TreeSet<>(); 
		super.setUp();
		treeSet = (TreeSet<Integer>)collection;
	}
	
	@Test
	void displayRootChildrenTest() {
		treeSet.displayRootChildren();
	}
	@Test
	void treeInversionTest() {
		treeSet.treeInversion();
		Integer[] expected = {100, 10, 1, -5 , -20 };
		Integer[] actual = new Integer[treeSet.size()];
		int index = 0;
		for (Integer num: treeSet) {
			actual[index++] = num;
		}
		assertArrayEquals(expected, actual);
		
		
	}
	@Test
	void displayTreeRotetedTest( ) {
		treeSet.displayTreeRotated();
	}
	
	@Test
	void widthTest() {
		assertEquals(2, treeSet.width());
	}
	
	@Test
	void heightTest() {
		assertEquals(4, treeSet.height());
	}
	
	@Test
	void sortedSequenceTreeTest() {
		TreeSet<Integer> treeSet = new TreeSet<>();
		int[] sortedArray = new Random().ints().distinct().limit(N_ELEMENTS).sorted().toArray();
		transformArray(sortedArray);
		for(int num: sortedArray) {
			treeSet.add(num);
		}
		balancedTreeTest(treeSet);
		
	}

	private void balancedTreeTest(TreeSet<Integer> treeSet) {
		assertEquals(20, treeSet.height());
		assertEquals( ( (N_ELEMENTS + 1 ) / 2 ), treeSet.width());
	}

	private void transformArray(int[] sortedArray) {
		int[] result = new int[ sortedArray.length ];
		addLevelElements( sortedArray, result, 0, sortedArray.length - 1, 1 );
		System.arraycopy(result, 0, sortedArray, 0, sortedArray.length);
	}
	
	private void addLevelElements(int[] source, int[] result, int left, int right, int index) {
		if ( left <= right ) {
			int middle =  ( right + left ) /2;
			result[ index - 1 ] = source [ middle ];
			addLevelElements( source, result, left, middle - 1, index << 1 );
			addLevelElements(source, result, middle + 1, right, (index << 1) + 1);
		}
	}



	@Test
	void balancedTreeTest() {
		createBigRandomCollection( new Random());
		treeSet.balance();
		balancedTreeTest(treeSet);
		int index = 0;
		for ( Integer num: treeSet ) {
			index++;
		}
		assertEquals(treeSet.size(), index);
	}
}
