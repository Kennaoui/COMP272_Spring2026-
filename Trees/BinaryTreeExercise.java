package javalucproject;

import java.util.ArrayList;

class BinaryTreeNode{
    private int data;
	private BinaryTreeNode leftChild = null;
	private inaryTreeNode rightChild = null;
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
	private BinaryTreeNode addPositionalChild(int d, boolean isleft){
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
			n = addPositionalChild(d, true)
		}
		
		return n;
	}

	//Add a right child if empty, otherwise returns null
	public BinaryTreeNode addRightChild(int d){
		BinaryTreeNode n = null;
		
		if(rightChild == null){
			n = addPositionalChild(d, false)
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
    }
    
    //write a method to implement the preorder traversal 
    //pseudocode from the slides
	public static void preOrderTraversal(BinaryTreeNode node){
		if(node == null)
			return;
		System.out.print(node.data + " ");	
		preOrderTraversal(node.getLeftChild());
		preOrderTraversal(node.getRightChild());
    }
	//write a method implement the inorder traversal 
	public static void inOrderTraversal(BinaryTreeNode node){
		if(node == null)
			return;
		
		inOrderTraversal(node.getLeftChild());
		System.out.print(node.data + " ");
		inOrderTraversal(node.getRightChild());
    }
	//write a method calculate the Hight of a given node

}
