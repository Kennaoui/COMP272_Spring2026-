import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



// =====================================================================
// Node class
// =====================================================================

/**
 * A node (position) in the linked tree.
 * Public getters, but mutations are controlled by the tree for consistency.
*/
static class Node<T> {

    // -------- State -----------
    final LinkedTree<T> owner;
    T element;
    Node<T> parent;
    final List<Node<T>> children;

    // ----- Constructor ---------
    Node(LinkedTree<T> owner, T element, Node<T> parent) {
        this.owner = owner;
        this.element = element;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    // -----Getters---------------
    T getElement() { return element;}

    Node<T> getParent() { return parent;} 


    List<Node<T>> getChildren() { return children; }

    @Override
    String toString() { return String.valueOf(element); }

    // -------Setters------------
    void setElement(T e) { //setElementInternal
        this.element = e;
    }

    void addChild(Node<T> child) {
        // child.parent already set in constructor; keep it consistent anyway
        child.parent = this;
        children.add(child);
    }
}


/**
 * A general linked-tree implementation.
 */
public class LinkedTree<T> implements Iterable<LinkedTree.Node<T>> {

    // ----- State -----
    private Node<T> root;
    private int size;

    // ----- Constructor -----
  
    public LinkedTree() {
        root = null;
        size = 0;
    }

    // ----- Tree ADT: Generic methods -----

    /** Returns the number of nodes in the tree. */
    public int size() {
        return size;
    }

    /** Returns true if the tree has no nodes. */
    public boolean isEmpty() {
        return size == 0;
    }


    // ----- Tree ADT: Accessor methods -----

    /** Returns the root node of the tree (or null if empty). */
    public Node<T> root() {
        return root;
    }

    /** Returns the parent of node p (or null if p is root). */
    public Node<T> parent(Node<T> p) {
        Node<T> node = validate(p);
        return node.getParent();
    }

  
    // ----- Internal validation helper -----

    private Node<T> validate(Node<T> p) {
        if (p == null) {
            throw new IllegalArgumentException("Node must not be null.");
        }
        if (p.owner != this) {
            throw new IllegalArgumentException("Node does not belong to this tree.");
        }
        return p;
    }
  
    // ----- Tree ADT: Update method -----

    /**
     * Adds a root to an empty tree and returns it.
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
     * Adds a new child with element e to parent p and returns the new child.
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
     */
    public T replace(Node<T> p, T e) {
        Node<T> node = validate(p);
        T old = node.elementInternal();
        node.setElementInternal(e);
        return old;
    }

   // ----- Tree ADT: Query methods -----
  
    /** Returns true if p has at least one child. */
    public boolean isInternal(Node<T> p) {
        return !isExternal(p);
    }

    /** Returns true if p has no children. */
    public boolean isExternal(Node<T> p) {
        Node<T> node = validate(p);
        return node.childrenInternal().isEmpty();
    }

    /** Returns true if p is the root of this tree. */
    public boolean isRoot(Node<T> p) {
        Node<T> node = validate(p);
        return node == root;
    }

    // ----- Iterators over nodes-----

    /** Returns an iterable of the children of node p in order. (Accesor) */
    public Iterable<Node<T>> getChildren(Node<T> p) {
        Node<T> node = validate(p);
        // return an unmodifiable view to protect representation
        return node.);
    }
  
    /** Returns an iterator over all nodes. (Generic)*/
    @Override
    public Iterator<Node<T>> iterator() {
        return nodes().iterator();
    }

    /**
     * Returns an iterable over all nodes in the tree.
     * preorder traversal (root, then children left-to-right).
     */
    public Iterable<Node<T>> nodes() {
        List<Node<T>> snapshot = new ArrayList<>(size);
        if (root != null) {
            preorder(root, snapshot);
        }
        return snapshot;
    }

    private void preorder(Node<T> p, List<Node<T>> out) {
        out.add(p);
        for (Node<T> child : p.childrenInternal()) {
            preorder(child, out);
        }
    }

    // ----- Additional methods-----
  
        public int depth(Node<T> p) {
        Node<T> node = validate(p);
    
        int depth = 0;
    
        while (!isRoot(node)) {
            node = node.getParent();
            depth++;
        }
    
        return depth;
    }

    public int height(Node<T> p) {
        Node<T> node = validate(p);
    
        return heightHelper(node);
    }
    
    private int heightHelper(Node<T> p) {
    
        // Leaf case
        if (isExternal(p)) {
            return 0;
        }
    
        int max = 0;
    
        for (Node<T> child : p.getChildren()) {
            max = Math.max(max, heightHelper(child));
        }
    
        return max + 1;
    }

}
