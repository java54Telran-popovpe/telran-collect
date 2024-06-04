package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import telran.util.HashSet;
import telran.util.Set;

class ListLength {
	
	HashSet<Integer> set;
	Integer[] arr= new Random().ints( -100_000, 100_000).boxed().distinct().limit(100_000).toArray(Integer[]::new);
	Map<Integer, Long> prevRes;
	
	public void initHashSet( float factor) {
		
		set = new HashSet<Integer>(16, factor);
		for ( Integer integer: arr )
			set.add(integer);
	}
	@Test
	void test() {
		for ( int i = 60; i < 95; i = i + 3) {
			initHashSet( i / 100f);
			Map<Integer, Long> statsToPrint = set.collectedStats.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
			System.out.println("Factor " + i);
			statsToPrint.forEach((length, freq) -> System.out.println(String.format("List %d, freq %d", length, deltaFreq( freq, length, prevRes))));
			prevRes = statsToPrint;
		}
	}
	private long deltaFreq(Long freq, Integer length, Map<Integer, Long> prevRes) {
		if ( prevRes != null ) {
			return freq - prevRes.getOrDefault(length, 0l);
		}
		return freq;
	}
}
