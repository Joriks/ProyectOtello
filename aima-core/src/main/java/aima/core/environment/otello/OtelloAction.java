package aima.core.environment.otello;

import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.Triplet;

public class OtelloAction extends DynamicAction {
	
	//Action names
	public static final String TAKE_THE_BOAT = "takeTheBoat";
	public static final String PUT_WHITE_DISK = "putWhiteDisk";
	public static final String PUT_BLACK_DISK = "putBlacDisk";
	public static final String CONVERT_TO_WHITE = "convertToWhite";
	public static final String CONVERT_TO_BLACK = "convertToBlack";
	
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

	public short getX() {
		return getDelta().getFirst();
	}

	public short getY() {
		return getDelta().getSecond();
	}
	
	public short getColour() {
		return getDelta().getThird();
	}

}
