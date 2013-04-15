package aima.core.environment.otello;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;
import aima.core.util.datastructure.Triplet;

public class OtelloFunctionFactory {
	//A10
	private static ActionsFunction actionsFunction = null;
	private static ResultFunction resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if(null == actionsFunction)
			actionsFunction = new OtelloActionsFunction();
		return actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if(null == resultFunction)
			resultFunction = new OtelloResultFunction();
		return resultFunction;
	}
	
	private static class OtelloActionsFunction implements ActionsFunction{

		@Override
		public Set<Action> actions(Object s) {
			Set<Action> actions = new LinkedHashSet<Action>();
			
			Otello mc = (Otello) s;
			//obtener deltas
			List<Triplet<Short,Short,Short>> deltas = mc.getDeltas();
			//para cada delta valido crear accion y anadirla a conjunto
			for(Triplet<Short,Short,Short> delta: deltas)
				if(mc.validMove(delta))
					if(delta.getThird() == 1){
						actions.add(new OtelloAction(OtelloAction.PUT_WHITE_DISK,delta));
						actions.add(new OtelloAction(OtelloAction.CONVERT_TO_WHITE,delta));
					}
					else
						if(delta.getThird() == 2){
							actions.add(new OtelloAction(OtelloAction.PUT_BLACK_DISK,delta));
							actions.add(new OtelloAction(OtelloAction.CONVERT_TO_BLACK,delta));
						}
			
			return actions;
		}
	}
	
	private static class OtelloResultFunction implements ResultFunction{

		@Override
		public Object result(Object s, Action a) {
			if (a instanceof OtelloAction){
				
				OtelloAction cAction = (OtelloAction) a;
				Otello state = (Otello) s;
				
				Otello newState = new Otello();
				newState.setState(state.getState());
				
				if (cAction.getName() == OtelloAction.PUT_WHITE_DISK)
					newState.putDisk(cAction.getDelta());
				else
					if(cAction.getName() == OtelloAction.PUT_BLACK_DISK)
						newState.putDisk(cAction.getDelta());
					else
						if(cAction.getName() == OtelloAction.CONVERT_TO_WHITE)
							newState.convertDisks(cAction.getDelta());
						else
							if(cAction.getName() == OtelloAction.CONVERT_TO_BLACK)
								newState.convertDisks(cAction.getDelta());
				s = newState;
			}
				
			return s;
		}
		
	}
		
}
