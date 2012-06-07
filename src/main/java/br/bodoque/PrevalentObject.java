package br.bodoque;


public abstract class PrevalentObject<T extends Prevalent> implements Prevalent {
	
	private T prevalentObject;
	private Long oID;
	
	public void save() {
		prevalentObject = whoAmI();
		
		if (isThisPrevalentObjectNotPersistedYet())
			generateOIDForThisPrevalentObject();
		
		Command serializeCommand = new SerializeCommand<T>(oID, prevalentObject);
		CommandLogList.addCommand(serializeCommand);
		CommandLogList.executeCommand(serializeCommand);
	}
	
	private void generateOIDForThisPrevalentObject() {
		this.oID = Sequence.getNextOIDFor(prevalentObject.getClass());
	}

	private boolean isThisPrevalentObjectNotPersistedYet() {
		return this.oID == null;
	}

	protected abstract T whoAmI();
	
	public Long getOID() {
		return oID;
	}
}
