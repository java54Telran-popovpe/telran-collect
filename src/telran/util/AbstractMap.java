package telran.util;

public abstract class AbstractMap<K, V> implements Map<K, V> {
	
	protected Set<Entry<K,V>> set;
	
	abstract protected Set<K> getEmptyKeySet();

	@Override
	public V get(K key) {
		Entry<K,V> patternEntry = new Entry<>(key, null);
		Entry<K,V> result = set.get( patternEntry );
		return result != null ? result.getValue() : null;
	}

	@Override
	public V put(K key, V value) {
		V result = null;
		Entry<K,V> entryWithKey = set.get(new Entry<>(key, null));
		if (entryWithKey == null ) {
			set.add( new Entry<K,V>( key, value) );
		} else {
			result = entryWithKey.getValue();
			entryWithKey.setValue(value);
		}
		return result;
	}

	@Override
	public V remove(K key) {
		V result = null;
		Entry<K,V> entryWithKey = set.get(new Entry<>(key, null));
		if (entryWithKey != null ) {
			result = entryWithKey.getValue();
			set.remove(entryWithKey);
		}
		return result;
	}

	@Override
	public Set<K> keySet() {
		Set<K> keys = getEmptyKeySet();
		set.forEach( e -> keys.add(e.getKey()));
		return keys;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return set;
	}

	@Override
	public Collection<V> values() {
		ArrayList<V> res = new ArrayList<>();
		set.forEach(e -> res.add(e.getValue()));
		return res;
	}

}
