package edu.usc.ai;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class GameMain {

	public static void main(String[] args) {
		Game game=new Game();
		for(int i=0;i<100;i++){
			game.readInput("D:\\Eclipse Workspace\\Assignment2\\input\\"+i+".in", i);
			//game.display();
			GameAlgorithm g = new GameAlgorithm();
			if(game.getMode().equalsIgnoreCase("MINIMAX"))
				g.minimax(game);
			else
				g.alphaBetaSearch(game);
			
			File file1 = new File("D:\\Eclipse Workspace\\Assignment2\\output\\"+i+".out");
	        File file2 = new File("D:\\Eclipse Workspace\\Assignment2\\testcases\\"+i+".out");
	        
	
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
