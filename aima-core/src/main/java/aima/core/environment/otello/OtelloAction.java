package aima.core.environment.otello;

import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.Triplet;

public class OtelloAction extends DynamicAction {
	
	//Action names
	public static final String TAKE_THE_BOAT = "takeTheBoat";

	//Action params
	public static final String ATTRIBUTE_DELTA = "delta";
	
	public OtelloAction(String name, Triplet<Short,Short,Short> delta) {
		super(name);
		this.setAttribute(ATTRIBUTE_DELTA, delta);
	}
	
	@SuppressWarnings("unchecked")
	public Triplet<Short,Short,Short> getDelta() {
		return (Triplet<Short,Short,Short>) getAttribute(ATTRIBUTE_DELTA);
	}

	public short getM() {
		return getDelta().getFirst();
	}

	public short getC() {
		return getDelta().getSecond();
	}
	
	public short getB() {
		return getDelta().getThird();
	}

}
