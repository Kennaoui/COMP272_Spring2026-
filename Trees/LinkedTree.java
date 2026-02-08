import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * LinkedTree<T>
 * ------------
 * A general-purpose, linked representation of a rooted tree.
 *
 * Key implementation ideas:
 * 1) Each node stores:
 *    - an element (the data)
 *    - a parent pointer (null for the root)
 *    - a list of children (0..k children)
 * 2) The tree object is responsible for structural mutations
 *    (adding root/children, validating nodes) to keep the structure consistent.
 * 3) We implement Iterable<Node<T>> so you can do:
 *       for (Node<T> n : tree) { ... }
 *    which uses a traversal order (here: preorder).
 */
public class LinkedTree<T> implements Iterable<LinkedTree.Node<T>> {

  // =====================================================================
  // Node class
  // =====================================================================

  /**
   * Node<T> represents a single position in the tree.
   *
   * Encapsulation / OOP design:
   * - Fields are private so outside code cannot break invariants.
   * - Only LinkedTree is allowed to:
   *     * create nodes (constructor is private)
   *     * change parent pointers
   *     * add children into a node's child list
   *
   * "owner" is a common defensive design:
   * - It prevents mixing nodes from two different trees.
   * - validate(...) checks this before doing any operation.
   */
  public static class Node<T> {

    // -------- State -----------
    private final LinkedTree<T> owner;     // which tree this node belongs to
    private T element;                     // the data stored at this node
    private Node<T> parent;                // parent pointer (null only for root)
    private final List<Node<T>> children;  // list of children (may be empty)

    //The keyword "final" means that this reference cannot be changed
    //after it is assigned.

    // ----- Constructor ---------
    // Private: only LinkedTree can create nodes
    private Node(LinkedTree<T> owner, T element, Node<T> parent) {
      this.owner = owner;
      this.element = element;
      this.parent = parent;
      this.children = new ArrayList<>();
    }

    // ----- Getters (safe read-only access) -----

    /** Returns the element stored at this node. */
    public T getElement() { return element; }

    /** Returns the parent node (null if this is the root). */
    public Node<T> getParent() { return parent; }

    /**
     * Returns an UNMODIFIABLE view of the children list.
     * Why?
     * - If we returned the real list, outside code could do children.add(...)
     *   and break the tree's invariants (size counter, parent pointers, etc.).
     */
    public List<Node<T>> getChildren() {
      return Collections.unmodifiableList(children);
    }

    @Override
    public String toString() {
      return String.valueOf(element);
    }

    // ----- Internal mutation helpers (ONLY the tree should use these) -----

    /** Replace the element stored in this node. (Safe: doesn't change structure.) */
    private void setElement(T e) {
      this.element = e;
    }

    /**
     * Add a child under this node.
     *
     * Invariant we enforce:
     * - child's parent pointer must be this node
     * - this node's children list must contain the child
     */
    private void addChild(Node<T> child) {
      child.parent = this;
      children.add(child);
    }

    /** Internal access to the mutable children list (tree-only). */
    private List<Node<T>> children() {
      return children;
    }
  }

  // =====================================================================
  // LinkedTree state
  // =====================================================================

  /**
   * Root pointer:
   * - null when the tree is empty
   * - otherwise points to the root node
   */
  private Node<T> root;

  /**
   * Size is maintained incrementally.
   * - addRoot sets size to 1
   * - addChild increments
   * This makes size() O(1).
   */
  private int size;

  // =====================================================================
  // Constructors
  // =====================================================================

  /** Constructs an empty tree. */
  public LinkedTree() {
    this.root = null;
    this.size = 0;
  }

  // =====================================================================
  // Tree ADT: Generic methods
  // =====================================================================

  /** Returns the number of nodes in the tree. O(1). */
  public int size() {
    return size;
  }

  /** Returns true if the tree has no nodes. O(1). */
  public boolean isEmpty() {
    return size == 0;
  }

  // =====================================================================
  // Tree ADT: Accessor methods
  // =====================================================================

  /** Returns the root node of the tree (or null if empty). O(1). */
  public Node<T> root() {
    return root;
  }

  /** Returns the parent of node p (or null if p is root). O(1). */
  public Node<T> parent(Node<T> p) {
    Node<T> node = validate(p);
    return node.getParent();
  }

  // =====================================================================
  // Validation helper
  // =====================================================================

  /**
   * validate(p) is a defensive-programming technique.
   *
   * Why it matters:
   * - Prevents NullPointerExceptions with a clearer message
   * - Prevents using nodes from another tree instance
   * - Helps preserve representation invariants
   */
  private Node<T> validate(Node<T> p) {
    if (p == null) {
      throw new IllegalArgumentException("Node must not be null.");
    }
    if (p.owner != this) {
      throw new IllegalArgumentException("Node does not belong to this tree.");
    }
    return p;
  }

  // =====================================================================
  // Tree ADT: Update methods
  // =====================================================================

  /**
   * Adds a root to an empty tree and returns it.
   *
   * Invariant:
   * - A tree has exactly one root (unless empty).
   *
   * Complexity: O(1)
   */
  public Node<T> addRoot(T e) {
    if (!isEmpty()) {
      throw new IllegalStateException("Tree already has a root.");
    }
    root = new Node<>(this, e, null);
    size = 1;
    return root;
  }

  /**
   * Adds a new child node storing element e under parent p.
   *
   * Implementation details:
   * - We create the child with parent pointer set to p.
   * - Then we add it to p's children list (and re-assert parent pointer).
   * - We increment size to keep size() O(1).
   *
   * Complexity: O(1) amortized (ArrayList add is amortized O(1)).
   */
  public Node<T> addChild(Node<T> p, T e) {
    Node<T> parent = validate(p);

    Node<T> child = new Node<>(this, e, parent);
    parent.addChild(child);

    size++;
    return child;
  }

  /**
   * Replaces the element stored at node p with e.
   * Returns the old element.
   *
   * - This is a "mutation" but does NOT change the tree shape.
   * - Therefore it doesn't affect parent/children pointers or size.
   *
   * Complexity: O(1).
   */
  public T replace(Node<T> p, T e) {
    Node<T> node = validate(p);
    T old = node.getElement();
    node.setElement(e);
    return old;
  }

  // =====================================================================
  // Tree ADT: Query methods
  // =====================================================================

  /** Returns true if p has at least one child. */
  public boolean isInternal(Node<T> p) {
    return !isExternal(p);
  }

  /**
   * Returns true if p has no children (leaf).
   *
   * Complexity: O(1) because ArrayList knows its size.
   */
  public boolean isExternal(Node<T> p) {
    Node<T> node = validate(p);
    return node.children().isEmpty();
  }

  /** Returns true if p is the root node of this tree. O(1). */
  public boolean isRoot(Node<T> p) {
    Node<T> node = validate(p);
    return node == root;
  }

  // =====================================================================
  // Iterable + Iterator support
  // =====================================================================

  /**
   * Implementing Iterable means: "this object can be used in a for-each loop".
   *
   * Iterable<T> requires:
   *   Iterator<T> iterator()
   *
   * Java for-each:
   *   Iterator<T> it = iterable.iterator();
   *   while (it.hasNext()) { T x = it.next(); ... }
   */
  @Override
  public Iterator<Node<T>> iterator() {
    return nodes().iterator();
  }

  /**
   * Returns an Iterable over all nodes using PREORDER traversal.
   *
   * Preorder definition:
   * - Visit the node itself
   * - Then recursively visit each child left-to-right
   *
   * - Many tree traversals exist (preorder, postorder, level-order, etc.)
   * - The traversal order defines what "iteration over the tree" means.
   *
   * Implementation detail:
   * - We build a snapshot list, then return it.
   * - This makes iteration simple and safe, but costs O(n) extra memory.
   * - An alternative is a "lazy iterator" that traverses on-the-fly.
   */
  public Iterable<Node<T>> nodes() {
    List<Node<T>> snapshot = new ArrayList<>(size);
    if (root != null) {
      preorder(root, snapshot);
    }
    return snapshot;
  }

  /**
   * Recursive preorder traversal helper.
   *
   * Complexity: O(n) total for a full traversal.
   * Space: O(h) recursion stack (h = height of tree) + O(n) snapshot list.
   */
  private void preorder(Node<T> p, List<Node<T>> out) {
    out.add(p);

    // We use internal children list because we're inside LinkedTree.
    for (Node<T> child : p.children()) {
      preorder(child, out);
    }
  }

  // =====================================================================
  // Additional methods: depth + height
  // =====================================================================

  /**
   * Depth of node p:
   * - number of edges on the path from p up to the root.
   *
   * Complexity: O(depth(p)) up the parent pointers.
   */
  public int depth(Node<T> p) {
    Node<T> node = validate(p);

    int depth = 0;
    while (!isRoot(node)) {
      node = node.getParent();
      depth++;
    }
    return depth;
  }

  /**
   * Height of the subtree rooted at p:
   * - leaf has height 0
   * - otherwise 1 + max height among children
   *
   * Complexity: O(size of subtree rooted at p)
   */
  public int height(Node<T> p) {
    Node<T> node = validate(p);
    return heightHelper(node);
  }

  private int heightHelper(Node<T> p) {
    if (isExternal(p)) {
      return 0; // leaf case
    }

    int max = 0;
    for (Node<T> child : p.children()) {
      max = Math.max(max, heightHelper(child));
    }
    return max + 1;
  }
}
