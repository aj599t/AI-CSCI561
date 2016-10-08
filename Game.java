package edu.usc.ai;

import java.io.BufferedReader;
import java.io.FileReader;


public class Game {
	private int n=0;
	private String mode="";
	private String me="";
	private String player="";
	private String notPlayer="";
	private int depth=0;
	private int cellValues[][]=null;
	private String initialBoardState[][]=null;
	private int outputNum = 0;
	

	void readInput(String file, int outputNum){
		setOutputNum(outputNum);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String curr="";
			int c=1, k=0, j=0;
			String temp[]=null;
			//System.out.println("Input: ");
			
			while ((curr = br.readLine()) != null) {
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

	public int getOutputNum() {
		return outputNum;
	}

	public void setOutputNum(int outputNum) {
		this.outputNum = outputNum;
	}

}
