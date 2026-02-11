package javalucproject;

import java.util.ArrayList;

class TreeNode{
    int data;
    ArrayList<TreeNode> children = new ArrayList();
    TreeNode parent = null;
    
    public TreeNode(int d){
        data = d;
    }
    
    public TreeNode addChild(int d){
        TreeNode n = new TreeNode(d);
        n.setParent(this);
        children.add(n);
        return n;
    }
    
    public ArrayList<TreeNode> getChildren(){
        return children;
    }
    
    public void setParent(TreeNode p){
        parent = p;
    }
    
    public TreeNode getParent(){
        return parent;
    }
}


public class TreeExercise {
    public static void main(String[] args){
        //create root node
		TreeNode root = new TreeNode(1);
        //add children to root node
		TreeNode child1 = root.addChild(2);
		TreeNode child2 = root.addChild(3);
        //add children to child nodes
		TreeNode child11 = child1.addChild(4);
		TreeNode child12 = child1.addChild(5);
		
		TreeNode child21 = child2.addChild(6);
		TreeNode child22 = child2.addChild(7);
		
        //call methods
		preOrderTraversal(root);
    }
    
    //write a method to implement the preorder traversal 
    //pseudocode from the slides
	public static void preOrderTraversal(TreeNode node){
		if(node == null)
			return;
		System.out.print(node.data + " ");
		ArrayList<TreeNode> children = node.getChildren();
		for(TreeNode tn : children){
			preOrderTraversal(tn);
		}
    }
    //write a method implement the postorder traversal 
    //pseudocde from the slides
    
    //write a method that given a node, finds the depth of that node
    
    //write a method that given the root node, finds the size of tree
    //aka how many nodes the tree has
}
