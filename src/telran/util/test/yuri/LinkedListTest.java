package telran.util.test.yuri;

import org.junit.jupiter.api.BeforeEach;

import telran.util.ArrayList;
import telran.util.LinkedList;

public class LinkedListTest extends ListTest {
	@BeforeEach
	  @Override
	  void setUp() {
		  collection = new LinkedList<Integer>();
		  super.setUp();
	  }
}
