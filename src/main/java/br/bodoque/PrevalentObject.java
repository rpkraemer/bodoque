package br.bodoque;

public abstract class PrevalentObject<T extends Prevalent> implements Prevalent {
	
	private T prevalentObject;
	private Long oID;

	public boolean save() {
		prevalentObject = whoAmI();
		
		if (isPrevalentObjectNotPersistedYet())
			generateOIDForThisPrevalentObject();
		
		addPrevalentObjectToRepository(prevalentObject);
		return true;
	}
	
	public boolean delete(boolean... shouldRaiseException) {
		prevalentObject = whoAmI();
		if (!raiseOrNotThisIsTheQuestion(this.getOID(), prevalentObject.getClass(), 
										 shouldRaiseException)) {
			return false;
		}
		deletePrevalentObject(prevalentObject, this.oID);
		return true;
	}

	private static boolean raiseOrNotThisIsTheQuestion(Long oID, Class<? extends Prevalent> 
													prevalentObjectClass, boolean... shouldRaiseException) {
		if(oID == null) { 
			if (shouldRaiseException.length > 0 && shouldRaiseException[0]) {
				System.out.println(1);
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
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> boolean delete(T prevalentObject, boolean... shouldRaiseException) {
		Long oID = ((PrevalentObject<T>) prevalentObject).getOID();
		if (!raiseOrNotThisIsTheQuestion(oID, prevalentObject.getClass(), shouldRaiseException)) {
			return false;
		}
		deletePrevalentObject(prevalentObject, oID);
		return true;
	}

	private static <T extends Prevalent> void deletePrevalentObject(T prevalentObject, Long oId) {
		Repository.deletePrevalentObject(prevalentObject, oId);
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
