package telran.util;


public interface List<T> extends Collection<T> {
	/**
	 * @param index
	 * @return reference to an object at a given index
	 * throws IndexOutOfBoundsException for either index < 0 or index >=size()
	 */
	T get(int index);
	
	
	/**
	 * 
	 * @param index
	 * @param obj
	 * adds object at a given index
	 * throws exception if index < 0 or index > size
	 */
	void add( int index, T obj );
	
	
	/**
	 * 
	 * @param index
	 * @return reference to a removed object 
	 * throws Exception as get method
	 */
	T remove( int index);
	
	/**
	 * 
	 * @param pattern
	 * @return index of first object equaled to a given pattern
	 * otherwize -1 
	 */
	int indexOf(T pattern);
	
	
	/**
	 * 
	 * @param pattern
	 * @return index of last object equaled to a given pattern
	 * otherwize -1 
	 */
	int lastIndexOf(T pattern);
	
	@Override
	default boolean remove (T pattern) {
		boolean result = false;
		int indexOfPattern = indexOf(pattern);
		if ( indexOfPattern > -1 ) {
			remove(indexOfPattern);
			result = true;
		}
		return result;

	}
	default boolean contains(T pattern) {
		return indexOf(pattern) > -1;
	}

	/**
	 * 
	 * @param index - index for check
	 * @param size - size of collection
	 * @param exlusive - if index value of size is not valid?
	 */
	static void checkIndex(int index, int upperBoundIncluded ) {
		if ( index < 0 || index >  upperBoundIncluded )
			throw new IndexOutOfBoundsException();
	}
}
