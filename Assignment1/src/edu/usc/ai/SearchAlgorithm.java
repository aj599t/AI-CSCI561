package edu.usc.ai;

import java.util.*;

public class SearchAlgorithm {
	
	Queue<Node> frontier = new LinkedList<Node>();
	Queue<Node> exploredSet = new LinkedList<Node>();
	Queue<Node> open = new PriorityQueue<Node>(new Node());
	
	void UCS(Graph g){
		System.out.println();
				
		Node child=null, parent=g.getStartState(), node=null;		 
		
		ArrayList<Node> children=null;

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
			 }
			 exploredSet.add(parent);
		}		
	}
	
	Node getSimilarNode(Node child, Queue<Node> q){
		
		Iterator i= q.iterator();
		Node n = null;
		while(i.hasNext()){
			n=(Node)i.next();
			if(n.getState().equals(child.getState()) && n.getNodenum() == (child.getNodenum()))
				return n;			
		}
		return null;
		
	}
	
	void BFS(Graph g){
				 
		 Node child=null, parent=g.getStartState();		 
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
			 } 			 
						 
		 }
		
	}
	
	void solution(Graph g){
		
		ArrayList<Node> displayList=new ArrayList<Node>();
		Node node=g.getGoalState();
		System.out.println("BFS/DFS Shortest Path: ");
		while(node!=null){
			//System.out.println(node.getState() + " "+ node.getDepth());
			displayList.add(node);
			node=node.getParent();
		}
		Collections.reverse(displayList);
		Iterator i=displayList.iterator();
		while(i.hasNext()){
			node=(Node)i.next();
			if(g.getAlgo().equals("UCS"))
				System.out.println(node.getState()+" "+node.getG());
			else
				System.out.println(node.getState()+" "+node.getDepth());
		}		
	}
	
	ArrayList<Node> expand(Node parent, Graph g){
		
		ArrayList<Node> children = new ArrayList<Node>();
		Node child1 = null;
		int graph[][]=g.getGraph();
		System.out.println("Children of "+ parent.getState());
		for(int i=0;i<g.getNumOfNodes();i++)
			if(graph[parent.getNodenum()][i]!=0){
				child1=g.getNodes().get(i);
				child1.setG(parent.getG()+graph[parent.getNodenum()][child1.getNodenum()]);
				//child.setParent(parent);
				g.getNodes().set(i, child1);
				children.add(child1);
				
				System.out.println(child1.getState()+" "+child1.getG());
			}
		
		if(g.getAlgo().equals("DFS")){
			Collections.reverse(children);
		}
		//System.out.println();
		
		return children;
	}
	
	void insert(Node parent, Node child, Graph g){	
			Node newChild = new Node(child.getNodenum(), child.getState(), child.getG());	
			newChild.setParent(parent);
			newChild.setDepth(parent.getDepth()+1);
			
			child.setParent(parent);
			child.setDepth(parent.getDepth()+1);
			if(g.getAlgo().equals("BFS")){
				child.setParent(parent);
				child.setDepth(parent.getDepth()+1);
				frontier.offer(child);
			}
			if(g.getAlgo().equals("DFS")){
				child.setParent(parent);
				child.setDepth(parent.getDepth()+1);
				((LinkedList<Node>) frontier).push(child);	//only this line is different
			}
			if(g.getAlgo().equals("UCS"))
				open.offer(newChild);
					
			
			//displaying the queue
			System.out.println("displaying queue");
			Node node=null;
			Iterator i=null;
			if(g.getAlgo().equals("UCS"))
				i=open.iterator();
			else
				i=frontier.iterator();
			while(i.hasNext()){
				node=(Node)i.next();
				System.out.println(node.getState()+" with parent : "+node.getParent().getState()+" with pathcost: "+node.getG());
			}		
			
	}

}
