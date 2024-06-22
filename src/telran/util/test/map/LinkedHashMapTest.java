package telran.util.test.map;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;


import org.junit.jupiter.api.BeforeEach;

import telran.util.LinkedHashMap;


class LinkedHashMapTest extends AbstractMapTest {
	
	@BeforeEach
	@Override
	void setUp() {
		map = new LinkedHashMap<>();
		super.setUp();
	}
	
	protected void runTest(Integer[] expectedKeys, Integer[] expectedValues) {
		Integer[] actual = map.keySet().stream().toArray(Integer[]::new);
		assertArrayEquals(expectedKeys, actual);
		
		actual = map.values().stream().toArray(Integer[]::new);
		assertArrayEquals(expectedValues, actual);
	}
}
