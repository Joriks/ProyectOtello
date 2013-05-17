package aima.core.environment.otello;

import java.util.List;

import aima.core.environment.tictactoe.TicTacToeState;
import aima.core.search.adversarial.Game;
import aima.core.util.datastructure.XYLocation;

public class OtelloGame implements Game<OtelloState,XYLocation,String>{
	OtelloState game = new OtelloState();

	public OtelloState getInitialState() {
		// TODO Auto-generated method stub
		return game;
	}

	public boolean isTerminal(OtelloState currState) { 
		return currState.getUtility()!="-1";
	}

	public OtelloState getResult(OtelloState currState, XYLocation action) {
		// TODO Auto-generated method stub
		OtelloState result = currState.clone();
		result.mark(action);
		return result;
	}

	@Override
	public String[] getPlayers() {
		// TODO Auto-generated method stub
		return new String[] { OtelloState.white, OtelloState.black };
	}

	@Override
	public String getPlayer(OtelloState state) {
		// TODO Auto-generated method stub
		return state.getPlayerToMove();
	}

	@Override
	public List<XYLocation> getActions(OtelloState state) {
		// TODO Auto-generated method stub
		return state.getUnMarkedPositions();
	}

	@Override
	public double getUtility(OtelloState state, String player) {
		// TODO Auto-generated method stub
		double result = Double.parseDouble(state.getUtility());
		if(state.getUtility() != state.Playing){
			if(player == OtelloState.black)
				result = 1 - result;	
		}
		else {
			throw new IllegalArgumentException("State is not Terminal");
		}
		return result;
	}
	
}
