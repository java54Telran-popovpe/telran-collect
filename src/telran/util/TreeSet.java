package telran.util;


import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

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
	private static final int DEFAULT_SPACES_PER_LEVEL = 2;
	Node<T> root;
	Comparator<T> comp;
	private int spacesPerLevel = DEFAULT_SPACES_PER_LEVEL;

	public int getSpacesPerLevel() {
		return spacesPerLevel;
	}

	public void setSpacesPerLevel(int spacesPerLevel) {
		this.spacesPerLevel = spacesPerLevel;
	}

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
		Node<T> node = getParentOrNode(pattern, GetParentOrNodeReturnMode.PARENT_OR_NODE);
		return node != null && comp.compare(pattern, node.data) == 0 ? node : null;
	}

	private Node<T> getParentOrNode(T pattern, GetParentOrNodeReturnMode mode) {
		Node<T> current = root;
		Node<T> parent = null;
		Node<T> lastRightTurn = null;
		Node<T> lastLeftTurn = null;
		int compareResult = 0;
		while( current != null && (compareResult = comp.compare(pattern, current.data)) != 0 ) {
			parent = current;
			if (compareResult > 0) {
				lastRightTurn = current;
				current = current.right;
			} else {
				lastLeftTurn = current;
				current = current.left;
			}
		}
		return 	switch(mode) {
					case PARENT_OR_NODE -> current == null ? parent : current;
					case CEILING -> current != null ? current : lastLeftTurn;
					case FLOOR -> current != null ? current : lastRightTurn;
				};
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
		Node<T> parent = getParentOrNode(node.data, GetParentOrNodeReturnMode.PARENT_OR_NODE);
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
			return current.right != null ? getLeastFrom(current.right) : getFirstParentIfNot(current, node -> node.parent.right == node);
	}


	private Node<T> getFirstParentIfNot( Node<T> node, Predicate<Node<T>> predicate) {
		while ( node.parent != null && predicate.test(node) ) {
			node = node.parent;
		}
		return node.parent;
	}

	@Override
	public T ceiling(T key) {
		Node<T> ceilingNode = getParentOrNode(key, GetParentOrNodeReturnMode.CEILING);
		return ceilingNode == null ? null : ceilingNode.data;
	}

	
	@Override
	public T floor(T key) {
		Node<T> floorNode = getParentOrNode(key, GetParentOrNodeReturnMode.FLOOR);
		return floorNode == null ? null : floorNode.data;
	}
	/**
	 * display tree in the following view
	 * -20
	 * 		10
	 * 			1
	 * 				-5
	 * 			100
	 */
	public void displayRootChildren() {
		displayRootChildren( root, 1);
	}
	
	private void displayRootChildren(Node<T> tmpRoot, int level) {
		if ( tmpRoot != null ) {
			displayRoot(tmpRoot, level);
			displayRootChildren(tmpRoot.left, level + 1);
			displayRootChildren(tmpRoot.right, level + 1);
		}
	}

	/**
	 * conversion of tree so that iterating has been in the inversing order 
	 */
	public void treeInversion() {
		comp = comp.reversed();
		treeInversion(root);
	}
		
	private void treeInversion(Node<T> tmpNode) {
		if ( tmpNode != null ) {
				treeInversion(tmpNode.left);
				treeInversion(tmpNode.right);
				Node<T> tmp = tmpNode.left;
				tmpNode.left = tmpNode.right;
				tmpNode.right = tmp;
		}
	}

	/**
	 * displays tree in the following form
	 * 				100
	 *			10
	 *				1
	 *					-5
	 *	-20
	 */
	public void displayTreeRotated() {
		displayTreeRotated(root, 1);
	}

	private void displayTreeRotated(Node<T> tmpRoot, int level) {
		if (tmpRoot != null) {
			displayTreeRotated(tmpRoot.right, level + 1);
			displayRoot(tmpRoot, level);
			displayTreeRotated(tmpRoot.left, level + 1);
		}
	}

	private void displayRoot(Node<T> tmpRoot, int level) {
		System.out.printf("%s", " ".repeat(level * spacesPerLevel ));
		System.out.println(tmpRoot.data);
	}
	/**
	 * 
	 * @return number of leaves ( node with both left and right nulls
	 */
	public int width() {
		return width(root);
	}
	private int width(Node<T> tmpRoot) {
		int result = 0;
		if ( tmpRoot != null) {
			if ( tmpRoot.left == null && tmpRoot.right == null ) {
				result = 1;
			} else {
				result = width(tmpRoot.left) + width(tmpRoot.right);
			}
		}
		return result;
	}

	/**
	 * 
	 * @return numberv of the nodes of the longest route
	 */
	public int height() {
		
		return height(root);
	}

	private int height(Node<T> tmpRoot) {
		int result = 0;
		if (tmpRoot != null ) {
			int heightLeft = height(tmpRoot.left);
			int heighRight = height(tmpRoot.right);
			result = 1 + Math.max(heightLeft, heighRight);
		}
		return result;
	}

	public void balance() {
		//Sorted array of tree nodes
		if ( root != null ) {
			Node<T>[] arrayNodes = getNodesArray();
			root = balanceArray( arrayNodes, 0, size - 1, null);
		}
	}

	private Node<T> balanceArray(Node<T>[] arrayNodes, int left, int right, Node<T> parent) {
		Node<T> root = null;
		if ( left <= right ) { 
			int indexRoot = ( left + right ) / 2;
			root = arrayNodes[indexRoot];
			root.parent = parent;
			root.left = balanceArray(arrayNodes, left, indexRoot - 1, root);
			root.right = balanceArray(arrayNodes, indexRoot + 1, right, root);
		}
		
		return root;
	}

	private Node<T>[] getNodesArray() {
		@SuppressWarnings("unchecked")
		Node<T>[] result = new Node[size];
		Node<T> current = getLeastFrom(root);
		for( int i = 0; i < size; i++ )  {
			result[i] = current;
			current = getCurrent(current);
		}
		return result;
	}
	

}
