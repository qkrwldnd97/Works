import javax.swing.text.html.parser.Parser;
import java.util.*;
import java.io.*;



/**
 * RedBlackBST class
 *
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {

	private static final boolean RED   = true;
	private static final boolean BLACK = false;
	Node root;     // root of the BST


	private static int element=0;
	private static int num = 0;
	private static ArrayList<Object> abc = new ArrayList<Object>();




	public class Node {
		Key key;           // key
		Value val;         // associated data
		Node left, right;  // links to left and right subtrees
		boolean color;     // color of parent link
		int N;             // subtree count


		public Node(Key key, Value val, boolean color, int N) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.N = N;
		}
	}


	// is node x red; false if x is null ?
	private boolean isRed(Node x) {
		if (x == null) return false;
		return (x.color == RED);
	}

	// number of node in subtree rooted at x; 0 if x is null
	private int size(Node x) {
		if (x == null) return 0;
		return x.N;
	}

	// return number of key-value pairs in this symbol table
	public int size() { return size(root); }

	// is this symbol table empty?
	public boolean isEmpty() {
		return root == null;
	}

	public RedBlackBST() {
		this.root = null;
	}


	// insert the key-value pair; overwrite the old value with the new value
	// if the key is already present
	public void insert(Key key, Value val) {
		root = insert(root,key,val);
		root.color = BLACK;
//		element++;
//		Node a  = new Node(key,val,RED, 1);
//		abc.add(a);
	}
	private Node insert(Node tree, Key key, Value val){
		if(tree == null){
			return new Node(key, val, RED, 1);
		}

		if(tree.key.compareTo(key) > 0 ){
			tree.left = insert(tree.left,key,val);
		}else if(tree.key.compareTo(key) < 0){
			tree.right = insert(tree.right,key,val);
		}else{
			tree.val = val;
		}

		tree.N = size(tree.left) + size(tree.right) +1;

		return balance(tree);

	}


	// delete the key-value pair with the given key
	public void delete(Key key) {
		if (!contains(key)) {
			return;
		}else{
			if (!isRed(root.right) && !isRed(root.left)){
				root.color = RED;
			}
			root = delete(root, key);
			if (root != null){
			root.N = size(root.left) + size(root.right) +1;
			root.color = BLACK;
			}
		}
		System.out.println(root.N);
	}

	private Node delete(Node tree, Key key){
//		System.out.println(root.N);
		if (!(key.compareTo(tree.key) >= 0))  {
			if (!isRed(tree.left.left) && !isRed(tree.left)){
				tree = moveRedLeft(tree);
			}
			tree.left = delete(tree.left, key);
		}
		else {
			if (isRed(tree.left)) {
				tree = rotateRight(tree);
			}
			if ((tree.right == null) && key.compareTo(tree.key) == 0) {
				return null;
			}
			if (!isRed(tree.right.left) && !isRed(tree.right)) {
				tree = moveRedRight(tree);
			}
			if (key.compareTo(tree.key) == 0) {
				Node abc = tree.right;
				while(abc.left != null){
					abc = abc.left;
				}
				tree.key = abc.key;
				tree.val = abc.val;
				tree.right = delmin(tree.right);
			}
			else{
				tree.right = delete(tree.right, key);
			}
			tree.N = size(tree.right) + size(tree.left) +1;
		}
		return balance(tree);
	}
	private Node delmin(Node tree) {
		if (tree.left == null){
			return null;
		}

		if (!isRed(tree.left) && !isRed(tree.left.left)) {
			tree = moveRedLeft(tree);
		}
		tree.left = delmin(tree.left);
		return balance(tree);
	}

	// value associated with the given key; null if no such key
	public Value search(Key key) {
		Node temp = root;
		Boolean find = false;
		while(temp != null){
			if(temp.key.compareTo(key) == 0){
				find = true;
				break;
			}else if(temp.key.compareTo(key) > 0){
				temp = temp.left;
			}else{
				temp = temp.right;
			}
		}
		if(find) {
			return temp.val;
		}else{
			return null;
		}
	}

	// is there a key-value pair with the given key?
	public boolean contains(Key key) {
		return (search(key) != null);
	}

	// height of tree (1-node tree has height 0)
	public int height() { return height(root); }
	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	// the key of rank k
	public Key getValByRank(int k) {
		ArrayList<Object> getval = new ArrayList<Object>();
		inorder(root, getval);
		if(k >= getval.size() || k<0){
			return null;
		}
		return ((Node)getval.get(k)).key;

	}

	// number of keys less than key
	public int rank(Key key) {
		ArrayList<Object> getrank = new ArrayList<Object>();
		inorder(root, getrank);
		if(((Node)getrank.get(getrank.size()-1)).key.compareTo(key) < 0){
			return getrank.size();
		}
		for(int i=0; i<getrank.size(); i++){
			if(((Node)getrank.get(i)).key.compareTo(key) >=0){
				return i;
			}
		}
		return 0;
	}
	private void inorder(Node tree, ArrayList<Object> a){
		if(tree== null){
			return;
		}
		inorder(tree.left, a);
		a.add(tree);
		inorder(tree.right, a);

	}

	public List<Key> getElements(int a, int b) {
		ArrayList<Object> str = new ArrayList<Object>();
		ArrayList<Key> str2 = new ArrayList<Key>();
		inorder(root, str);
		if (a < 0 || b < 0 || a>=str.size() || b>=str.size()) {
			return new ArrayList<>();
		}
		for(int i = a; a<=b; a++){
			str2.add( ((Node)str.get(a)).key);
		}
		return str2;
	}

	// make a left-leaning link lean to the right
	private Node rotateRight(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = x.right.color;
		x.right.color = RED;
		x.N = h.N;
		h.N = size(h.left) + size(h.right) + 1;
		return x;
	}

	// make a right-leaning link lean to the left
	private Node rotateLeft(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = x.left.color;
		x.left.color = RED;
		x.N = h.N;
		h.N = size(h.left) + size(h.right) + 1;
		return x;
	}

	// flip the colors of a node and its two children
	private void flipColors(Node h) {
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private Node moveRedLeft(Node h) {
		flipColors(h);
		if (isRed(h.right.left)) {
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h) {
		flipColors(h);
		if (isRed(h.left.left)) {
			h = rotateRight(h);
		}
		return h;
	}

	// restore red-black tree invariant
	private Node balance(Node h) {
		// assert (h != null);

		if (isRed(h.right))                      h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))     flipColors(h);

		h.N = size(h.left) + size(h.right) + 1;
		return h;
	}

	public static void main(String[] args) {

		Scanner readerTest = null;

		try {
			//Change File name to test other test files.
			readerTest = new Scanner(new File("./src/insert.txt"));
		} catch (IOException e) {
			System.out.println("Reading Oops");
		}

		RedBlackBST<Integer, Integer> test = new RedBlackBST<>();

		while(readerTest.hasNextLine()){
			String[] input  =readerTest.nextLine().split(" ");

			for(String x: input){
				System.out.print(x+" ");
			}

			System.out.println();
			switch (input[0]){
				case "insert":
					Integer key = Integer.parseInt(input[1]);
					Integer val = Integer.parseInt(input[2]);
					test.insert(key,val);
					printTree(test.root);
					System.out.println();
					break;

				case "delete":
					Integer key1 = Integer.parseInt(input[1]);
					test.delete(key1);
					printTree(test.root);
					System.out.println();
					break;

				case "search":
					Integer key2 = Integer.parseInt(input[1]);
					Integer ans2 = test.search(key2);
					System.out.println(ans2);
					System.out.println();
					break;

				case "getval":
					Integer key3 = Integer.parseInt(input[1]);
					Integer ans21 = test.getValByRank(key3);
					System.out.println(ans21);
					System.out.println();
					break;

				case "rank":
					Integer key4 = Integer.parseInt(input[1]);
					Object ans22 = test.rank(key4);
					System.out.println(ans22);
					System.out.println();
					break;

				case "getelement":
					Integer low = Integer.parseInt(input[1]);
					Integer high = Integer.parseInt(input[2]);
					List<Integer> testList = test.getElements(low,high);

					for(Integer list : testList){
						System.out.println(list);
					}

					break;

				default:
					System.out.println("Error, Invalid test instruction! "+input[0]);
			}
		}

	}

	public static void printTree(RedBlackBST.Node node){

		if (node == null){
			return;
		}

		printTree(node.left);
		System.out.print(((node.color == true)? "Color: Red; ":"Color: Black; ") + "Key: " + node.key + "\n");
		printTree(node.right);
	}
}