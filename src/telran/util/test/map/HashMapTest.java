package telran.util.test.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.HashMap;
import telran.util.test.map.AbstractMapTest;

class HashMapTest extends AbstractMapTest {
	
	@BeforeEach
	@Override
	void setUp() {
		map = new HashMap<>(); 
		super.setUp();
	}
}
