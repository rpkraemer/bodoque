package praconsulta;

import java.util.LinkedList;
import java.util.Queue;

public class CommandQueue {

	@SuppressWarnings("rawtypes")
	private static Queue<Command> queue = new LinkedList<Command>();
	
	@SuppressWarnings("rawtypes")
	synchronized public static void addCommand(Command command) {
		queue.offer(command);
	}
	
	@SuppressWarnings("rawtypes")
	synchronized public static void executeQueueCommands() {
		while (!queue.isEmpty()) {
			Command command = queue.poll();
			command.execute();
		}
	}
}
