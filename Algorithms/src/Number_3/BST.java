package Number_3;

/**
 * P252 算法3.3 基于二叉查找树的符号表(有序)
 * 
 * @author he
 *
 */
public class BST<Key extends Comparable<Key>, Value> {

	private Node root;// 根结点

	/**
	 * 结点类，用于储存键-值对、左右结点以及结点计数器 size(x)=size(x.left)+size(x.right)+1;
	 * 
	 * @author he
	 *
	 */
	private class Node {
		private Key key;// 键
		private Value value;// 值
		private Node left, right;// 指向左右子树的连接
		private int N;// 以该结点为根的子树中的结点数

		public Node(Key key, Value value, int N) {
			this.key = key;
			this.value = value;
			this.N = N;
		}

	}

	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null) {
			return 0;
		} else {
			return x.N;
		}
	}

	// 根据键返回值
	public Value get(Key key) {
		return get(root, key);
	}

	// 递归遍历二叉查找树
	private Value get(Node x, Key key) {
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			return get(x.left, key);
		} else if (cmp > 0) {
			return get(x.right, key);
		} else {
			return x.value;
		}

	}

	// 添加键值对
	public void put(Key key, Value value) {
		root = put(root, key, value);
	}

	// 始终返回的是根结点
	private Node put(Node x, Key key, Value value) {
		// 如果key存在则更新，否则添加新结点
		if (x == null) {
			return new Node(key, value, 1);
		}

		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			x.left = put(x.left, key, value);
		} else if (cmp > 0) {
			x.right = put(x.right, key, value);
		} else {
			x.value = value;
		}
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	// 返回最小键
	public Key min() {
		return min(root).key;
	}

	private Node min(Node x) {
		if (x.left == null) {
			return x;
		}
		return min(x.left);
	}

	// 返回最大键
	public Key max() {
		return max(root).key;
	}

	private Node max(Node x) {
		if (x.right == null) {
			return x;
		} else {
			return max(x.right);
		}
	}

	// 向下取整，小于等于key的最大键
	public Key floor(Key key) {
		Node x = floor(root, key);
		if (x == null) {
			return null;
		} else {
			return x.key;
		}
	}

	/**
	 * 如果key小于根节点则向下取整一定左子树中， 如果大于根结点，则可能在右子树中如果没有，则根结点就是满足条件的key
	 *
	 * @param x
	 * @param key
	 * @return
	 */
	private Node floor(Node x, Key key) {
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			return floor(x.left, key);
		}

		Node t = floor(x.right, key);
		if (t != null) {
			return t;
		} else {
			return x;
		}

	}

	// 向上取整,大于等于key的最小键
	public Key ceiling(Key key) {
		Node x = ceiling(root, key);
		if (x == null) {
			return null;
		} else {
			return x.key;
		}
	}

	/**
	 * 如果key大于根结点则向上取整一定在右子树中，如果key小于根结点，则可能在左子树中， 如果为未命中，则根结点就是向上取整的值
	 * 
	 * @param x
	 * @param key
	 * @return
	 */
	private Node ceiling(Node x, Key key) {
		if (x == null) {
			return null;
		}
		int cmp = key.compareTo(x.key);
		if (cmp == 0) {
			return x;
		}
		if (cmp > 0) {
			return ceiling(x.right, key);
		}

		Node t = ceiling(x.left, key);
		if (t != null) {
			return t;
		} else {
			return x;
		}

	}

	// 根据索引查找键
	public Key select(int k) {
		return select(root, k).key;
	}

	/**
	 * 如果左子树的结点数大于k，则递归地在左子树中查找排名为k的键； 如果t等于k，在返回根结点的键；
	 * 如果t小于k，递归地在右子树中查找排名为（k-t-1）的键
	 * 
	 * @param x
	 * @param k
	 * @return
	 */
	private Node select(Node x, int k) {
		// 返回排名为k的结点
		if (x == null) {
			return null;
		}
		int t = size(x.left);
		if (t > k) {
			return select(x.left, k);
		} else if (t < k) {
			return select(x.right, k - t - 1);
		} else {
			return x;
		}
	}

	// 根据键返回下下标（排名）
	public int rank(Key key) {
		return rank(root, key);
	}

	/**
	 * 如果给定的键和根结点的键相等，返回根节点左子树的结点总数t size(x.left); 如果给定的键比根结点的键小,递归计算在做子树的排名；
	 * 如果给定的键比根结点的键大，返回根结点左子树结点总数t+1+它在右子树的排名
	 * 
	 * @param x
	 * @param key
	 * @return
	 */
	private int rank(Node x, Key key) {
		if (x == null) {
			return 0;
		}

		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			return rank(x.left, key);
		} else if (cmp > 0) {
			return 1 + size(x.left) + rank(x.right, key);
		} else {
			return size(x.left);
		}

	}

	public static void main(String[] args) {
		BST<String, Integer> bst = new BST<String, Integer>();
		bst.put("S", 0);
		bst.put("E", 1);
		bst.put("B", 2);
		bst.put("E", 6);
		System.out.println(bst.get("E"));
		System.out.println(bst.min());
		System.out.println(bst.max());
		System.out.println(bst.floor("A"));
		System.out.println(bst.ceiling("C"));
		System.out.println(bst.select(1));
		System.out.println(bst.rank("S"));

	}
}
