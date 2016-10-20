
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class homework {
	
	public static void main(String[] args) {
		Game game=new Game();
		game.readInput();
		game.display();
		GameAlgorithm g = new GameAlgorithm();
		if(game.getMode().equalsIgnoreCase("MINIMAX"))
			g.minimax(game);
		else
			g.alphaBetaSearch(game);

	}
}


class Node {
	
	//private int nodeNum = 0;
	//private int value = 0;
	private int depth = 0;
	//private int utility = 0;
	private String moveType = "";
	private String move ="";
	private String player = "";
	private String notaPlayer = "";
	private String boardState[][] = null;
	
	/*public int getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}*/
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	/*
	public int getUtility() {
		return utility;
	}
	public void setUtility(int utility) {
		this.utility = utility;
	}*/
	public String[][] getBoardState() {
		return boardState;
	}
	public void setBoardState(String[][] boardState) {
		this.boardState = boardState;
	}
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getMoveType() {
		return moveType;
	}
	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}
	/*public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}*/
	public String getNotaPlayer() {
		return notaPlayer;
	}
	public void setNotaPlayer(String notaPlayer) {
		this.notaPlayer = notaPlayer;
	}
	

}

class Game {
	private int n=0;
	private String mode="";
	private String me="";
	private String player="";
	private String notPlayer="";
	private int depth=0;
	private int cellValues[][]=null;
	private String initialBoardState[][]=null;
	
	void readInput(){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("input.txt"));
			String curr="";
			int c=1, k=0, j=0;
			String temp[]=null;
			//System.out.println("Input: ");
			
			while ((curr = br.readLine()) != null) {
				curr = curr.trim();
				if(c==1){
					setN(Integer.parseInt(curr));
					cellValues=new int[n][n];
					initialBoardState=new String[n][n];
					temp=new String[n];
				}
				
				if(c==2)
					setMode(curr);				
				if(c==3){
					setPlayer(curr);
					setMe(curr);
					if(curr.equalsIgnoreCase("X"))
						setNotPlayer("o");
					else setNotPlayer("x");
				}
				if(c==4)
					setDepth(Integer.parseInt(curr));
				if(c>=5 && c<(5+getN())){
					temp = curr.split(" ");
					for(int i=0;i<getN();i++){
						cellValues[k][i] = Integer.parseInt(temp[i]);				
					}
					k++;
				}
				
				if(c>=5+getN() && c<(5+2*getN())){
					temp = curr.split("");
					for(int i=0;i<getN();i++){
						initialBoardState[j][i] = (temp[i]);
						
					}
					j++;
					
				}
				c++;
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Issue while creating input: ");	
			e.printStackTrace();
		} 
		
	}
	
	void display(){
		for(int i=0;i<getN();i++){
			for(int j=0;j<getN();j++)
				System.out.print(cellValues[i][j]+" ");		
			System.out.println();
		}
		for(int i=0;i<getN();i++){
			for(int j=0;j<getN();j++)				
				System.out.print(initialBoardState[i][j]+" ");			
			System.out.println();
		}
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int[][] getCellValues() {
		return cellValues;
	}

	public void setCellValues(int[][] cellValues) {
		this.cellValues = cellValues;
	}

	public String[][] getInitialBoardState() {
		return initialBoardState;
	}

	public void setInitialBoardState(String boardState[][]) {
		this.initialBoardState = boardState;
	}

	public String getNotPlayer() {
		return notPlayer;
	}

	public void setNotPlayer(String notPlayer) {
		this.notPlayer = notPlayer;
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}

}

class GameAlgorithm {
	
	int nodeNum = 0;
	Node myMove = new Node();
	
	public void minimax(Game g){
		long lStartTime = System.nanoTime();
		Node initialState = new Node();
		//initialState.setNodeNum(nodeNum++);
		initialState.setBoardState(g.getInitialBoardState());
		initialState.setPlayer(g.getPlayer());
		initialState.setNotaPlayer(g.getNotPlayer());
		int value = maxValue(initialState, g);
		System.out.println("Value: "+value);
		display(g);
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Elapsed milliseconds: " + difference/1000000);
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
			
			System.out.println("Result: ");
			
			System.out.println(myMove.getMove()+" "+myMove.getMoveType());
			bw.write(myMove.getMove()+" "+myMove.getMoveType());
			bw.newLine();
			String move[][] = myMove.getBoardState();
			for(int i=0;i<g.getN();i++){
				for(int j=0;j<g.getN();j++){
					System.out.print(move[i][j]);
					bw.write(move[i][j]);
				}	
				System.out.println();	
				bw.newLine();
			}
			
			bw.close();
			

		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		//Node mylocalMove = new Node();
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
				 //copyNode(mylocalMove, child);
				 if(state.getDepth()<=0)
					 copyNode(myMove, child);				
			 }
			 //v = Math.max(v, minValue(child, g));
		 }
		 
		 return v;	
	}
	
	public int minValue(Node state, Game g){
		//Node mylocalMove = new Node();
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
				 //copyNode(mylocalMove, child);
			 }
			 //v = Math.min(v, maxValue(child, g));
		 }
		 
		 return v;	
		
	}
	
	public boolean cutOffTest(Node state, Game g){
		if(state.getDepth() == g.getDepth() || isEmpty(state.getBoardState()))
			return true;
		return false;
	}
	
	public boolean isEmpty(String [][]boardState){
		
		for(int i=0;i<boardState.length; i++)
			for(int j=0;j<boardState[i].length; j++){
				if(boardState[i][j].equalsIgnoreCase("."))
					return false;
			}
		return true;
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
		//ArrayList<Node> stakeChildren = new ArrayList<Node>();
		
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
						children.add(n);
					}
					
					//else its a stake anyways
					
				}
			}
		}
		
		//children.addAll(stakeChildren);
		children.addAll(raidChildren);
		
		return children;
		
	}
	
	void alphaBetaSearch(Game g){
		long lStartTime = System.nanoTime();		
		Node initialState = new Node();
		//initialState.setNodeNum(nodeNum++);
		initialState.setBoardState(g.getInitialBoardState());
		initialState.setPlayer(g.getPlayer());
		initialState.setNotaPlayer(g.getNotPlayer());
		int value = maxAlphaBetaValue(initialState, g, -9999, 9999);
		System.out.println("Value: "+value);
		display(g);
		
		long lEndTime = System.nanoTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Elapsed milliseconds: " + difference/1000000);
		
		
	}
	
	public int maxAlphaBetaValue(Node state, Game g, int a, int b){
		//Node mylocalMove = new Node();
		
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
				 //copyNode(mylocalMove, child);
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
		
		//Node mylocalMove = new Node();
		
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
				 //copyNode(mylocalMove, child);
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
