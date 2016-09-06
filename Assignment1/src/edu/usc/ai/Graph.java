package edu.usc.ai;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class Graph {
	
	private int graph [][] ;
	private String algo="";
	private Node startState=null;
	private Node goalState=null;
	private int numOfNodes=0;
	private int numOfTrafficLines = 0;
	private Object[] nodeStringArray=null;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private Map nodeMap = new LinkedHashMap();
	
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
		int i=0;
		String temp[]=null;
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
				if(c>=5 && c<(getNumOfTrafficLines()+5)){
					
					edges[i++]=curr;
					temp=curr.split(" ");
					n.add(temp[0]);
					n.add(temp[1]);
				}
					
				//System.out.println(curr);
				c++;
			}
			
			nodeStringArray=n.toArray();
			System.out.println("printing Map ");
			for(int j=0;j<nodeStringArray.length;j++){
				
				nodeMap.put((String)nodeStringArray[j], j);
				System.out.println(nodeMap.get((String)nodeStringArray[j]));
				nodes.add(new Node(j,(String)nodeStringArray[j]));
			}
			
			System.out.println(" Edges: ");
			String inputTemp[][]=new String[getNumOfTrafficLines()][3];
			graph=new int[n.size()][n.size()];
			for(int k=0;k<getNumOfTrafficLines();k++){
				inputTemp[k] = edges[k].split(" ");
				System.out.println(inputTemp[k][0]+ "  "+ inputTemp[k][1]+" "+ inputTemp[k][2]);
				graph[(int) nodeMap.get(inputTemp[k][0])][(int) nodeMap.get(inputTemp[k][1])]=Integer.parseInt(inputTemp[k][2]);
			}
			
			setStartState(nodes.get((int) nodeMap.get(source)));
			setGoalState(nodes.get((int) nodeMap.get(destination)));
			System.out.println("start state: ");
			System.out.println(getStartState().getState());
			System.out.println("end state: ");
			System.out.println(getGoalState().getState());
			System.out.println("Printing all vertices: ");
			Iterator it=nodes.iterator();
			while(it.hasNext()){
				System.out.println(((Node)it.next()).getState());
			}
			
			setNumOfNodes(n.size());
			System.out.println(" number of nodes: "+ getNumOfNodes());
			
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
}
