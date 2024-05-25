package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<T> implements List<T> {
	
	private static int DEFAULT_CAPACITY = 16;
	private int size;
	private T[] array;

	@SuppressWarnings("unchecked")
	public ArrayList( int capacity) {
		array = (T[]) new Object[capacity];
	}
	
	public ArrayList( ) {
		this( DEFAULT_CAPACITY );
	}
	
	@Override
	public boolean add(T obj) {
		if ( size == array.length ) {
			allocate();
		}
		array[size++] = obj;
		return true;
	}

	private void allocate() {
		array = Arrays.copyOf(array, array.length * 2);
		
	}

	@Override
	public boolean remove(T pattern) {
		int index = indexOf( pattern );
		boolean result = false;
		if (index > -1 ) {
			result = true;
			remove(index);
		}
		return result;
	}

	@Override
	public boolean contains(T pattern) {
		return indexOf(pattern) > -1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public T next() {
				if ( !hasNext() )
					throw new NoSuchElementException();
				return array[index++];
			}
			
		};
	}

	@Override
	public T get(int index) {
		if ( index < 0 || index >= size )
			throw new IndexOutOfBoundsException();
		return array[ index ];
	}

	@Override
	public void add(int index, T obj) {
		List.checkIndex(index, size );
		if( size == array.length)
			allocate();
		System.arraycopy(array, index, array, index + 1, size - index);
		array[ index ] = obj;
		size++;
	}

	@Override
	public T remove(int index) {
		List.checkIndex(index, size -1 );
		T removedElement = array[ index ];
		System.arraycopy(array, index + 1, array, index, size - index - 1); 
		size--;
		return removedElement;
	}

	@Override
	public int indexOf(T pattern) {
		int index = 0;
	while ( index < size && !Objects.equals( array[ index ], pattern) ) {
			index++;
		}
		return index == size ? -1 : index;
	}

	

	@Override
	public int lastIndexOf(T pattern) {
		int index = size - 1;
		while ( index > -1 && !Objects.equals( array[ index ], pattern) ) {
			index--;
		}
		return index;
	}
	

}
