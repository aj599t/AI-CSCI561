package edu.usc.ai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SearchAlgorithm {
	
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
		//System.out.println(g.getAlgo()+" Shortest Path: ");
		while(node!=null){
			//System.out.println(node.getState() + " "+ node.getDepth());
			displayList.add(node);
			node=node.getParent();
		}
		Collections.reverse(displayList);
		
		File file = new File("D:\\Eclipse Workspace\\Assignment1\\codeOutput\\output"+g.getFileNum()+".txt");
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
					//System.out.println(node.getState()+" "+node.getG());
					}
				else{
					bw.write(node.getState()+" "+node.getDepth());
					bw.newLine();
					//System.out.println(node.getState()+" "+node.getDepth());
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
		//System.out.println("Children of "+ parent.getState());
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
				
				//System.out.println(child1.getState()+" "+child1.getG());
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
			/*System.out.println("displaying queue");
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
			*/
	}

}
