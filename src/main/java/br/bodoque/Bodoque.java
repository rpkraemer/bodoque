package br.bodoque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bodoque {

	static Logger logger = LogManager.getLogger(Bodoque.class.getName());
	
	public static void initialize() {
		logger.info("Inicializando Bodoque");
		initializeBodoqueResources();
	}

	private static void initializeBodoqueResources() {
		Repository.clearRepository();
		Sequence.clearSequences();
	}
	
}