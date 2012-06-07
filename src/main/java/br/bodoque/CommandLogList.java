package br.bodoque;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CommandLogList {

	private static List<Command> commandLogList;
	
	static {
		commandLogList = new ArrayList<Command>(500);
	}
	
	public static void addCommand(Command command) {
		int commandIndex = commandLogList.indexOf(command);
		if (commandIndex == -1)
			commandLogList.add(command);
		else
			commandLogList.set(commandIndex, command);
	}

	public static void clearLogList() {
		commandLogList.clear();
	}

	public static List<Command> getLogList() {
		return Collections.unmodifiableList(commandLogList);
	}

	public static void executeCommand(Command command) {
		int commandIndex = commandLogList.indexOf(command);
		if (commandIndex == -1)
			throw new IllegalArgumentException("Command " + command + "does not exist in log list");
		
		Command commandoToExecute = commandLogList.get(commandIndex);
		commandoToExecute.execute();
	}


}
