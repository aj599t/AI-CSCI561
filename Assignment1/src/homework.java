

import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class homework {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Graph g = new Graph();
		g.createInput();
		//g.displayGraph();
		String algo=g.getAlgo();
		SearchAlgorithm s=new SearchAlgorithm();
		switch(algo.toUpperCase()){
		
		case "BFS": 
			s.BFS(g);
			break;
		case "DFS":
			s.BFS(g);
			break;
		case "UCS":
			s.UCS(g);
			break;
		case "A*":
			s.UCS(g);
			break;
		default:
			System.out.println("Check input");
		}
	}

}

class SearchAlgorithm {
	
	Queue<Node> frontier = new LinkedList<Node>();
	Queue<Node> exploredSet = new LinkedList<Node>();
	Queue<Node> open = null;
	int counter=0;
		
	void UCS(Graph g){
		
		
	    if(g.getAlgo().equals("UCS"))
	    	open = new PriorityQueue<Node>(new Node());
	    if(g.getAlgo().equals("A*")){
	    	Comparator<Node> comp = new Comparator<Node>() {
		        public int compare(Node n1, Node n2) {
		        	if(((n1.getG()+n1.getH())-(n2.getG()+n2.getH())) == 0 && n1.getParent().equals(n2.getParent()))
		        		return (int) (n1.getTimestamp()-n2.getTimestamp());
		            return ((n1.getG()+n1.getH())-(n2.getG()+n2.getH()));
		        }
		    };
	    	open = new PriorityQueue<Node>(comp);
	    }	
				
		Node child=null, parent=g.getStartState(), node=null;		 
		
		ArrayList<Node> children=null;
		g.getStartState().setTimestamp(counter++);
		open.offer(g.getStartState());
		
		while(true){
			if(open.isEmpty())
				 return;
			parent = open.poll();
			if(parent.getState().equals(g.getGoalState().getState())){
				 solution(g);
				 return;
			}
			children=expand(parent, g);
			
			Iterator<Node> iterator=children.iterator();
			 while(iterator.hasNext()){
				 child= iterator.next();
				 if(! exploredSet.contains(child) && ! open.contains(child))
					 insert(parent, child, g);
				 else{
					 if(g.getAlgo().equals("UCS")){
						 if(open.contains(child)){
							 node = getSimilarNode(child, open);
							 if(child.getG() < node.getG()){
								 open.remove(node);			//deleteNode(node, frontier);
							 	 insert(parent, child, g);
							 }						 						 
						 }else{
							 if(exploredSet.contains(child)){
								 node = getSimilarNode(child, exploredSet);
								 if(child.getG() < node.getG()){
									 exploredSet.remove(node);	//deleteNode(node, exploredSet);
								 	 insert(parent, child, g);
								 }								 
							 }
						 }
					 }
					 
					 if(g.getAlgo().equals("A*")){
						 if(open.contains(child)){
							 node = getSimilarNode(child, open);
							 if((child.getG()+child.getH()) < (node.getG()+node.getH())){
								 open.remove(node);			//deleteNode(node, frontier);
							 	 insert(parent, child, g);
							 }						 						 
						 }else{
							 if(exploredSet.contains(child)){
								 node = getSimilarNode(child, exploredSet);
								 if((child.getG()+child.getH()) < (node.getG()+node.getH())){
									 exploredSet.remove(node);	//deleteNode(node, exploredSet);
								 	 insert(parent, child, g);
								 }								 
							 }
						 }
						 
					 }
					 
					 
					 
					 
				 }
			 }
			 exploredSet.add(parent);
		}
	}
	
	Node getSimilarNode(Node child, Queue<Node> q){
		
		Iterator<Node> i= q.iterator();
		Node n = null;
		while(i.hasNext()){
			n=(Node)i.next();
			if(n.getState().equals(child.getState()) && n.getNodenum() == (child.getNodenum()))
				return n;			
		}
		return null;
		
	}
	
	void BFS(Graph g){
				 
		 Node child=null, parent=g.getStartState(), node=null;		 
		 ArrayList<Node> children;
		 
		 if(g.getAlgo().equals("BFS"))
			 frontier.offer(g.getStartState());
		 if(g.getAlgo().equals("DFS"))
			 ((LinkedList<Node>) frontier).push(g.getStartState());	
		 //exploredSet.add(g.getStartState());
		 
		 while(true){
			 
			 if(frontier.isEmpty())
				 return;
			 if(g.getAlgo().equals("BFS"))
				 parent = frontier.poll();
			 if(g.getAlgo().equals("DFS"))
				 parent = ((LinkedList<Node>) frontier).pop();
			 
			 if(parent.getState().equals(g.getGoalState().getState())){
				 solution(g);
				 return;
			 }
			 
			 exploredSet.add(parent);
			 
			 children=expand(parent, g);
			 Iterator<Node> iterator=children.iterator();
			 while(iterator.hasNext()){
				 child= iterator.next();
				 if(! exploredSet.contains(child) && ! frontier.contains(child))
					 insert(parent, child, g);
				 else{					 
						 if(frontier.contains(child)){
							 node = getSimilarNode(child, frontier);
							 if(child.getDepth() < node.getDepth()){
								 frontier.remove(node);			//deleteNode(node, frontier);
							 	 insert(parent, child, g);
							 }						 						 
						 }else{
							 if(exploredSet.contains(child)){
								 node = getSimilarNode(child, exploredSet);
								 if(child.getDepth() < node.getDepth()){
									 exploredSet.remove(node);	//deleteNode(node, exploredSet);
								 	 insert(parent, child, g);
								 }								 
							 }
						 
						 }
				 } 			 
						 
		 
			 }
			 
		 }
		
	}
	
	void solution(Graph g){
		
		ArrayList<Node> displayList=new ArrayList<Node>();
		Node node=g.getNodes().get(g.getGoalState().getNodenum());
		System.out.println("BFS/DFS Shortest Path: ");
		while(node!=null){
			//System.out.println(node.getState() + " "+ node.getDepth());
			displayList.add(node);
			node=node.getParent();
		}
		Collections.reverse(displayList);
		
		File file = new File("output.txt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
		
			Iterator<Node> i=displayList.iterator();
			while(i.hasNext()){
				node=(Node)i.next();
				if(g.getAlgo().equals("UCS") || g.getAlgo().equals("A*")){
					bw.write(node.getState()+" "+node.getG());
					bw.newLine();
					System.out.println(node.getState()+" "+node.getG());}
				else{
					bw.write(node.getState()+" "+node.getDepth());
					bw.newLine();
					System.out.println(node.getState()+" "+node.getDepth());
				}
					
			}	
			bw.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	
	
	ArrayList<Node> expand(Node parent, Graph g){
		
		ArrayList<Node> children = new ArrayList<Node>();
		Node child, child1 = null;
		int graph[][]=g.getGraph();
		System.out.println("Children of "+ parent.getState());
		//new code
		ArrayList<Integer> childList = g.getAdjList().get(parent.getNodenum());
		for (int i = 0; i < childList.size(); i++) {
			//if(graph[parent.getNodenum()][childList.get(i)]!=0){
				child=g.getNodes().get(childList.get(i));
				child1 = new Node(child.getNodenum(),child.getState());
				child1.setDepth(child.getDepth());
				child1.setG(parent.getG()+graph[parent.getNodenum()][child.getNodenum()]);
				child1.setH(child.getH());
				child1.setParent(child.getParent());
				child1.setTimestamp(counter++);
				//child.setParent(parent);
				//g.getNodes().set(i, child1);
				children.add(child1);
				
				System.out.println(child1.getState()+" "+child1.getG());
			//}
		}
		
		/*for(int i=0;i<g.getNumOfNodes();i++)
			if(graph[parent.getNodenum()][i]!=0){
				child=g.getNodes().get(i);
				child1 = new Node(child.getNodenum(),child.getState());
				child1.setG(parent.getG()+graph[parent.getNodenum()][child.getNodenum()]);
				child1.setH(child.getH());
				child1.setParent(child.getParent());
				//child.setParent(parent);
				//g.getNodes().set(i, child1);
				children.add(child1);
				
				System.out.println(child1.getState()+" "+child1.getG());
			}*/
		
		if(g.getAlgo().equals("DFS")){
			Collections.reverse(children);
		}
		//System.out.println();
		
		return children;
	}
	
	void insert(Node parent, Node child, Graph g){	
			
			//child.se
			child.setParent(parent);
			child.setDepth(parent.getDepth()+1);
			child.setTimestamp(counter++);
			if(g.getAlgo().equals("BFS")){
				/*child.setParent(parent);
				child.setDepth(parent.getDepth()+1);*/
				g.getNodes().set(child.getNodenum(), child);
				frontier.offer(child);
			}
			if(g.getAlgo().equals("DFS")){
				/*child.setParent(parent);
				child.setDepth(parent.getDepth()+1);*/
				g.getNodes().set(child.getNodenum(), child);
				((LinkedList<Node>) frontier).push(child);	//only this line is different
			}
			if(g.getAlgo().equals("UCS") || g.getAlgo().equals("A*")){
				Node newChild = new Node(child.getNodenum(), child.getState(), child.getG());	
				newChild.setParent(parent);
				newChild.setDepth(parent.getDepth()+1);
				newChild.setH(child.getH());
				newChild.setTimestamp(counter++);
				g.getNodes().set(newChild.getNodenum(), newChild);
				open.offer(newChild);
			}
					
			
			//displaying the queue
			System.out.println("displaying queue");
			Node node=null;
			Iterator<Node> i=null;
			if(g.getAlgo().equals("UCS") || g.getAlgo().equals("A*"))
				i=open.iterator();
			else
				i=frontier.iterator();
			while(i.hasNext()){
				node=(Node)i.next();
				System.out.println(node.getState()+" with parent : "+node.getParent().getState()+" with pathcost: "+node.getG());
			}		
			
	}

}


class Graph {
	
	private int graph [][] ;
	private String algo="";
	private Node startState=null;
	private Node goalState=null;
	private int numOfNodes=0;
	private int numOfTrafficLines = 0;
	private Object[] nodeStringArray=null;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
	private Map nodeMap = new LinkedHashMap();
	private int numOfSundayTrafficLines = 0;

	
	public int[][] getGraph() {
		return graph;
	}

	public void setGraph(int[][] graph) {
		this.graph = graph;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}

	public Node getStartState() {
		return startState;
	}

	public void setStartState(Node startState) {
		this.startState = startState;
	}

	public Node getGoalState() {
		return goalState;
	}

	public void setGoalState(Node goalState) {
		this.goalState = goalState;
	}

	public int getNumOfTrafficLines() {
		return numOfTrafficLines;
	}

	public void setNumOfTrafficLines(int numOfTrafficLines) {
		this.numOfTrafficLines = numOfTrafficLines;
	}

	void createInput(){
		System.out.println();
		
		String curr=null, source=null,destination=null;
		int c=1;
		String edges[] =null;
		int i=0 ,k1=0;
		String temp[]=null;
		String temp1[]=null;
		Set<String> n = new LinkedHashSet<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("input.txt"));
			System.out.println("Input: ");
			while ((curr = br.readLine()) != null) {
				if(c==1){
					setAlgo(curr);
					System.out.println("Algo: "+algo);
				}
				if(c==2){
					source=curr;
					//n.add(curr);	
					//System.out.println("Start State: "+getStartState().getState());
				
				}
				if(c==3){
					destination=curr;
					//n.add(curr);	
					//System.out.println("Goal State: "+getGoalState().getState());
				
				}
				if(c==4){
					setNumOfTrafficLines(Integer.parseInt(curr));	
					System.out.println("NumOfTrafficLines: "+getNumOfTrafficLines());
					edges= new String[getNumOfTrafficLines()];
				
				}
				int end=getNumOfTrafficLines()+5;
				if(c>=5 && c<end){
					if(curr!=null || !curr.equals("")){
						edges[i++]=curr;
						temp=curr.split(" ");
						n.add(temp[0]);
						n.add(temp[1]);
					}
				}
				
				if(c == end){
					setNumOfSundayTrafficLines(Integer.parseInt(curr));
					System.out.println(getNumOfSundayTrafficLines());
					temp1=new String[getNumOfSundayTrafficLines()];
				}
				
				if(c>end && c<(1+end+getNumOfSundayTrafficLines())){
					
					System.out.println("SundayTraffic: "+curr);
					if(curr!=null || !curr.equals("")){
						temp1[k1]=curr;
						k1++;
					}
					
				}
					
					
				//System.out.println(curr);
				c++;
			}
			if(getNumOfTrafficLines() == 0){
				n.add(source);
				n.add(destination);
			}
			nodeStringArray=n.toArray();
			System.out.println("printing Map ");
			for(int j=0;j<nodeStringArray.length;j++){
				
				nodeMap.put((String)nodeStringArray[j], j);
				System.out.println(nodeMap.get((String)nodeStringArray[j]));
				nodes.add(new Node(j,(String)nodeStringArray[j]));
				adjList.add(j, new ArrayList<Integer>());
			}
			String temp2[] = null;
			
			if(getNumOfSundayTrafficLines() != 0 || temp1!=null){
			
				for(int l=0;l<temp1.length;l++){
					temp2=temp1[l].split(" ");
					nodes.get((int) nodeMap.get(temp2[0])).setH(Integer.parseInt(temp2[1]));					
				}
			}
			
			System.out.println(" Edges: ");
			String inputTemp[][]=new String[getNumOfTrafficLines()][3];
			graph=new int[n.size()][n.size()];
			for(int k=0;k<getNumOfTrafficLines();k++){
				inputTemp[k] = edges[k].split(" ");
				System.out.println(inputTemp[k][0]+ "  "+ inputTemp[k][1]+" "+ inputTemp[k][2]);
				System.out.println(inputTemp[k][2].length());
				graph[(int) nodeMap.get(inputTemp[k][0])][(int) nodeMap.get(inputTemp[k][1])]=Integer.parseInt(inputTemp[k][2].trim());
				//new code of adjacency list input
				adjList.get((int) nodeMap.get(inputTemp[k][0])).add((int) nodeMap.get(inputTemp[k][1]));				
				//new code end
			}
			//new code 
			int arr=0;
			System.out.println("Printing adjList: ");
			for(ArrayList<Integer> list: adjList){
				for(Integer l2: list ){
					System.out.println(nodeStringArray[arr]+" ---> "+nodeStringArray[l2]+" ");
				}
				System.out.println();
				arr++;
			}
			//new code end
			
			Node start=nodes.get((int) nodeMap.get(source));
			//start.setParent(start);
			setStartState(start);
			setGoalState(nodes.get((int) nodeMap.get(destination)));
			
			Iterator it=nodes.iterator();
			while(it.hasNext()){
				Node k11=(Node) it.next();
				System.out.println(k11.getState() +" "+ k11.getH());
			}
			
			setNumOfNodes(n.size());
			Iterator iit=n.iterator();
			while(iit.hasNext())
				System.out.println("set element: "+(String)iit.next());
			System.out.println(" number of nodes: "+ getNumOfNodes());
			if (br != null)
				br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		
	}

	void displayGraph(){
		
		for(int i=0;i<graph.length;i++){
			for(int j=0;j<graph[i].length;j++){
				if(graph[i][j]!=0)
					System.out.println("From "+ nodeStringArray[i] +" to "+nodeStringArray[j]+" : "+ graph[i][j]);
				
			}
				
		}
	}

	public Object[] getNodeStringArray() {
		return nodeStringArray;
	}

	public void setNodeStringArray(Object[] nodeStringArray) {
		this.nodeStringArray = nodeStringArray;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public Map getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map nodeMap) {
		this.nodeMap = nodeMap;
	}

	public int getNumOfNodes() {
		return numOfNodes;
	}

	public void setNumOfNodes(int numOfNodes) {
		this.numOfNodes = numOfNodes;
	}

	public int getNumOfSundayTrafficLines() {
		return numOfSundayTrafficLines;
	}

	public void setNumOfSundayTrafficLines(int numOfSundayTrafficLines) {
		this.numOfSundayTrafficLines = numOfSundayTrafficLines;
	}

	public ArrayList<ArrayList<Integer>> getAdjList() {
		return adjList;
	}

	public void setAdjList(ArrayList<ArrayList<Integer>> adjList) {
		this.adjList = adjList;
	}

	
}


class Node implements Comparator<Node> {
	
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



