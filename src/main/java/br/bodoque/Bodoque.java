package br.bodoque;

public class Bodoque {

	public static void initialize() {
		initializeBodoqueResources();
	}

	private static void initializeBodoqueResources() {
		Repository.clearRepository();
		Sequence.clearSequences();
	}
	
}