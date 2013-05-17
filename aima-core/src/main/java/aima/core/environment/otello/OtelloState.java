package aima.core.environment.otello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.util.datastructure.Pair;
import aima.core.util.datastructure.Triplet;
//import aima.core.util.datastructure.XYLocation;
import aima.core.util.datastructure.XYLocation;



//Definicion del Otello
 
public class OtelloState implements Cloneable{
	
	private static final short white = 1;
	private static final short black = 2;
	private static final short EMPTY = 0;

	private short leftmoves = 60;


	private short board [][] = new short[8][8]; // Tablero/ Board
	//board = 0 Vacio
	//board = 1 Blancas
	//board = 2 Negras

	private short playerToMove = black;

	private short utility = -1; // 1: win for White, 2: win for Black, 0: draw


	public OtelloState(){ // Constructor de la clase, inicaliza el tablero./Class Constructor. Initiate Board.
	
		for(int i = 0;i < 8;i++){
			for(int j = 0;j < 8;j++){
				board[i][j] = 0;
			}
		}
		board[3][3] = board[4][4] = white;
		board[3][4] = board[4][3] = black;
	}
	
	public short getPlayerToMove() {
		return playerToMove;
	}
	
	public boolean isEmpty(int col, int row) {
		return board[col][row] == EMPTY;
	}
	
	public short getValue(int col, int row) {
		return board[col][row];
	}
	
	public short getUtility() {
		return utility;
	}
	
	public void mark(XYLocation action) { // Marca y Convierte Fichas!!!
		mark(action.getXCoOrdinate(), action.getYCoOrdinate());
	}
	
	public void mark(int col, int row) {
		if (utility == -1 && getValue(col, row) == EMPTY) {
			board[col][row] = playerToMove;
			convertDisks(col,row);
			analyzeUtility();
			playerToMove = (playerToMove == white ? black : white); // modificar
		}
	}
	
	private void convertDisk(int col, int row){
		convertLeftTop(col,row);
		convertLeft(col,row);
		convertLeftBottom(col,row);
		convertTop(col,row);
		convertBottom(col,row);
		convertRightTop(col,row);
		convertRight(col,row);
		convertRightBottom(col,row);
	}


	private void convertLeftTop(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	private void convertLeft(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	private void convertLeftBottom(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	private void convertTop(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	private void convertBottom(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	private void convertRightTop(int col, int row) {
		// TODO Auto-generated method stub
		
	}
	
	private void convertRight(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	private void convertRightBottom(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validLeftTopMove(short temp[][], Triplet<Short,Short,Short> delta){
			
			ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
			if(delta.getFirst()>1 && delta.getSecond()>1 && delta.getFirst()<=7 && delta.getSecond()<=7)
				if( temp[delta.getFirst()-1][delta.getSecond()-1] != delta.getThird() && temp[delta.getFirst()-1][delta.getSecond()-1] != 0 ){
					int j = delta.getSecond()-1;
					for(int i = delta.getFirst()-1;i>=0 && j>=0;i--,j--){
						if(delta.getThird() == temp[i][j])
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
						else
							if(temp[i][j] != 0)
								middledisks.add(new Pair<Short, Short>((short) i, (short) j));
							else
								return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
					}
				}
			return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
			//A9
	}
	
	private void analyzeUtility() {
		if (leftmoves == 0) 
			utility = (count() > white ? white : black);
		//else if (getNumberOfMarkedPositions() == 64) {
		//	utility = 0; 
	
		//}
	}
	
	private short count(){
		short countw = 0;
		short countb = 0;
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(board[i][j] == white)
					countw++;
				else if(board[i][j] == black)
					countb++;
			}
		}
		if(countw > countb)
			return white;
		else
			if (countb > countw)
				return black;
			else
				return 0;
		
	}

}