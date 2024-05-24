package telran.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Collection<T> extends Iterable<T> {
	default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
	default Stream<T> parellelStream() {
		return StreamSupport.stream(spliterator(), true);
	}
	boolean add(T obj);
	boolean remove(T pattern);
	boolean contains(T pattern);
	int size();
	static <T> boolean compareNullable(T element, T pattern) {
		return  ( element == null && pattern == null ) || ( element != null && element.equals(pattern) ) ? true : false;
	}
}
