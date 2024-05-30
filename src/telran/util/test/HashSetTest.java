package telran.util.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.HashSet;

public class HashSetTest extends SetTest {
	
@Override
	@BeforeEach
	void setUp( ) {
		collection = new HashSet<>(4, 0.75f);
		super.setUp();
	}

@Override
protected void runTest(Integer[] expected) {
	Integer[] actual = collection.stream().sorted().toArray( Integer[]::new);
	Arrays.sort(expected);
	assertArrayEquals( expected, actual );
}



	
}
