package br.bodoque;

import java.io.IOException;
import java.util.Iterator;
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
			List<Command> commandsToFlush = CommandLogList.getLogList();
			Iterator<Command> iterator = commandsToFlush.iterator();
			while (iterator.hasNext()) {
				Command commandToExecute = iterator.next();
				commandToExecute.execute();
				
				if (writer == null)
					writer = new SnapshotWriter();
			
				try {
					writer.writeToSnapshot(
							((SerializeCommand<Prevalent>) commandToExecute).
							getJSONRepresentation());
					commandToExecute.setRemovable(true);
				} catch (IOException e) {
					// TODO where is my logger?
				}
			}
			cleanLogListWhenIsToBig();
			
			if (writer != null)
				writer.flushAndClose();
			writer = null; //to create new snapshot next cycle of writes
			
			try {
				Thread.sleep(secondsPollInterval);
			} catch (InterruptedException e) {
				//TODO where is my logger?!
			}
		}
	}

	private void cleanLogListWhenIsToBig() {
		List<Command> commandLogList = CommandLogList.getLogList(); 
		if (commandLogList.size() >= 1000) {
			Iterator<Command> iterator = commandLogList.iterator();
			while (iterator.hasNext()) {
				Command commandToBeRemoved = iterator.next();
				if (commandToBeRemoved.isRemovable())
					CommandLogList.removeCommand(commandToBeRemoved);
			}
		}
	}
}
