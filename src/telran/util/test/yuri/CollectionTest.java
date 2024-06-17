package telran.util.test.yuri;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import telran.util.Collection;
import telran.util.List;

public abstract class CollectionTest {
	protected static final int N_ELEMENTS = 1_048_575;
	private static final int N_RUNS = 1000000;
	protected Collection<Integer> collection;
	Integer[] numbers = {-20, 10, 1, 100, -5};
	int newNumber = 1000000;
	@BeforeEach
	void setUp() {
		for(Integer num: numbers) {
			collection.add(num);
		}
	}
	@Test
	void iteratorTest() {
		
		runTest(numbers);
		Iterator<Integer> it = collection.iterator();
		assertThrowsExactly(IllegalStateException.class,
				() -> it.remove());
		Integer num = null;
		while(it.hasNext()) {
			num = it.next();
		}
		assertThrowsExactly(NoSuchElementException.class,
				() -> it.next());
		it.remove();
		assertFalse(collection.contains(num));
		assertEquals(numbers.length - 1, collection.size());
		//two remove calls with no iterating must throw IllegalStateException
		assertThrowsExactly(IllegalStateException.class, 
				() -> it.remove());
		
		
	}
	@Test
	void addEqualedTest() {
		
		Integer[] expected = {-20, 10, 1, 100, -5, numbers[0]};
		assertTrue(collection.add(numbers[0]));
		runTest(expected);
	}
	@Test
	void addUniqueTest() {
		
		Integer[] expected = {-20, 10, 1, 100, -5, newNumber};
		assertTrue(collection.add(newNumber));
		runTest(expected);
	}
	@Test
	void removeTest() {
		assertTrue(collection.remove(numbers[0]));
		runTest(Arrays.copyOfRange(numbers, 1, numbers.length));
		assertFalse(collection.remove(newNumber));
	}
	protected void runTest(Integer[] expected) {
		Integer [] actual = collection.stream().toArray(Integer[]::new);
		assertArrayEquals(expected, actual);
	}
	@Test
	void containsTest() {
		assertTrue(collection.contains(numbers[0]));
		assertFalse(collection.contains(newNumber));
	}
	@Test
	void sizeTest() {
		assertEquals(numbers.length, collection.size());
		collection.add(newNumber);
		assertEquals(numbers.length + 1, collection.size());
		collection.remove(numbers[0]); //after successful removing size reduced
		assertEquals(numbers.length, collection.size());
		collection.remove(numbers[0]); //after unsuccessful removing size doesn't change
		assertEquals(numbers.length, collection.size());
	}
	@Test
	
	void performanceAddContainsIteratorTest() {
		Random random = new Random();
		createBigRandomCollection(random);
		assertEquals(N_ELEMENTS, collection.size());
		Integer [] actual = new Integer[N_ELEMENTS];
		int index = 0;
		for(Integer num: collection) {
			actual[index++] = num;
		}
		assertEquals(N_ELEMENTS, index);
		
		if(collection instanceof List) {
			System.out.println("Performance test of method \"contains\" for all List objects takes huge time");
		} else {
			for(int i = 0; i < N_RUNS; i++) {
			collection.contains(random.nextInt());
		}
		}
		
	}
	protected void createBigRandomCollection(Random random) {
		int[] randomNumbers = random.ints().distinct().limit(N_ELEMENTS).toArray();
		for(Integer num: numbers) {
			collection.remove(num);
		}
		for(int i = 0; i < N_ELEMENTS; i++) {
			collection.add(randomNumbers[i]);
		}
	}
	@Test
	void removeIfTest() {
		assertTrue(collection.removeIf(n -> n % 2 == 0));
		assertTrue(collection.stream().allMatch(n -> n % 2 != 0));
		assertFalse(collection.removeIf(n -> n % 2 == 0));
	}
	@Test
	void clearTest() {
		collection.clear();
		assertEquals(0, collection.size());
	}
	@Test
	@Timeout(value = 5, unit = TimeUnit.SECONDS,
	threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void performanceClearTest() {
		createBigRandomCollection(new Random());
		collection.clear();
	}
	
}
