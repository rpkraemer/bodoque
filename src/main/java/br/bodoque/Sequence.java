package br.bodoque;

import java.util.HashMap;
import java.util.Map;

public class Sequence {

	private static Map<Class<? extends Prevalent>, Long> sequences;
	
	static {
		sequences = new HashMap<Class<? extends Prevalent>, Long>();
	}
	
	public static Long getNextOIDFor(Class<? extends Prevalent> prevalentObjectClass) {
		Long nextOID = sequences.get(prevalentObjectClass);
		if (nextOID == null) {
			sequences.put(prevalentObjectClass, 1L);
			nextOID = 1L;
		} else 
			sequences.put(prevalentObjectClass, ++nextOID);

		return nextOID;
	}

	public static void clearSequenceFor(Class<? extends Prevalent> prevalentObjectClass) {
		sequences.remove(prevalentObjectClass);
	}
}
