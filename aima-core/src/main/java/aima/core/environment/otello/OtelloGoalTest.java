package aima.core.environment.otello;

import java.util.Arrays;

import aima.core.search.framework.GoalTest;

public class OtelloGoalTest implements GoalTest {
	
	private final static short goalState [] = {0,0,0,3,3,1};
	private short leftmoves = 60;
	@Override
	public boolean isGoalState(Object state) {
		/*Otello Otello = (Otello) state;
		
		return Arrays.equals(Otello.getState(), goalState);*/
		if(leftmoves==0)
			return true;
		else{
			leftmoves--;
			return false;
		}
	}
	
}
