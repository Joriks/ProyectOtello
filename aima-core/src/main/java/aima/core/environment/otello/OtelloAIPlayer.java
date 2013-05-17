package aima.core.environment.otello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aima.core.environment.connectfour.ActionValuePair;
import aima.core.environment.connectfour.ConnectFourState;
import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.util.datastructure.XYLocation;

public class OtelloAIPlayer extends IterativeDeepeningAlphaBetaSearch<OtelloState, XYLocation, String> {

	public OtelloAIPlayer(Game<OtelloState, XYLocation, String> game, int time) {
		super(game, -3.0, +3.0, time);
	}

	/**
	 * Modifies the super implementation by making safe winner values even more
	 * attractive if depth is small.
	 */
	@Override
	protected double eval(OtelloState state, String player) {
	
	
		double score = Puntos(state.getPlayerToMove(), state) - Puntos(state.getAdversary(), state);

        // If the game is over
        if (state.getUtility() != "-1")
        {
            // if player has won
            if (score > 0)
                return 100;
            // if player has lost (or tied)
            else
                return -100;
        }

        // if game isn't over, return relative advatage
        return score;
	
		
	}
	

	
	public double Puntos(String player, OtelloState state)
    {
        double points = 0;

        for (int x = 0; x< 8; x++)
        	for (int y = 0; y<8; y++)
                if (state.getValue(x, y)== player)
                    points++;

        return points;
    }
	



	
}


