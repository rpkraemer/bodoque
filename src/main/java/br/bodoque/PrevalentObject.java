package br.bodoque;

public abstract class PrevalentObject<T extends Prevalent> implements Prevalent {
	
	private T prevalentObject;
	private Long oID;

	public void save() {
		prevalentObject = whoAmI();
		
		//OMG not possible use is* in method names because FlexJson serialize it \o/
		if (this.ehPrevalentObjectNotPersistedYet())
			this.generateOIDForThisPrevalentObject();
		
		addPrevalentObjectToRepository(prevalentObject);
		Command serializeCommand = createSerializeCommand(prevalentObject);
		addCommandToLogList(serializeCommand);
	}
	
	public void delete(boolean... shouldRaiseException) {
		prevalentObject = whoAmI();
		if (shouldRaiseException.length > 0) {
			if(this.oID == null) { 
				String message = String.format("Cannot remove object: %s. Only allowed to remove objects saved.", prevalentObject);
				throw new CannotRemovePrevalentObjectException(message); 
			} 
		}
		deletePrevalentObject(prevalentObject, this.oID);
		removeCommandFromLogList(prevalentObject);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Prevalent> void addPrevalentObjectToRepository(T prevalentObject) {
		Repository.addPrevalentObject(prevalentObject, 
				((PrevalentObject<T>) prevalentObject).getOID());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> void save(T prevalentObject) {
		if (((PrevalentObject<T>) prevalentObject).getOID() == null)
			((PrevalentObject<T>) prevalentObject).oID = Sequence.getNextOIDFor(prevalentObject.getClass());
		
		addPrevalentObjectToRepository(prevalentObject);
		Command serializeCommand = createSerializeCommand(prevalentObject);
		addCommandToLogList(serializeCommand);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> void delete(T prevalentObject) {
		Long oId = ((PrevalentObject<T>) prevalentObject).getOID();
		if (oId == null) { return ; }
		deletePrevalentObject(prevalentObject, oId);
		removeCommandFromLogList(prevalentObject);
	}
	


	private static <T extends Prevalent> void removeCommandFromLogList(T prevalentObject) {
		CommandLogList.removeCommand(new SerializeCommand<Prevalent>(prevalentObject));
	}

	private static <T extends Prevalent> void deletePrevalentObject(T prevalentObject, Long oId) {
		Repository.deletePrevalentObject(prevalentObject, oId);
	}

	private static void addCommandToLogList(Command serializeCommand) {
		CommandLogList.addCommand(serializeCommand);
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
