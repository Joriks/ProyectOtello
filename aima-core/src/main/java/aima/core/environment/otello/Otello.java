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
import aima.core.util.datastructure.Pair;
import aima.core.util.datastructure.Triplet;

public class Otello {

	public final static short X = 0;
	public final static short Y = 1;
	public final static short Colour = 2;
	//En principio no hacen falta
	
	private short state [][] = new short[8][8];
	//State = 0 Vacio
	//State = 1 Blancas
	//State = 2 Negras
	
	private ArrayList<Triplet<Short, Short, Short>> deltas = new ArrayList< Triplet<Short, Short, Short> >();
	//A3
	
	public Otello(){
		
		for(int i = 0;i < 8;i++){
			for(int j = 0;j < 8;j++){
				state[i][j] = 0;
			}
		}
		state[3][3] = state[4][4] = 1;
		state[3][4] = state[4][3] = 2;
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
	
	private void showDeltas(){
		
		for(Triplet<Short, Short, Short> triplet: deltas){
			System.out.println("Delta: <"+(short) triplet.getFirst()+","+(short) triplet.getSecond()+","+( ( (short) triplet.getThird() == 1 ) ? "White" : "Black" )+">");
		}
		
	}
	
	public short[][] getState() {
		return state;
	}

	public void setState(short[][] state) {
		this.state = state;
	}
	
	public List<Triplet<Short, Short, Short>> getDeltas() {
		
		List< Triplet<Short, Short, Short> > deltalist = new ArrayList<Triplet<Short, Short, Short> >();
		//A4
		for(Triplet<Short, Short, Short> triplet: deltas){
			deltalist.add(triplet);
		}
		
		return deltalist;
		
	}
	
	public boolean validMove(Triplet<Short, Short, Short> delta){
		
		short temp[][] = Arrays.copyOf(state, 64);
		//A5
		if(temp[delta.getFirst()][delta.getSecond()] == 0){
			Pair<Boolean, ArrayList<Pair<Short, Short>>> LT = validLeftTopMove(temp, delta);
			Pair<Boolean, ArrayList<Pair<Short, Short>>> L = validLeftMove(temp, delta);
			Pair<Boolean, ArrayList<Pair<Short, Short>>> LB = validLeftBottomMove(temp, delta);
			Pair<Boolean, ArrayList<Pair<Short, Short>>> T = validTopMove(temp, delta);
			Pair<Boolean, ArrayList<Pair<Short, Short>>> B = validBottomMove(temp, delta);
			Pair<Boolean, ArrayList<Pair<Short, Short>>> RT = validRightTopMove(temp, delta);
			Pair<Boolean, ArrayList<Pair<Short, Short>>> R = validRightMove(temp, delta);
			Pair<Boolean, ArrayList<Pair<Short, Short>>> RB = validRigthBottomMove(temp, delta);
			if(LT.getFirst() || L.getFirst() || LB.getFirst() || 
					T.getFirst() || B.getFirst() ||
					RT.getFirst() || R.getFirst() || RB.getFirst())//A6
					return true;
		}
		return false;
		
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validLeftTopMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>1 && delta.getSecond()>1 && delta.getFirst()<=7 && delta.getSecond()<=7)
			if( temp[delta.getFirst()-1][delta.getSecond()-1] != delta.getThird() && temp[delta.getFirst()-1][delta.getSecond()-1] != 0 ){
				int j = delta.getSecond()-1;
				for(int i = delta.getFirst()-1;i>=0 && j>=0;i--,j--){
					if(delta.getThird() == temp[i][j])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[i][j] != 0)
							middledisks.add(new Pair<Short, Short>((short) i, (short) j));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		//A9
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validLeftMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>1 && delta.getSecond()>=1 && delta.getFirst()<=7 && delta.getSecond()<=7)
			if( temp[delta.getFirst()-1][delta.getSecond()] != delta.getThird() && temp[delta.getFirst()-1][delta.getSecond()] != 0 ){
				for(int i = delta.getFirst()-1;i>=0;i--){
					if(delta.getThird() == temp[i][delta.getSecond()])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[i][delta.getSecond()] != 0)
							middledisks.add(new Pair<Short, Short>((short) i, (short) delta.getSecond()));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validLeftBottomMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>1 && delta.getSecond()>=1 && delta.getFirst()<=7 && delta.getSecond()<7)
			if( temp[delta.getFirst()-1][delta.getSecond()+1] != delta.getThird() && temp[delta.getFirst()-1][delta.getSecond()+1] != 0 ){
				int j = delta.getSecond()+1;
				for(int i = delta.getFirst()-1;i>=0 && j<=8;i--,j++){
					if(delta.getThird()==temp[i][j])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[i][j] != 0)
							middledisks.add(new Pair<Short, Short>((short) i,(short) j));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validTopMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>=1 && delta.getSecond()>1 && delta.getFirst()<=7 && delta.getSecond()<=7)
			if( temp[delta.getFirst()][delta.getSecond()-1] != delta.getThird() && temp[delta.getFirst()][delta.getSecond()-1] != 0 ){
				for(int j = delta.getSecond()-1;j>=0;j--){
					if(delta.getThird()==temp[delta.getFirst()][j])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[delta.getFirst()][j] != 0)
							middledisks.add(new Pair<Short, Short>((short) delta.getFirst(),(short) j));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validBottomMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>=1 && delta.getSecond()>=1 && delta.getFirst()<=7 && delta.getSecond()<7)
			if( temp[delta.getFirst()][delta.getSecond()+1] != delta.getThird() && temp[delta.getFirst()][delta.getSecond()+1] != 0 ){
				for(int j = delta.getSecond()+1;j<=8;j++){
					if(delta.getThird()==temp[delta.getFirst()][j])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[delta.getFirst()][j] != 0)
							middledisks.add(new Pair<Short, Short>((short) delta.getFirst(), (short) j));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validRightTopMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>=1 && delta.getSecond()>1 && delta.getFirst()<7 && delta.getSecond()<=7)
			if( temp[delta.getFirst()+1][delta.getSecond()-1] != delta.getThird() && temp[delta.getFirst()+1][delta.getSecond()-1] != 0 ){
				int j = delta.getSecond()-1;
				for(int i = delta.getFirst()+1;i<=8 && j>=0;i++,j--){
					if(delta.getThird()==temp[i][j])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[i][j] != 0)
							middledisks.add(new Pair<Short, Short>((short) i, (short) j));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}	
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validRightMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>=1 && delta.getSecond()>=1 && delta.getFirst()<7 && delta.getSecond()<=7)
			if( temp[delta.getFirst()+1][delta.getSecond()] != delta.getThird() && temp[delta.getFirst()+1][delta.getSecond()] != 0 ){
				for(int i = delta.getFirst()+1;i<=8;i++){
					if(delta.getThird()==temp[i][delta.getSecond()])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[i][delta.getSecond()] != 0)
							middledisks.add(new Pair<Short, Short>((short) i, (short) delta.getSecond()));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		
	}
	
	private Pair<Boolean, ArrayList<Pair<Short, Short>>> validRigthBottomMove(short temp[][], Triplet<Short,Short,Short> delta){
		
		ArrayList<Pair<Short, Short>> middledisks = new ArrayList<Pair<Short,Short>>();
		if(delta.getFirst()>=1 && delta.getSecond()>=1 && delta.getFirst()<7 && delta.getSecond()<7)
			if( temp[delta.getFirst()+1][delta.getSecond()+1] != delta.getThird() && temp[delta.getFirst()+1][delta.getSecond()+1] != 0 ){
				int j = delta.getSecond()+1;
				for(int i = delta.getFirst()+1;i<=8 && j<=8;i++,j++){
					if(delta.getThird()==temp[i][j])
						return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) true, middledisks);
					else
						if(temp[i][j] != 0)
							middledisks.add(new Pair<Short, Short>((short) i, (short) j));
						else
							return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
				}
			}
		return new Pair<Boolean, ArrayList<Pair<Short, Short>>>((boolean) false, null);
		
	}
	
	public void putDisk(Triplet<Short, Short, Short> delta){
		//A7
		short temp[][] = Arrays.copyOf(state, 64);
		temp[delta.getFirst()][delta.getSecond()] = delta.getThird();
		this.state = Arrays.copyOf(temp, 64);
		
	}
	
	public void convertDisks(Triplet<Short, Short, Short> delta){
		
		short temp[][] = Arrays.copyOf(state, 64);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> LT = validLeftTopMove(temp, delta);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> L = validLeftMove(temp, delta);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> LB = validLeftBottomMove(temp, delta);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> T = validTopMove(temp, delta);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> B = validBottomMove(temp, delta);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> RT = validRightTopMove(temp, delta);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> R = validRightMove(temp, delta);
		Pair<Boolean, ArrayList<Pair<Short, Short>>> RB = validRigthBottomMove(temp, delta);
		if(LT.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = LT.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		if(L.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = L.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		if(LB.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = LB.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		if(T.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = T.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		if(B.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = B.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		if(RT.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = RT.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		if(R.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = R.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		if(RB.getFirst()){
			ArrayList<Pair<Short, Short>> convertlist = RB.getSecond();
			for(Pair<Short, Short> convertdisk: convertlist){
				temp[convertdisk.getFirst()][convertdisk.getSecond()] = delta.getThird();
			}
		}
		this.state = Arrays.copyOf(temp, 64);
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
				OtelloFunctionFactory.getActionsFunction(),
				OtelloFunctionFactory.getResultFunction(),
				new OtelloGoalTest());
		
		Search search = new DepthFirstSearch(new GraphSearch());

		try{
			SearchAgent agent = new SearchAgent(problem, search);
			//El agente es el que une el problema con el algoritmo de busqueda
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
