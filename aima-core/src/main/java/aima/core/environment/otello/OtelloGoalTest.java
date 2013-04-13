package aima.core.environment.otello;

import java.util.Arrays;

import aima.core.search.framework.GoalTest;

public class OtelloGoalTest implements GoalTest {
	
	private final static short goalState [] = {0,0,0,3,3,1};

	@Override
	public boolean isGoalState(Object state) {
		Otello Otello = (Otello) state;
		
		return Arrays.equals(Otello.getState(), goalState);
	}

}
