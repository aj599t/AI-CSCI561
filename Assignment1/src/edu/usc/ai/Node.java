package edu.usc.ai;

import java.util.Comparator;


public class Node implements Comparator<Node> {
	
	private int nodenum ;					//node number
	private String state = null;		//node name
	private int g = 0;				//path cost
	private int depth = 0;				
	private Node parent = null;
	private int h = 0;
	private long timestamp= 0;
	
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
	public Node(int nodenum, int h, String state){
		this.nodenum=nodenum;
		this.state=state;
		this.h=h;
		
	}
	public Node(int nodenum, String state, int g){
		this.nodenum=nodenum;
		this.state=state;
		this.g=g;
		
	}
	
	public Node(int nodenum, String state, int g, long timestamp){
		this.nodenum=nodenum;
		this.state=state;
		this.g=g;
		this.timestamp=timestamp;
		
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
		if((((Node)o1).getG() - ((Node)o2).getG()) == 0)
			return (int) (((Node)o1).getTimestamp() - ((Node)o2).getTimestamp());
		return ((Node)o1).getG() - ((Node)o2).getG();
	
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	

}
