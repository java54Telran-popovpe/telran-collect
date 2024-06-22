package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> extends AbstractCollection<T> implements List<T> {
	
	Node<T> head;
	Node<T> tail;
	
	 static class Node<T> {
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
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private Node<T> currentNode = head;
			private Node<T> prev = null;
			
			@Override
			public boolean hasNext() {
				return currentNode != null;
			}

			@Override
			public T next() {
				if (!hasNext())
					throw new NoSuchElementException();
				prev = currentNode;
				currentNode = currentNode.next;
				return prev.data;
			}
			
			@Override
			public void remove() {
				if (prev == null)
					throw new IllegalStateException();
				deleteNode(prev);
				prev = null;
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
		deleteNode(nodeToDelete);
		return deletedData;
	}

	void deleteNode(Node<T> nodeToDelete) {
		if (size == 1 )
			deleteLonelyNode();
		else if ( nodeToDelete == head )
			deleteHeadNode();
		else if( nodeToDelete == tail )
			deleteTailNode( );
		else
			deleteInBetweenNode( nodeToDelete );
		size--;
		nodeToDelete.data = null;
	}
	



	private void deleteLonelyNode() {
		head = tail = null;
	}

	private void deleteInBetweenNode(Node<T> nodeToDelete) {
		Node<T> nodePrev = nodeToDelete.prev;
		Node<T> nodeNext = nodeToDelete.next;
		nodePrev.next = nodeNext;
		nodeNext.prev = nodePrev;
		nodeToDelete.prev = nodeToDelete.next = null;
	}

	private void deleteTailNode() {
		tail = tail.prev;
		tail.next = null;
	}

	private void deleteHeadNode() {
		head = head.next;
		head.prev = null;
	}

	@Override
	//Time complexity O(n)
	public int indexOf(T pattern) {
		Node<T> nodeToCheck = head;
		int currentIndex = -1;
		boolean patternFound = false;
		while( nodeToCheck != null && !patternFound) {
			currentIndex++;
			patternFound = Objects.equals(nodeToCheck.data, pattern);
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
			patternFound = Objects.equals(nodeToCheck.data, pattern);
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
	
	void addNode( int index, Node<T> node) {
		
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
