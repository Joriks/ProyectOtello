package aima.gui.demo.search;

import aima.core.environment.otello.OtelloGame;
import aima.core.environment.otello.OtelloState;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation;

public class OtelloDemo {
	public static void main(String[] args) {
		System.out.println("OTELLO DEMO");
		System.out.println("");
		startMinimaxDemo();
		startAlphaBetaDemo();
	}

	private static void startMinimaxDemo() {
		System.out.println("MINI MAX DEMO\n");
		OtelloGame game = new OtelloGame();
		OtelloState currState = game.getInitialState();
		AdversarialSearch<OtelloState, XYLocation> search = MinimaxSearch.createFor(game);
		while (!(game.isTerminal(currState))) {
			System.out.println(game.getPlayer(currState) + "  playing ... ");
			XYLocation action = search.makeDecision(currState);
			currState = game.getResult(currState, action);
			System.out.println(currState);
		}
		System.out.println("MINI MAX DEMO done");
	}

	private static void startAlphaBetaDemo() {
		System.out.println("ALPHA BETA DEMO\n");
		OtelloGame game = new OtelloGame();
		OtelloState currState = game.getInitialState();
		AdversarialSearch<OtelloState, XYLocation> search = AlphaBetaSearch
				.createFor(game);
		while (!(game.isTerminal(currState))) {
			System.out.println(game.getPlayer(currState) + "  playing ... ");
			XYLocation action = search.makeDecision(currState);
			currState = game.getResult(currState, action);
			System.out.println(currState);
		}
		System.out.println("ALPHA BETA DEMO done");
	}

}
