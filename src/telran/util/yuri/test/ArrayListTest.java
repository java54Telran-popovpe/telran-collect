package telran.util.yuri.test;

import org.junit.jupiter.api.BeforeEach;

import telran.util.ArrayList;

public class ArrayListTest extends ListTest {
  @BeforeEach
  @Override
  void setUp() {
	  collection = new ArrayList<Integer>(3);
	  super.setUp();
  }
}
