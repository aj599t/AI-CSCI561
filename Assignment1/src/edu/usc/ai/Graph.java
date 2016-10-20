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
	private ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
	private Map nodeMap = new LinkedHashMap();
	private int numOfSundayTrafficLines = 0;
	private int fileNum=0;

	
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

	void createInput(String fileName, int fileNum){
		//System.out.println();
		setFileNum(fileNum);
		String curr=null, source=null,destination=null;
		int c=1;
		String edges[] =null;
		int i=0 ,k1=0;
		String temp[]=null;
		String temp1[]=null;
		Set<String> n = new LinkedHashSet<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			//System.out.println("Input: ");
			while ((curr = br.readLine()) != null) {
				if(c==1){
					setAlgo(curr);
					System.out.println("Algo: "+algo);
				}
				if(c==2){
					source=curr;
					//n.add(curr);	
					//System.out.println("Start State: "+source);
				
				}
				if(c==3){
					destination=curr;
					//n.add(curr);	
					//System.out.println("Goal State: "+destination);
				
				}
				if(c==4){
					setNumOfTrafficLines(Integer.parseInt(curr));	
					//System.out.println("NumOfTrafficLines: "+getNumOfTrafficLines());
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
					//System.out.println(getNumOfSundayTrafficLines());
					temp1=new String[getNumOfSundayTrafficLines()];
				}
				
				if(c>end && c<(1+end+getNumOfSundayTrafficLines())){
					
					//System.out.println("SundayTraffic: "+curr);
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
			//System.out.println("printing Map ");
			for(int j=0;j<nodeStringArray.length;j++){
				
				nodeMap.put((String)nodeStringArray[j], j);
				//System.out.println(nodeMap.get((String)nodeStringArray[j]));
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
			
			//System.out.println(" Edges: ");
			String inputTemp[][]=new String[getNumOfTrafficLines()][3];
			graph=new int[n.size()][n.size()];
			for(int k=0;k<getNumOfTrafficLines();k++){
				inputTemp[k] = edges[k].split(" ");
				//System.out.println(inputTemp[k][0]+ "  "+ inputTemp[k][1]+" "+ inputTemp[k][2]);
				//System.out.println(inputTemp[k][2].length());
				graph[(int) nodeMap.get(inputTemp[k][0])][(int) nodeMap.get(inputTemp[k][1])]=Integer.parseInt(inputTemp[k][2].trim());
				//new code of adjacency list input
				adjList.get((int) nodeMap.get(inputTemp[k][0])).add((int) nodeMap.get(inputTemp[k][1]));				
				//new code end
			}
			//new code 
			int arr=0;
			//System.out.println("Printing adjList: ");
			/*for(ArrayList<Integer> list: adjList){
				for(Integer l2: list ){
					System.out.println(nodeStringArray[arr]+" ---> "+nodeStringArray[l2]+" ");
				}
				System.out.println();
				arr++;
			}*/
			//new code end
			
			Node start=nodes.get((int) nodeMap.get(source));
			//start.setParent(start);
			setStartState(start);
			setGoalState(nodes.get((int) nodeMap.get(destination)));
			
			/*Iterator it=nodes.iterator();
			while(it.hasNext()){
				Node k11=(Node) it.next();
				System.out.println(k11.getState() +" "+ k11.getH());
			}*/
			
			setNumOfNodes(n.size());
			/*Iterator iit=n.iterator();
			while(iit.hasNext())
				System.out.println("set element: "+(String)iit.next());
			System.out.println(" number of nodes: "+ getNumOfNodes());*/
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

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	
}
