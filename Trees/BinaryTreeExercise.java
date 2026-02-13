package javalucproject;

import java.util.Stack;
//import java.util.ArrayList;

class BinaryTreeNode{
	
    private int data;
    private BinaryTreeNode leftChild = null;
    private BinaryTreeNode rightChild = null;
    private BinaryTreeNode parent = null;

    //Constructor
    public BinaryTreeNode(int d){
        data = d;
    }

    //Accessor behavior
    public BinaryTreeNode getLeftChild(){
        return leftChild;
    }

    public BinaryTreeNode getRightChild(){
        return rightChild;
    }

    public BinaryTreeNode getParent(){
        return parent;
    }

    public int data(){
        return data;
    }

    //Mutator behavior

    //Setters
    public void setParent(BinaryTreeNode p){
        parent = p;
    }

    public void setData(int d){
        data = d;
    }

    //Private helper to create and attach a child to the node
    private BinaryTreeNode addLeftOrRightChild(int d, boolean isleft){
        BinaryTreeNode n = new BinaryTreeNode(d);
        n.setParent(this);

        if(isleft)
            leftChild = n;
        else
            rightChild = n;

        return n;
    }

    //Add a left child if empty, otherwise returns null
    public BinaryTreeNode addLeftChild(int d){
        BinaryTreeNode n = null;

        if(leftChild == null){
            n = addLeftOrRightChild(d, true);
        }

        return n;
    }

    //Add a right child if empty, otherwise returns null
    public BinaryTreeNode addRightChild(int d){
        BinaryTreeNode n = null;

        if(rightChild == null){
            n = addLeftOrRightChild(d, false);
        }

        return n;
    }

    //Try to add left child, else right child, else returns null (This is a policy decision, not a property of all binary trees!!)
    public BinaryTreeNode addChild(int d){

        BinaryTreeNode n = addLeftChild(d);
        if(n == null)
            n = addRightChild(d);
        return n;
    }
}


class BinaryTree {
    private BinaryTreeNode root;
    private int size;

    // Constructor
    public BinaryTree(){
        root = null;
        size = 0;
    }

    public BinaryTreeNode getRoot(){
        return root;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return root == null;
    }


	// Search in BST order

	private BinaryTreeNode searchPosition(int value){
	    BinaryTreeNode current = root;
	    BinaryTreeNode parent = null;
		boolean found = false;
		
	
	    while(current != null && !found){
	        parent = current;
	
	        if(value == current.data())
			{
	            found = true;
			}
	
	        else if(value < current.data())
	           	 	current = current.getLeftChild();
	       		 else
	            	current = current.getRightChild();
	    }
	
	    return parent;
	}

	public BinaryTreeNode search(int value){
        BinaryTreeNode node = searchPosition(value);	
		
        if (node!= null && node.data() != value)
			node = null;
		
        return node;
    }


    // Insert in BST order (no duplicates)
    public boolean insertBST(int value){
        BinaryTreeNode node = searchPosition(value);

		// if node is null, that means that the tree is empty. So we add a root, increase the size and return true. 
        if(node == null){
            root = new BinaryTreeNode(value);
            size++;
            return true;
        }

		int curVal = node.data();

		//if node is not null, but its data is equal to value, we return false
		if(curVal == value)
			return false;

		//Otherwise we insert left or right, increase the size and return true. 
		if(value < curVal) 
			node.addLeftChild(value);
		else 
			node.addRightChild(value);
		size++;
		return true;
	}


}


public class BinaryTreeExercise {
    public static void main(String[] args){
        //create root node
        BinaryTreeNode root = new BinaryTreeNode(1);
        //add children to root node
        BinaryTreeNode child1 = root.addChild(2);
        BinaryTreeNode child2 = root.addChild(3);
        //add children to child nodes
        BinaryTreeNode child11 = child1.addChild(4);
        BinaryTreeNode child12 = child1.addChild(5);

        BinaryTreeNode child21 = child2.addChild(6);
        BinaryTreeNode child22 = child2.addChild(7);

        //call methods
        preOrderTraversal(root);
        inOrderTraversal(root);

        // Example usage of BST-based BinaryTree
        BinaryTree bst = new BinaryTree();
        bst.insertBST(10);
        bst.insertBST(5);
        bst.insertBST(15);
        bst.insertBST(3);
        bst.insertBST(7);

        BinaryTreeNode found = bst.search(7);
        if(found != null)
            System.out.println("\nFound: " + found.data());
        else
            System.out.println("\nNot found");
    }

    //write a method to implement the preorder traversal
    //pseudocode from the slides
    public static void preOrderTraversal(BinaryTreeNode node){
        if(node == null)
            return;
        System.out.print(node.data() + " ");
        preOrderTraversal(node.getLeftChild());
        preOrderTraversal(node.getRightChild());
    }

	public void preOrderIterative(){
		    if(root == null)
		        return;
		
		    Stack<BinaryTreeNode> stack = new Stack<>();
		    stack.push(root);
		
		    while(!stack.isEmpty()){
		        BinaryTreeNode current = stack.pop();
		
		        System.out.print(current.data() + " ");
		
		        // Push right first so left is processed first
		        if(current.getRightChild() != null)
		            stack.push(current.getRightChild());
		
		        if(current.getLeftChild() != null)
		            stack.push(current.getLeftChild());
		    }
	}

    //write a method implement the inorder traversal
    public static void inOrderTraversal(BinaryTreeNode node){
        if(node == null)
            return;
        inOrderTraversal(node.getLeftChild());
        System.out.print(node.data() + " ");
        inOrderTraversal(node.getRightChild());
    }

	
	//Write a method to compute the height of a node
	public static int height(BinaryTreeNode node){
	    if(node == null)
	        return -1;   // height in terms of edges
	
	    int leftHeight = height(node.getLeftChild());
	    int rightHeight = height(node.getRightChild());
	
	    return 1 + Math.max(leftHeight, rightHeight);
	}
	

}
