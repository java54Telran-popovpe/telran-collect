package telran.util;
/**
 * 
 * cannot be equal objects (obj.equals(obj1) == true
 * cannot be nulls
 * @param <T>
 */
public interface Set<T> extends Collection<T> {
	T get( T pattertn );
}
