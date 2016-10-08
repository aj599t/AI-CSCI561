package edu.usc.ai;

public class Node {
	
	int nodeNum = 0;
	int value = 0;
	int depth = 0;
	int utility = 0;
	private String moveType = "";
	String move ="";
	String player = "";
	String notaPlayer = "";
	String boardState[][] = null;
	
	
	
	public int getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getUtility() {
		return utility;
	}
	public void setUtility(int utility) {
		this.utility = utility;
	}
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getNotaPlayer() {
		return notaPlayer;
	}
	public void setNotaPlayer(String notaPlayer) {
		this.notaPlayer = notaPlayer;
	}
	

}
