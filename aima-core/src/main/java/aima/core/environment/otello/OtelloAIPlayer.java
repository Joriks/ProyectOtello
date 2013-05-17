package aima.core.environment.otello;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;

public class OtelloAIPlayer extends IterativeDeepeningAlphaBetaSearch<OtelloState, Integer, String> {

	public OtelloAIPlayer(Game<OtelloState, Integer, String> game, int time) {
		super(game, 0.0, 1.0, time);
	}

}
