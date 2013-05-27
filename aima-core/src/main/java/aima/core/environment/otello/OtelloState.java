package aima.core.environment.otello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.environment.tictactoe.TicTacToeState;
import aima.core.util.datastructure.Pair;
import aima.core.util.datastructure.Triplet;
import aima.core.util.datastructure.XYLocation;
//import aima.core.util.datastructure.XYLocation;



//Definicion del Otello
 
public class OtelloState implements Cloneable{
	
	public static final String white = "1";
	public static final String black = "2";
	public static final String EMPTY = "0";

	private short leftmoves = 60;


	private String board [][] = new String[8][8]; // Tablero/ Board
	//board = 0 Vacio
	//board = 1 Blancas
	//board = 2 Negras

	private String playerToMove = black;

	public String utility = Playing; // 1: win for White, 2: win for Black, 0: draw
	public static final String Playing = "-1";


	public OtelloState(){ // Constructor de la clase, inicaliza el tablero./Class Constructor. Initiate Board.
	
		for(int i = 0;i < 8;i++){
			for(int j = 0;j < 8;j++){
				board[i][j] = EMPTY;
			}
		}
/*		board[3][3] = board[4][4] = white;
		board[3][4] = board[4][3] = black;
		*/
		board[1][4] = board[3][6] = white;
		board[1][5] = board[2][6] = board[2][7] = black;
	}
	
	public String getAdversary(){
		
		if(playerToMove == "1")
			return "2";
		else
			return "1";
	}
	
	public String getPlayerToMove() {
		return playerToMove;
	}
	
	public boolean isEmpty(int col, int row) {
		return board[col][row] == EMPTY;
	}
	
	public String getValue(int col, int row) {
		return board[col][row];
	}
	
	public String getUtility() {
		return utility;
	}
	
	public void mark(XYLocation action) { // Marca y Convierte Fichas!!!
		mark(action.getXCoOrdinate(), action.getYCoOrdinate());
	}
	
	public void mark(int col, int row) {
		if (utility == Playing && getValue(col, row) == EMPTY) {
			String temp[][] = new String[8][8];
			temp = cloneM(board);
			board[col][row] = playerToMove;
			if(!convertDisks(col,row)){
				board = cloneM(temp);	
			}
			else{
				analyzeUtility();
				playerToMove = (playerToMove == white ? black : white);
			}
		}
	}
	
	
	
	private boolean convertDisks(int col, int row){
		boolean a1,a2,a3,a4,a5,a6,a7,a8;
		a1 = convertLeftTop(col, row);
		a2 = convertLeft(col, row);
		a3 = convertLeftBottom(col, row);
		a4 = convertTop(col, row);
		a5 = convertBottom(col, row);
		a6 = convertRightBottom(col, row);
		a7 = convertRight(col, row);
		a8 = convertRightTop(col, row);
		if(a1 || a2 || a3 || a4 || a5 || a6 || a7 || a8){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean convertLeftTop(int col, int row) {
		// TODO Auto-generated method stub
		String temp[][] = cloneM(board);
		if(col>0 && row >0 && col <=7 && row<=7)
			if(temp[col-1][row-1] != getPlayerToMove() && temp[col-1][row-1] != EMPTY){
				int j = row-1;
				for(int i = col-1;i>=0 && j>=0;i--,j--)
					if(getPlayerToMove() == temp[i][j]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[i][j] != EMPTY)
							temp[i][j] = getPlayerToMove();
						else
							return false;
					}
				
			}
		return false;		
	}

	private boolean convertLeft(int col, int row) {
		// TODO Auto-generated method stub
		String temp [][] =  cloneM(board);
		if(col>0 && row >=0 && col <=7 && row<=7)
			if(temp[col-1][row] != getPlayerToMove() && temp[col-1][row] != EMPTY){
				for(int i = col-1;i>=0;i--)
					if(getPlayerToMove() == temp[i][row]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[i][row] != EMPTY)
							temp[i][row] = getPlayerToMove();
						else
							return false;
					}
				
			}
		return false;
	}

	private boolean convertLeftBottom(int col, int row) {
		// TODO Auto-generated method stub
		String temp [][] =  cloneM(board);
		if(col>0 && row >0 && col <=7 && row<7)
			if(temp[col-1][row+1] != getPlayerToMove() && temp[col-1][row+1] != EMPTY){
				int j = row+1;
				for(int i = col-1;i>=0 && j>=0;i--,j++)
					if(getPlayerToMove() == temp[i][j]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[i][j] != EMPTY)
							temp[i][j] = getPlayerToMove();
						else
							return false;
					}
				
			}
		return false;
	}

	private boolean convertTop(int col, int row) {
		// TODO Auto-generated method stub
		String temp [][] =  cloneM(board);
		if(col>=0 && row >0 && col <=7 && row<=7)
			if(temp[col][row-1] != getPlayerToMove() && temp[col][row-1] != EMPTY){
				for(int j = row-1;j>=0;j--)
					if(getPlayerToMove() == temp[col][j]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[col][j] != EMPTY)
							temp[col][j] = getPlayerToMove();
						else
							return false;
					}
			}
		return false;
	}

	private boolean convertBottom(int col, int row) {
		// TODO Auto-generated method stub
		String temp [][] =  cloneM(board);
		if(col>=0 && row >=0 && col <=7 && row<7)
			if(temp[col][row+1] != getPlayerToMove() && temp[col][row+1] != EMPTY){
				for(int j = row+1;j<8;j++)
					if(getPlayerToMove() == temp[col][j]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[col][j] != EMPTY)
							temp[col][j] = getPlayerToMove();
						else
							return false;
					}
				
			}
		return false;
	}

	private boolean convertRightTop(int col, int row) {
		// TODO Auto-generated method stub
		String temp [][] =  cloneM(board);
		if(col>=0 && row > 0 && col <7 && row<7)
			if(temp[col+1][row-1] != getPlayerToMove() && temp[col+1][row-1] != EMPTY){
				int j = row-1;
				for(int i = col+1;i<8 && j>=0;i++,j--)
					if(getPlayerToMove() == temp[i][j]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[i][j] != EMPTY)
							temp[i][j] = getPlayerToMove();
						else
							return false;
					}
				
			}
		return false;
	}
	
	private boolean convertRight(int col, int row) {
		// TODO Auto-generated method stub
		String temp [][] =  cloneM(board);
		if(col>=0 && row >=0 && col <7 && row<=7)
			if(temp[col+1][row] != getPlayerToMove() && temp[col+1][row] != EMPTY){
				for(int i = col+1;i<8;i++)
					if(getPlayerToMove() == temp[i][row]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[i][row] != EMPTY)
							temp[i][row] = getPlayerToMove();
						else
							return false;
					}
				
			}
		return false;
	}

	private boolean convertRightBottom(int col, int row) {
		// TODO Auto-generated method stub
		String temp [][] =  cloneM(board);
		if(col>=0 && row >=0 && col <7 && row<7)
			if(temp[col+1][row+1] != getPlayerToMove() && temp[col+1][row+1] != EMPTY){
				int j = row+1;
				for(int i = col+1;i<8 && j<8;i++,j++){
					if(getPlayerToMove() == temp[i][j]){
						board = cloneM(temp);
						return true;
					}
					else{
						if(temp[i][j] != EMPTY)
							temp[i][j] = getPlayerToMove();
						else
							return false;
					}
				}
				
			}
		return false;
	}

	
	
	private void analyzeUtility() {
		if (leftmoves == 0) 
			utility = (count().equalsIgnoreCase(white) ? white : black);
	}
	
	private String count(){
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
				return EMPTY;
		
	}
	
	

	public List<XYLocation> getUnMarkedPositions() {
		// TODO Auto-generated method stub
		List<XYLocation> result = new ArrayList<XYLocation>();
		for(int i = 0;i<8;i++){
			for(int j = 0;j<8;j++){
				if(getValue(i, j) == EMPTY )
					result.add(new XYLocation(i, j));
			}
		}
		return result;
	}
	
	@Override
	public OtelloState clone() {
		OtelloState copy = null;
		try {
			copy = (OtelloState) super.clone();
			copy.board = (String[][]) board.clone();
			for(int i = 0;i<board.length;i++)
				copy.board[i] = board[i].clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace(); // should never happen...
		}
		return copy;
	}
	
	private String[][] cloneM(String[][] tab){
		String copy[][] = new String[8][8];
		for(int i = 0;i<tab.length;i++)
			copy[i] = tab[i].clone();
		return copy;
	}
	
	public int get_Col(){
		return board.length;
	}
	
	public int get_Row(){
		return board[0].length;
	}
	
	public int getMoves(){
		return 60-leftmoves;
	}
}