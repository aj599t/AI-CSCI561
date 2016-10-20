package edu.usc.ai;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class SearchMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Graph g = new Graph();
		int n=165;
		for(int i=n;i<=n;i++){
			g.createInput("D:\\Eclipse Workspace\\Assignment1\\input\\input"+i+".txt",i);
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
			
			File file1 = new File("D:\\Eclipse Workspace\\Assignment1\\Output\\output"+i+".txt");
	        File file2 = new File("D:\\Eclipse Workspace\\Assignment1\\codeOutput\\output"+i+".txt");
	        

	        try {
				boolean result = FileUtils.contentEqualsIgnoreEOL(file1, file2, null);
				if(! result)
					System.out.println("Test case "+i+ " failed "+result);
				else
					System.out.println("test case "+i+" passed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		}
	}

}
