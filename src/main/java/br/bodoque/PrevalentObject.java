package br.bodoque;


public abstract class PrevalentObject<T extends Prevalent> implements Prevalent {
	
	private T prevalentObject;
	private Long oID;
	
	public void save() {
		prevalentObject = whoAmI();
		
		//OMG not possible use is* in method names because FlexJson serialize it \o/
		if (this.ehPrevalentObjectNotPersistedYet())
			this.generateOIDForThisPrevalentObject();
		
		Command serializeCommand = createSerializeCommand(prevalentObject);
		addCommandToLogList(serializeCommand);
	}
	
	public static <T extends Prevalent> void save(T prevalentObject) {
		Command serializeCommand = createSerializeCommand(prevalentObject);
		addCommandToLogList(serializeCommand);
	}

	private static void addCommandToLogList(Command serializeCommand) {
		CommandLogList.addCommand(serializeCommand);
		CommandLogList.executeCommand(serializeCommand);
	}

	private static <T extends Prevalent> Command createSerializeCommand(T prevalentObject) {
		Command serializeCommand = new SerializeCommand<T>(prevalentObject);
		return serializeCommand;
	}
	
	private void generateOIDForThisPrevalentObject() {
		this.oID = Sequence.getNextOIDFor(prevalentObject.getClass());
	}

	private boolean ehPrevalentObjectNotPersistedYet() {
		return this.oID == null;
	}

	protected abstract T whoAmI();
	
	public Long getOID() {
		return oID;
	}
}
