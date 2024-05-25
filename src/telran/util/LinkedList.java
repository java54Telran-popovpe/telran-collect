package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
	
	Node<T> head;
	Node<T> tail;
	int size;
	
	private static class Node<T> {
		T data;
		Node<T> prev;
		Node<T> next;
		Node(T data) {
			this.data = data;
		}
	} 

	@Override
	//Time complexity O(1)
	public boolean add(T obj) {
		Node<T> node = new Node<>(obj);
		addNode(size, node);
		return true;
	}

	@Override
	//Time complexity O(n)
	public boolean remove(T pattern) {
		boolean result = false;
		int indexOfPattern = indexOf(pattern);
		if ( indexOfPattern > -1 ) {
			remove(indexOfPattern);
			result = true;
		}
		return result;
	}

	@Override
	//Time complexity O(n)
	public boolean contains(T pattern) {
		return indexOf(pattern) > -1;
	}

	@Override
	//Time complexity O(1)
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private Node<T> currentNode = head;
			
			@Override
			public boolean hasNext() {
				return currentNode != null;
			}

			@Override
			public T next() {
				if (!hasNext())
					throw new NoSuchElementException();
				T valueToReturn = currentNode.data;
				currentNode = currentNode.next;
				return valueToReturn;
			}
			
		};
	}

	@Override
	//Time complexity O(n)
	public T get(int index) {
		List.checkIndex(index, size - 1 );
		return getNode(index).data;
	}

	@Override
	//Time complexity O(n)
	public void add(int index, T obj) {
		
		List.checkIndex(index, size);
		Node<T> node = new Node<>(obj);
		addNode(index, node);
	}

	@Override
	//Time complexity O(n)
	public T remove(int index) {
		List.checkIndex(index, size - 1 );
		Node<T> nodeToDelete = getNode(index);
		T deletedData = nodeToDelete.data;
		deleteNode(nodeToDelete, index);
		return deletedData;
	}

	private void deleteNode(Node<T> nodeToDelete, int index) {
		if (size == 1 )
			deleteLonelyNode( nodeToDelete );
		else if ( index == 0 )
			deleteHeadNode( nodeToDelete );
		else if( index == size - 1 )
			deleteTailNode( nodeToDelete );
		else
			deleteInBetweenNode( nodeToDelete );
		size--;
		
	}

	private void deleteLonelyNode(Node<T> nodeToDelete) {
		head = tail = null;
	}

	private void deleteInBetweenNode(Node<T> nodeToDelete) {
		Node<T> nodePrev = nodeToDelete.prev;
		Node<T> nodeNext = nodeToDelete.next;
		nodePrev.next = nodeNext;
		nodeNext.prev = nodePrev;
	}

	private void deleteTailNode(Node<T> nodeToDelete) {
		Node<T> nodePrev = nodeToDelete.prev;
		nodePrev.next = null;
		tail = nodePrev;
	}

	private void deleteHeadNode(Node<T> nodeToDelete) {
		Node<T> nodeNext = nodeToDelete.next;
		nodeNext.prev = null;
		head = nodeNext;
	}

	@Override
	//Time complexity O(n)
	public int indexOf(T pattern) {
		Node<T> nodeToCheck = head;
		int currentIndex = -1;
		boolean patternFound = false;
		while( nodeToCheck != null && !patternFound) {
			currentIndex++;
			patternFound = Collection.compareNullable(nodeToCheck.data, pattern);
			nodeToCheck = nodeToCheck.next;
		}
		return patternFound ? currentIndex : -1;
	}

	@Override
	//Time complexity O(n)
	public int lastIndexOf(T pattern) {
		Node<T> nodeToCheck = tail;
		int currentIndex = size;
		boolean patternFound = false;
		while( nodeToCheck != null && !patternFound) {
			currentIndex--;
			patternFound = Collection.compareNullable(nodeToCheck.data, pattern);
			nodeToCheck = nodeToCheck.prev;
		}
		return patternFound ? currentIndex : -1;
	}
	private Node<T> getNode( int index ) {
		return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
	}

	private Node<T> getNodeFromTail(int index) {
		Node<T> current = tail;
		for( int i = size - 1; i > index; i-- ) {
			current = current.prev;
		}
		return current;
	}

	private Node<T> getNodeFromHead(int index) {
		Node<T> current = head;
		for( int i = 0; i < index; i++ ) {
			current = current.next;
		}
		return current;
	}
	
	private void addNode( int index, Node<T> node) {
		
		if ( index == 0 ) {
			addHead( node );
		} else if ( index == size) {
			addTail( node );
		} else {
			addMiddle( node, index );
		}
		size++;
	}

	private void addMiddle(Node<T> node, int index) {
		Node<T> nodeNext = getNode( index );
		Node<T> nodePrev = nodeNext.prev;
		nodeNext.prev = node;
		nodePrev.next = node;
		node.prev = nodePrev;
		node.next = nodeNext;
	}

	private void addTail(Node<T> node) {
		tail.next = node;
		node.prev = tail;
		tail = node;
	}

	private void addHead(Node<T> node) {
		if ( head == null ) {
			head = tail = node;
		} else {
			node.next = head;
			head.prev = node;
			head = node;
		}
	}

}
