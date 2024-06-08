package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeSet<T> extends AbstractCollection<T> implements SortedSet<T> {
	private static class  Node<T> {
		T data;
		Node<T> parent;
		Node<T> left;
		Node<T> right;
		Node(T data) {
			this.data = data;
		}
	}
	Node<T> root;
	Comparator<T> comp;

	public TreeSet(Comparator<T> comp ) {
		this.comp = comp;
	}
	
	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>)Comparator.naturalOrder());
	}
	@Override
	public T get(T pattern) {
		Node<T> node = getNode(pattern);
		return node != null ? node.data : null;
	}

	
	
	private Node<T> getNode(T pattern) {
		Node<T> node = getParentOrNode(pattern);
		return node != null && comp.compare(pattern, node.data) == 0 ? node : null;
	}

	private Node<T> getParentOrNode(T pattern) {
		Node<T> current = root;
		Node<T> parent = null;
		int compareResult = 0;
		while( current != null && (compareResult = comp.compare(pattern, current.data)) != 0 ) {
			parent = current;
			current = compareResult > 0 ? current.right : current.left;
		}
		return current == null ? parent : current;
	}

	@Override
	public boolean add(T obj) {
		boolean result = false;
		if( obj == null) {
			throw new NullPointerException();
		}
		
		if ( !contains(obj)) {
			result = true;
			Node<T> node = new Node<>(obj);
			if ( root == null) {
				addRoot(node);
			} else {
				addWithParent(node);
			}
			size++;
		}
		return result;
	}

	private void addWithParent(Node<T> node) {
		Node<T> parent = getParentOrNode(node.data);
		if (comp.compare(node.data, parent.data) > 0 ) {
			parent.right = node;
		} else {
			parent.left = node;
		}
		node.parent = parent;
	}

	private void addRoot(Node<T> node) {
		root = node;
	}

	@Override
	public boolean remove(T pattern) {
		boolean result = false;
		Node<T> node = getNode(pattern);
		if ( node != null) {
			removeNode(node);
			result = true;
			size--;
		}
		return result;
	}

	private void removeNode(Node<T> node) {
		if ( node.left == null || node.right == null )
			removeNodeWithMaxTwoNeighbours( node );
		else {
			Node<T> nodeToDeleteInstead = getGreatestFrom(node.left);
			node.data = nodeToDeleteInstead.data;
			removeNodeWithMaxTwoNeighbours( nodeToDeleteInstead );
		}
	}
	private void removeNodeWithMaxTwoNeighbours(Node<T> node) {
		node.data = null;
		Node<T> childNode = node.left == null ? node.right : node.left;
		Node<T> parentNode = node.parent;
		if (parentNode != null ) {
			if ( parentNode.left == node )
				parentNode.left = childNode;
			else
				parentNode.right = childNode;
		} else {
			root = childNode;
		}
		if ( childNode != null )
				childNode.parent = parentNode;
	}

	@Override
	public boolean contains(T pattern) {
		return getNode(pattern) != null;
	}

	@Override
	public Iterator<T> iterator() {
		return new TreeSetIterator();
	}

	@Override
	public T first() {
		return root == null ? null : getLeastFrom(root).data;
	}

	private Node<T> getLeastFrom(Node<T> node) {
		if ( node != null) {
			while( node.left != null) {
				node = node.left;
			}
		}
		return node;
	}

	@Override
	public T last() {
		return root == null ? null : getGreatestFrom(root).data;
	}
	
	private Node<T> getGreatestFrom(Node<T> node) {
		if ( node != null) {
			while( node.right != null) {
				node = node.right;
			}
		}
		return node;
	}

	private class TreeSetIterator implements Iterator<T> {
		
		Node<T> current = getLeastFrom(root);
		Node<T> nodeToDelete = null;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			nodeToDelete = current;
			T res = current.data;
			current = getCurrent(current);
			return res;
		}
		
		@Override
		public void remove() {
			if (nodeToDelete == null )
				throw new IllegalStateException();
			removeNode(nodeToDelete);
			size--;
			nodeToDelete = null;
		}
		
	}

	private Node<T> getCurrent(Node<T> current) {
			return current.right != null ? getLeastFrom(current.right) : getFirstGreaterFromParents(current);
	}

	private Node<T> getFirstGreaterFromParents(Node<T> node) {
		while ( node.parent != null && node.parent.right == node ) {
			node = node.parent;
		}
		return node.parent;
	}

}
