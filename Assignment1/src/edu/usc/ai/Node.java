package edu.usc.ai;

import java.util.Comparator;

public class Node implements Comparator<Node> {
	
	int nodenum ;					//node number
	String state = null;		//node name
	int g = 0;				//path cost
	int depth = 0;				
	Node parent = null;
	
	public Node(){
		
	}
	
	public Node(int nodenum){
		this.nodenum=nodenum;
		
		
	}
	public Node(String state){
		
		this.state=state;
		
	}
	public Node(int nodenum, String state){
		this.nodenum=nodenum;
		this.state=state;
		
	}
	public Node(int nodenum, String state, int g){
		this.nodenum=nodenum;
		this.state=state;
		this.g=g;
		
	}
	
	@Override
	public boolean equals(Object o){
		
		if(o!=null && (o instanceof Node) && ((Node)o).getState() == this.getState())
			return true;
		if(o!=null && (o instanceof Node) && ((Node)o).getNodenum() == this.getNodenum())
			return true;
		
		return false;		
		
	}
	
	@Override
	public int hashCode(){
		
		return this.getNodenum();
	}
	
	
	public int getNodenum() {
		return nodenum;
	}
	public void setNodenum(int node) {
		this.nodenum = node;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}

	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		return ((Node)o1).getG() - ((Node)o2).getG();
	
	}
	

}
