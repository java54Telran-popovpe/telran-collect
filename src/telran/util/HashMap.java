package telran.util;

public class HashMap<K, V> extends AbstractMap<K, V> {

	@Override
	protected Set<K> getEmptyKeySet() {
		// TODO Auto-generated method stub
		return new HashSet<K>();
	}
	
	public HashMap() {
		set = new HashSet<Entry<K,V>>();
	}

}
