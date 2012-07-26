package br.bodoque;

public abstract class PrevalentObject<T extends Prevalent> implements Prevalent {
	
	private T prevalentObject;
	private Long oID;

	public boolean save() {
		prevalentObject = whoAmI();
		
		if (isPrevalentObjectNotPersistedYet())
			generateOIDForThisPrevalentObject();
		
		addPrevalentObjectToRepository(prevalentObject);
		Command serializeCommand = createSerializeCommand(prevalentObject);
		addCommandToLogList(serializeCommand);
		return true;
	}
	
	public boolean delete(boolean... shouldRaiseException) {
		prevalentObject = whoAmI();
		if (!raiseOrNotThisIsTheQuestion(this.getOID(), prevalentObject.getClass(), 
										 shouldRaiseException)) {
			return false;
		}
		deletePrevalentObject(prevalentObject, this.oID);
		removeCommandFromLogList(prevalentObject);
		return true;
	}

	private static boolean raiseOrNotThisIsTheQuestion(Long oID, Class<? extends Prevalent> 
													prevalentObjectClass, boolean... shouldRaiseException) {
		if(oID == null) { 
			if (shouldRaiseException.length > 0 && shouldRaiseException[0]) {
				String message = String.format("Cannot remove object of class %s. " +
						"Only allowed to remove objects saved.", prevalentObjectClass.getSimpleName());
				throw new CannotDeletePrevalentObjectException(message); 
			} else {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Prevalent> void addPrevalentObjectToRepository(T prevalentObject) {
		Repository.addPrevalentObject(prevalentObject, 
				((PrevalentObject<T>) prevalentObject).getOID());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> boolean save(T prevalentObject) {
		if (((PrevalentObject<T>) prevalentObject).getOID() == null)
			((PrevalentObject<T>) prevalentObject).oID = Sequence.getNextOIDFor(prevalentObject.getClass());
		
		addPrevalentObjectToRepository(prevalentObject);
		Command serializeCommand = createSerializeCommand(prevalentObject);
		addCommandToLogList(serializeCommand);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> boolean delete(T prevalentObject, boolean... shouldRaiseException) {
		Long oID = ((PrevalentObject<T>) prevalentObject).getOID();
		if (!raiseOrNotThisIsTheQuestion(oID, prevalentObject.getClass(), shouldRaiseException)) {
			return false;
		}
		deletePrevalentObject(prevalentObject, oID);
		removeCommandFromLogList(prevalentObject);
		return true;
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

	private boolean isPrevalentObjectNotPersistedYet() {
		return this.oID == null;
	}

	protected abstract T whoAmI();
	
	public Long getOID() {
		return oID;
	}
}
