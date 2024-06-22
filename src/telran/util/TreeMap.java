package telran.util;

import java.util.Comparator;

public class TreeMap<K, V> extends AbstractMap<K, V> implements SortedMap<K, V> {
	
	@Override
	public K firstKey() {
		Entry<K,V> entry = ((SortedSet<Entry<K,V>>)set).first();
		return entry != null ? entry.getKey() : null;
	}

	@Override
	public K lastKey() {
		Entry<K,V> entry = ((SortedSet<Entry<K,V>>)set).last();
		return entry != null ? entry.getKey() : null;
	}

	@Override
	public K floorKey(K key) {
		Entry<K,V> pattern = new Entry<>(key, null);
		Entry<K,V> entry = ((SortedSet<Entry<K,V>>)set).floor(pattern);
		return entry != null ? entry.getKey() : null;
	}

	@Override
	public K ceilingKey(K key) {
		Entry<K,V> pattern = new Entry<>(key, null);
		Entry<K,V> entry = ((SortedSet<Entry<K,V>>)set).ceiling(pattern);
		return entry != null ? entry.getKey() : null;	
	}

	@Override
	protected Set<K> getEmptyKeySet() {
		return new TreeSet<K>();
	}
	
	public TreeMap() {
		set = new TreeSet<>();
		
	}
	
	public TreeMap( Comparator<Entry<K,V>> comp) {
		set = new TreeSet<>(comp);
	}

}
