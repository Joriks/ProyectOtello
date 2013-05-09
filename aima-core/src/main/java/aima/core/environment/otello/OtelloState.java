package aima.core.environment.otello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.util.datastructure.Triplet;
//import aima.core.util.datastructure.XYLocation;
import aima.core.util.datastructure.XYLocation;



// Definicion del TicTacToe
 
public class OtelloState {
	
private static final short white = 1;
private static final short black = 2;
private static final short EMPTY = 0;

private short leftmoves = 60;


private short board [][] = new short[8][8]; // Tablero/ Board

//Asignación de las posibles posiciones(las vacias)./ free position
private ArrayList<Triplet<Short, Short, Short>> deltas = new ArrayList< Triplet<Short, Short, Short> >();

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
	generateDeltas();
}

private void generateDeltas() {
	
	short black = 1;
	short white = 2;
	for(int i = 0;i < 8;i++){
		for(int j = 0;j < 8;j++){
			deltas.add( new Triplet<Short, Short, Short>(new Short((short) i), new Short((short) j), new Short((short) black)) );
			deltas.add( new Triplet<Short, Short, Short>(new Short((short) i), new Short((short) j), new Short((short) white)) );
		}
	}
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
	//mark(action.getXCoOrdinate(), action.getYCoOrdinate());
}

public void mark(int col, int row) {
	if (utility == -1 && getValue(col, row) == EMPTY) {
		board[col][row] = playerToMove;
		// convertir
		//convertDisks(Triplet<Short, Short, Short> delta);
		analyzeUtility();
		playerToMove = (playerToMove == white ? black : white); // modificar
	}
}

private void analyzeUtility() {
	if (leftmoves == 0) 
		utility = (count() > white ? white : black);
	else if (getNumberOfMarkedPositions() == 64) {
		utility = 0; 
	}
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