package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {
	
	private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
	private static final float DEFAULT_FACTOR = 0.75f;
	List<T>[] hashTable;
	int size;
	float factor;
	
	private class HashSetIterator implements Iterator<T> {
		
		//HashTableIterator iterates over no-zero-sized elements(lists) of hashTable
		private class HashTableIterator implements Iterator<List<T>> {
			int currentIndex = -1;
			
			private HashTableIterator() {
				setCurrentIndexOnNextList();
				
			}
			
			@Override
			public boolean hasNext() {
				return currentIndex < hashTable.length;
			}

			@Override
			public List<T> next() {
				if (!hasNext())
					throw new NoSuchElementException();
				
				List<T> result = hashTable[currentIndex];
				setCurrentIndexOnNextList();
				return result;
				
			}
			
			private void setCurrentIndexOnNextList() {
				do {
					currentIndex++;
				}
				while (currentIndex < hashTable.length && ( hashTable[currentIndex] == null || 
															hashTable[currentIndex] != null && hashTable[currentIndex].size() == 0 ) );
				
			}
		}

		Iterator<List<T>> hashTableIterator = new HashTableIterator();
		Iterator<T> currentListIterator;
		
		private HashSetIterator() {
			if ( hashTableIterator.hasNext() )
				currentListIterator = hashTableIterator.next().iterator();
		}
		
	
		@Override
		public boolean hasNext() {
			return hashTableIterator.hasNext() || ( currentListIterator != null && currentListIterator.hasNext());
		}

		@Override
		public  T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T result = currentListIterator.next();
			if(!currentListIterator.hasNext() && hashTableIterator.hasNext()) {
				currentListIterator = hashTableIterator.next().iterator();
					
			}
			return result;
		}
		
		
	}

	
	public HashSet( int hashTableLength, float factor ) {
		hashTable = new List[hashTableLength];
		this.factor = factor;
	}
	
	public HashSet( ) {
		this(DEFAULT_HASH_TABLE_LENGTH, DEFAULT_FACTOR);
	}
	
	@Override
	public boolean add(T obj) {
		boolean result = false;
		if ( !contains( obj )) {
			if ( ( float )size / hashTable.length >= factor )
				hashTableReallocation();
			addObjInHashTable( obj, hashTable );
			size++;
			result = true;
		}
		return result;
	}

	private void hashTableReallocation() {
		List<T>[] newHashTable = new List[ hashTable.length * 2];
		List<T>[] oldHashTable = hashTable;
		hashTable = newHashTable;
		for( List<T> list: oldHashTable ) {
			if ( list != null ) {
				for ( T obj : list) {
					addObjInHashTable(obj, newHashTable);
				}
			}
		}
	}

	private void addObjInHashTable(T obj, List<T>[] table) {
		int index = getIndex(obj);
		List<T> list = table[index];
		if ( list == null ) {
			list = new LinkedList<>();
			table[index] = list;
		}
		list.add(obj);
	}

	private int getIndex(T obj) {
		int hashCode = obj.hashCode();
		int index = Math.abs(hashCode % hashTable.length);
		return index;
	}

	@Override
	public boolean remove(T pattern) {
		boolean result = false;
		int index = getIndex(pattern);
		List<T> list = hashTable[index];
		if ( list != null && list.remove(pattern) ) {
			size--;
			result = true;
		}
		return result;
	}

	@Override
	public boolean contains(T pattern) {
		int index = getIndex(pattern);
		List<T> list = hashTable[index];
		return list!= null && list.contains(pattern);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new HashSetIterator();
	}

	@Override
	public T get(T pattern) {
		T result = null;
		List<T> list = hashTable[getIndex(pattern)];
		if ( list != null ) {
			int index = list.indexOf(pattern);
			if ( index > -1 )
				result = list.get(index);
		}
		return result;
	}

}
