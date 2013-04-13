package aima.core.environment.otello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.util.datastructure.Triplet;

public class Otello {

	public final static short M1 = 0;
	public final static short C1 = 1;
	public final static short B1 = 2;
	public final static short M2 = 3;
	public final static short C2 = 4;
	public final static short B2 = 5;
	
	private short state [] = new short[6];
	
	private final static short [][] deltas = 
		 {{1,0,1},{0,1,1},{2,0,1},{0,2,1},{1,1,1},
        {-1,0,-1},{0,-1,-1},{-2,0,-1},{0,-2,-1},{-1,-1,-1}}; 
	
	public Otello(){
		state[M1] = 3; state[C1] = 3; state[B1] = 1;
		state[M2] = 0; state[C2] = 0; state[B2] = 0;
	}

	public short[] getState() {
		return state;
	}

	public void setState(short[] state) {
		this.state = state;
	}
	
	public List<Triplet<Short, Short, Short>> getDeltas() {
		List<Triplet<Short,Short,Short> > ds = new ArrayList<Triplet<Short,Short,Short> >();
		
		for (int i=0; i<10; i++)
			ds.add(new Triplet<Short, Short, Short>(new Short((short)deltas[i][0]),
												    new Short((short)deltas[i][1]),
												    new Short((short)deltas[i][2])));
		return ds;
	}

	public boolean validMove(Triplet<Short,Short,Short> delta){
		
		short temp[] = Arrays.copyOf(state, 6);
		
		//el barco esta donde se espera
		if (( (delta.getThird() ==  1) & (temp[B1] > 0) )||
			( (delta.getThird() == -1) & (temp[B2] > 0) )){
			
			temp[M1] -= delta.getFirst();
			temp[C1] -= delta.getSecond();
			temp[B1] -= delta.getThird();
			temp[M2] += delta.getFirst();
			temp[C2] += delta.getSecond();
			temp[B2] += delta.getThird();

			//los canibales se comen a misioneros?
			if (( (temp[M1] > 0) & (temp[C1] > temp[M1]) ) || 
				( (temp[M2] > 0 ) & (temp[C2] > temp[M2])) )
					return false;
			
			//el movimiento es correcto?
			if ( (temp[M1] >= 0) & (temp[C1] >= 0) &
				 (temp[M2] >= 0) & (temp[C2] >= 0) )
				return true; 
		}

		return false;
	}
	
	public void takeTheBoat(Triplet<Short,Short,Short> delta){
		short temp[] = Arrays.copyOf(state, 6);
		
		temp[M1] -= delta.getFirst();
		temp[C1] -= delta.getSecond();
		temp[B1] -= delta.getThird();
		temp[M2] += delta.getFirst();
		temp[C2] += delta.getSecond();
		temp[B2] += delta.getThird();

		this.state = Arrays.copyOf(temp, 6); 
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(state);
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		if ((o == null) || (this.getClass() != o.getClass()))
			return false;
		
		Otello aState = (Otello) o;
		return Arrays.equals(state, aState.getState());
	}
	
	public static void main(String[] args) {
		
		Problem problem = new Problem(new Otello(),
				OtelloFunctionFactory.getActionsFunctions(),
				OtelloFunctionFactory.getResultFunction(),
				new OtelloGoalTest());
		
		Search search = new DepthFirstSearch(new GraphSearch());

		try{
			SearchAgent agent = new SearchAgent(problem, search);
			
			System.out.println("Actions");
			List<Action> actions = agent.getActions();
			for(Action action: actions)
				System.out.println(action);
			
			System.out.println("Metrics");
			Iterator<Object> iter = agent.getInstrumentation().keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				System.out.println(key+": "+agent.getInstrumentation().getProperty(key));
			}
			
			
		}catch(Exception e){
			System.err.println("Capturada Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
