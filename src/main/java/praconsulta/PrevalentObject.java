package praconsulta;


public abstract class PrevalentObject<T> implements Prevalent {

	private T prevalentObject;
	private Command<T> serializeCommand;
	
	/**
	 * Return the reference to your object, i.e, return this
	 * @return this
	 */
	public abstract T whoAmI();
	
	public void save() {
		prevalentObject = whoAmI();
		serializeCommand = new SerializeCommand<T>(prevalentObject);
		addCommandToQueue(serializeCommand);
	}
	
	public static <T> void save(T prevalentObject) {
		if (!(prevalentObject instanceof PrevalentObject)) {
			return;
		}
		Command<T> serializeCommand = new SerializeCommand<T>(prevalentObject);
		addCommandToQueue(serializeCommand);
	}
	
	private static <T> void addCommandToQueue(Command<T> command) {
		CommandQueue.addCommand(command);
//		CommandQueue.executeQueueCommands();
	}
}
