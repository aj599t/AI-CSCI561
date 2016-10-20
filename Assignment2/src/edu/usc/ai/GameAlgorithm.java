package edu.usc.ai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GameAlgorithm {
	
	int nodeNum = 0;
	Node myMove = new Node();
	
	public void minimax(Game g){
		
		Node initialState = new Node();
		//initialState.setNodeNum(nodeNum++);
		initialState.setBoardState(g.getInitialBoardState());
		initialState.setPlayer(g.getPlayer());
		initialState.setNotaPlayer(g.getNotPlayer());
		int value = maxValue(initialState, g);
		//System.out.println("Value: "+value);
		display(g);
	}
	
	void display(Game g){		
		
		//write to output.txt
		File file = new File("output.txt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//System.out.println("Result: ");
			
			//System.out.println(myMove.getMove()+" "+myMove.getMoveType());
			bw.write(myMove.getMove()+" "+myMove.getMoveType());
			bw.newLine();
			String move[][] = myMove.getBoardState();
			for(int i=0;i<g.getN();i++){
				for(int j=0;j<g.getN();j++){
					//System.out.print(move[i][j]);
					bw.write(move[i][j]);
				}	
				//System.out.println();	
				bw.newLine();
			}
			
			bw.close();
			

		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void displayState(Game g, String a[][]){		
		
		//write to output.txt
		
			System.out.println("Result: ");
			
			for(int i=0;i<g.getN();i++){
				for(int j=0;j<g.getN();j++){
					System.out.print(a[i][j]);
					
				}	
				System.out.println();	
			}
	
	}

	void copyNode(Node n1, Node n2){
		
		n1.setBoardState(n2.getBoardState());
		n1.setDepth(n2.getDepth());
		n1.setMove(n2.getMove());
		n1.setMoveType(n2.getMoveType());
		//n1.setNodeNum(n2.getNodeNum());
		n1.setNotaPlayer(n2.getNotaPlayer());
		n1.setPlayer(n2.getPlayer());
		//n1.setUtility(n2.getUtility());
		//n1.setValue(n2.getValue());
	}
	
	public int maxValue(Node state, Game g){
		
		Node mylocalMove = new Node();
		int v = -9999 ;
		
		ArrayList<Node> children = new ArrayList<Node>();
		Node child = null;
		if(cutOffTest(state, g))			
			return eval(state, g);		
		
		children = generateChildren(state, g);
		Iterator<Node> iterator = children.iterator();
		
		 while(iterator.hasNext()){
			 child= iterator.next();
			 int minNodeValue = minValue(child, g);
			 
			 if(state.getDepth()<=0){				 
				 if(minNodeValue == v){
					 if(myMove.getMoveType().equalsIgnoreCase("Raid") && child.getMoveType().equalsIgnoreCase("Stake")){
						 copyNode(myMove, child);
					 }
				 }
			 }
			 if(minNodeValue > v){
				 v = minNodeValue;
				 copyNode(mylocalMove, child);
				 if(state.getDepth()<=0)
					 copyNode(myMove, child);				
			 }
			 //v = Math.max(v, minValue(child, g));
		 }
		 
		 return v;	
	}
	
	public int minValue(Node state, Game g){
		Node mylocalMove = new Node();
		int v = 9999 ;
		ArrayList<Node> children = new ArrayList<Node>();
		Node child = null;
		
		if(cutOffTest(state, g))
			return eval(state, g);
				
		children = generateChildren(state, g);
		Iterator<Node> iterator=children.iterator();
		 while(iterator.hasNext()){
			 child= iterator.next();
			 int maxNodeValue = maxValue(child, g);
			 if(state.getDepth()<=0){
				 if(maxNodeValue == v){
					 if(myMove.getMoveType().equalsIgnoreCase("Raid") && child.getMoveType().equalsIgnoreCase("Stake")){
						 copyNode(myMove, child);
					 }
				 }
			 }
			 if(maxNodeValue < v){
				 v = maxNodeValue;
				 copyNode(mylocalMove, child);
			 }
			 //v = Math.min(v, maxValue(child, g));
		 }
		 
		 return v;	
		
	}
	
	public boolean cutOffTest(Node state, Game g){
		if(state.getDepth() == g.getDepth())
			return true;
		return false;
	}
	
	public int eval(Node state, Game g){
		
		String currentBoard[][] = state.getBoardState();
		int xValue = 0, oValue=0;
		for(int i=0;i<currentBoard.length; i++)
			for(int j=0;j<currentBoard[i].length; j++){
				if(currentBoard[i][j].equalsIgnoreCase("X"))
					xValue+= g.getCellValues()[i][j];
				if(currentBoard[i][j].equalsIgnoreCase("O"))
					oValue+= g.getCellValues()[i][j];
					
			}
		
		//state.setValue(xValue - oValue);
		if(g.getMe().equalsIgnoreCase("X"))			
			return xValue - oValue;
		else
			return oValue - xValue;
			
	}
	
	public void copyArray(String a[][], String b[][], Game g){
		
		for(int i=0;i<g.getN();i++)
			for(int j=0; j<g.getN();j++){
				a[i][j] = b[i][j];
			}
		
	}
	
	public ArrayList<Node> generateChildren(Node state, Game g){
		
		ArrayList<Node> children = new ArrayList<Node>();
		ArrayList<Node> raidChildren = new ArrayList<Node>();
		ArrayList<Node> stakeChildren = new ArrayList<Node>();
		
		for(int i=0;i<g.getN();i++){
			for(int j=0; j<g.getN();j++){
				String currBoardState[][] = new String [g.getN()][g.getN()];
				copyArray(currBoardState, state.getBoardState(), g);				
				if(currBoardState[i][j].equalsIgnoreCase(".")){
					Node n = new Node();
					currBoardState[i][j] = state.getPlayer();
					//Check if its a raid i.e if there is a player adjacent to the node
					boolean foundRaid = false;
					if(i-1 > -1 && i-1 < g.getN())
						if(currBoardState[i-1][j].equalsIgnoreCase(state.getPlayer()))
							foundRaid = true;

					if(i+1 > -1 && i+1 < g.getN())
						if(currBoardState[i+1][j].equalsIgnoreCase(state.getPlayer()))
							foundRaid = true;

					if(j-1 > -1 && j-1 < g.getN())
						if(currBoardState[i][j-1].equalsIgnoreCase(state.getPlayer()))
							foundRaid = true;
					
					if(j+1 > -1 && j+1 < g.getN())
						if(currBoardState[i][j+1].equalsIgnoreCase(state.getPlayer()))
							foundRaid = true;
					
					boolean updateRaid = false;
					if(foundRaid){
						
						if(i-1 > -1 && i-1 < g.getN())
							if(currBoardState[i-1][j].equalsIgnoreCase(state.getNotaPlayer())){
								currBoardState[i-1][j] = state.getPlayer();
								updateRaid = true;
							}
							
						if(i+1 > -1 && i+1 < g.getN())
							if(currBoardState[i+1][j].equalsIgnoreCase(state.getNotaPlayer())){
								currBoardState[i+1][j] = state.getPlayer();			
								updateRaid = true;
							}
						
						if(j-1 > -1 && j-1 < g.getN())
							if(currBoardState[i][j-1].equalsIgnoreCase(state.getNotaPlayer())){
								currBoardState[i][j-1] = state.getPlayer();
								updateRaid = true;
							}
													
						if(j+1 > -1 && j+1 < g.getN())
							if(currBoardState[i][j+1].equalsIgnoreCase(state.getNotaPlayer())){
								currBoardState[i][j+1] = state.getPlayer();
								updateRaid = true;
							}
					}
					
					n.setDepth(state.getDepth()+1);
					//currBoardState[i][j] = g.getPlayer();
					n.setBoardState(currBoardState);					
					n.setMove((char)(j+65)+""+(i+1)+"");
					//n.setMove((char)(j+65)+(char)(i+65)+"");
					//n.setNodeNum(nodeNum++);
					n.setPlayer(state.getNotaPlayer());
					n.setNotaPlayer(state.getPlayer());
					
					if(updateRaid){
						n.setMoveType("Raid");
						raidChildren.add(n);
					}
					else{
						n.setMoveType("Stake");
						stakeChildren.add(n);
					}
					
					//else its a stake anyways
					
				}
			}
		}
		
		children.addAll(stakeChildren);
		children.addAll(raidChildren);
		
		return children;
		
	}
	
	void alphaBetaSearch(Game g){
		
		Node initialState = new Node();
		//initialState.setNodeNum(nodeNum++);
		initialState.setBoardState(g.getInitialBoardState());
		initialState.setPlayer(g.getPlayer());
		initialState.setNotaPlayer(g.getNotPlayer());
		int value = maxAlphaBetaValue(initialState, g, -9999, 9999);
		//System.out.println("Value: "+value);
		display(g);
		
	}
	
	public int maxAlphaBetaValue(Node state, Game g, int a, int b){
		Node mylocalMove = new Node();
		
		ArrayList<Node> children = new ArrayList<Node>();
		Node child = null;
		if(cutOffTest(state, g))
			return eval(state, g);
		
		int v = -9999 ;
		children = generateChildren(state, g);
		Iterator<Node> iterator=children.iterator();
		while(iterator.hasNext()){
			child= iterator.next();
			int minNodeValue = minAlphaBetaValue(child, g, a, b);
			if(state.getDepth()<=0){
				if(minNodeValue == v){
					 if(myMove.getMoveType().equalsIgnoreCase("Raid") && child.getMoveType().equalsIgnoreCase("Stake")){
						 copyNode(myMove, child);
					 }
				}
			 }
			 if(minNodeValue > v){
				 v = minNodeValue;
				 copyNode(mylocalMove, child);
				 if(state.getDepth()<=0)
					 copyNode(myMove, child);
			 }
			 //v = Math.max(v, minValue(child, g));	
			 if(v>=b)
				 return v;
			 			 
			 a = Math.max(a,v);
		 }	 
		 
		 return v;	
	}
	
	public int minAlphaBetaValue(Node state, Game g, int a, int b){
		
		Node mylocalMove = new Node();
		
		ArrayList<Node> children = new ArrayList<Node>();
		Node child = null;
		if(cutOffTest(state, g))
			return eval(state, g);
		
		int v = 9999 ;
		children = generateChildren(state, g);
		Iterator<Node> iterator=children.iterator();
		 while(iterator.hasNext()){
			 child= iterator.next();
			 int maxNodeValue = maxAlphaBetaValue(child, g, a, b);
			 if(state.getDepth()<=0){
				 if(maxNodeValue == v){
					 if(myMove.getMoveType().equalsIgnoreCase("Raid") && child.getMoveType().equalsIgnoreCase("Stake")){
						 copyNode(myMove, child);
					 }
				 }
				 
			 }
			 if(maxNodeValue < v){
				 v = maxNodeValue;				 
				 copyNode(mylocalMove, child);
				 //myMove = child;	 
			 } 
			 //v = Math.min(v, maxValue(child, g));
			 if(v <= a)
				 return v;
			 
			 b = Math.min(b, v);
		 }
		 
		 return v;		
	}
}
