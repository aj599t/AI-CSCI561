package edu.usc.ai;

public class SearchMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Graph g = new Graph();
		g.createInput();
		g.displayGraph();
		String algo=g.getAlgo();
		SearchAlgorithm s=new SearchAlgorithm();
		switch(algo){
		
		case "BFS": 
			s.BFS(g);
			break;
		case "DFS":
			s.BFS(g);
			break;
		case "UCS":
			s.UCS(g);
			break;
		default:
			System.out.println("Check input");
		}
	}

}
