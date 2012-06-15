package br.bodoque;

import java.util.ArrayList;
import java.util.List;


public class CommandLogList {

	private static List<Command> commandLogList;
	
	static {
		commandLogList = new ArrayList<Command>(500);
	}
	
	public synchronized static void addCommand(Command command) {
		int commandIndex = commandLogList.indexOf(command);
		if (commandIndex == -1)
			commandLogList.add(command);
		else
			commandLogList.set(commandIndex, command);
	}
	
	public synchronized static void removeCommand(Command command) {
		int commandIndex = commandLogList.indexOf(command);
		if (commandIndex >= 0)
			commandLogList.remove(commandIndex);
	}

	public static void clearLogList() {
		commandLogList.clear();
	}

	public synchronized static List<Command> getLogList() {
		//TODO Verify method caller, if caller is Reader Thread return mutable list
		//TODO else return unmodifiable list (read-only)
		return commandLogList;
	}

	public synchronized static void executeCommand(Command command) {
		int commandIndex = commandLogList.indexOf(command);
		if (commandIndex == -1)
			throw new IllegalArgumentException("Command " + command + "does not exist in log list");
		
		Command commandoToExecute = commandLogList.get(commandIndex);
		commandoToExecute.execute();
	}


}
