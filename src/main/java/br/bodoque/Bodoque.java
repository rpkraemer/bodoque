package br.bodoque;

import java.io.IOException;
import java.util.List;


public class Bodoque {

	public static void initialize() {
		initializeBodoqueResources();
		initializeLogListDaemonThread();
	}

	private static void initializeLogListDaemonThread() {
		Runnable logListReaderRunnable = new LogListDaemon();
		Thread readerThread = new Thread(logListReaderRunnable);
		
		readerThread.start();
	}

	private static void initializeBodoqueResources() {
		CommandLogList.clearLogList();
		Repository.clearRepository();
		Sequence.clearSequences();
	}
}

class LogListDaemon implements Runnable {
	
	private final int secondsPollInterval = 15 * 1000;
	private SnapshotWriter writer;
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(secondsPollInterval);
			} catch (InterruptedException e) {
				//TODO where is my logger?!
			}
			
			List<Command> commandsToFlush = CommandLogList.getLogList();
			for (int index = 0; !commandsToFlush.isEmpty(); index++) {
				Command commandToExecute = commandsToFlush.get(index);
				commandToExecute.execute();
				
				if (writer == null)
					writer = new SnapshotWriter();
			
				try {
					writer.writeToSnapshot(
							((SerializeCommand<Prevalent>) commandToExecute).
							getJSONRepresentation());
					CommandLogList.removeCommand(commandToExecute);
				} catch (IOException e) {
					// TODO where is my logger?
				}
			}
		}
	}
}
