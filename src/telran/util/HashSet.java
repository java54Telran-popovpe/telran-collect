package telran.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.platform.engine.support.hierarchical.Node;

@SuppressWarnings("unchecked")
public class HashSet<T> extends AbstractCollection<T> implements Set<T> {
	
	private static final int DEFAULT_HASH_TABLE_LENGTH = 16;
	private static final float DEFAULT_FACTOR = 0.75f;
	List<T>[] hashTable;
	float factor;
	
	public java.util.List<Integer> collectedStats = new ArrayList<Integer>();
	
	private class HashTableIterator implements Iterator<Iterator<T>> {
		private int currentIndex;
		
		private HashTableIterator() {
			correctIndex();
		}
		
		@Override
		public boolean hasNext() {
			return currentIndex < hashTable.length;
		}

		@Override
		public Iterator<T> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			Iterator<T> result = hashTable[currentIndex].iterator();
			currentIndex++;
			correctIndex();
			return result;
		}
		
		private void correctIndex() {
			while (currentIndex < hashTable.length && 
					( hashTable[currentIndex] == null || 
					  hashTable[currentIndex] != null && hashTable[currentIndex].size() == 0 ) ) {
				currentIndex++;
			};
			
		}
	}

	
	private class HashSetIterator implements Iterator<T> {
		
		Iterator<Iterator<T>> hashTableIterator = new HashTableIterator();
		Iterator<T> currentListIterator;
		Iterator<T> listIteratorToRemove;
		private boolean flNext = false;
		
		
		private HashSetIterator() {
			if ( hashTableIterator.hasNext() )
				currentListIterator = hashTableIterator.next();
		}
		
	
		@Override
		public boolean hasNext() {
			return hashTableIterator.hasNext() || ( currentListIterator != null && currentListIterator.hasNext());
		}

		@Override
		public  T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			flNext = true;
			listIteratorToRemove = currentListIterator;
			T result = currentListIterator.next();
			if(!currentListIterator.hasNext() && hashTableIterator.hasNext()) {
				currentListIterator = hashTableIterator.next();
					
			}
			return result;
		}
		@Override
		public void remove() {
			if (!flNext )
				throw new IllegalStateException();
			listIteratorToRemove.remove();
			size--;
			flNext = false;
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
			if ( ( float )size / hashTable.length >= factor ) {
				collectStat(hashTable.length, 3);
				hashTableReallocation();
			}
			addObjInHashTable( obj, hashTable );
			size++;
			result = true;
		}
		return result;
	}

	private void collectStat(int length, int i) {
		collectedStats.addAll( 
				Arrays.stream(hashTable)
				.filter(e->(Objects.nonNull(e) && e.size() >= i))
				.map(e->e.size()).toList());
		
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
