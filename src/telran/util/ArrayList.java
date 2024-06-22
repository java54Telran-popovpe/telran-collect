	package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public class ArrayList<T> extends AbstractCollection<T>  implements List<T> {
	
	private static int DEFAULT_CAPACITY = 16;
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
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private int index = 0;
			boolean flNext = false;
			
			@Override
			public boolean hasNext() {
				return index < size;
			}
			
			@Override
			public void remove() {
				if (!flNext)
					throw new IllegalStateException();
				ArrayList.this.remove(--index);
				flNext = false;
			}

			@Override
			public T next() {
				if ( !hasNext() )
					throw new NoSuchElementException();
				flNext = true;
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
	
	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int shiftValue = 0;
		for ( int i = 0; i < size; i++) {
			if ( predicate.negate().test(array[ i ]))
				moveWithShift( i,  shiftValue );
			else {
				shiftValue++;
			}
		}
		size -= shiftValue;
		return shiftValue > 0 ;
	}

	private void moveWithShift(int from, int shiftValue) {
		if ( shiftValue > 0 ) {
			array[from - shiftValue] = array[from];
			array[ from ] = null;
		}
	}

}
