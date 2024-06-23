package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import telran.util.LinkedList.Node;

public class LinkedHashSet<T> extends AbstractCollection<T> implements Set<T>{
	
	HashMap<T, Node<T>> map = new HashMap<T, Node<T>>();
	LinkedList<T> list = new LinkedList<T>();
	
	@Override
	public boolean add(T obj) {
		boolean result = false;
		Node<T> node = map.get(obj);
		if(node == null ) {
			addNewElement(obj);
			size++;
			result = true;
		}
		return result;
	}
	private void addNewElement(T obj) {
		Node<T> node = new Node<>(obj);
		map.put(obj, node);
		list.addNode(size, node);
	}
	
	@Override
	public boolean remove(T pattern) {
		boolean result = false;
		Node<T> node = map.remove(pattern);
		if ( node != null ) {
			list.deleteNode(node);
			size--;
			result = true;
		}
		return result;
	}
	@Override
	public boolean contains(T pattern) {
		
		return map.get(pattern) != null;
	}
	@Override
	public Iterator<T> iterator() {
		return new LinkedHashSetIterator();
	}
	@Override
	public T get(T pattertn) {
		Node<T> node = map.get(pattertn);
		return node != null ? node.data : null;
	}
	
	
	class LinkedHashSetIterator implements Iterator<T> {
		
		Iterator<T> listIterator = list.iterator();
		T prev = null;

		@Override
		public boolean hasNext() {
			return listIterator.hasNext();
		}

		@Override
		public T next() {
			if (!hasNext())
				new NoSuchElementException();
			prev = listIterator.next();
			return prev;
		}
		
		@Override
		public void remove() {
			if ( prev == null ) {
				throw new IllegalStateException();
			}
			listIterator.remove();
			map.remove(prev);
			size--;
			prev = null;
		}
	}


}
