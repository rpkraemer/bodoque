package br.bodoque;

import com.rits.cloning.Cloner;

public abstract class PrevalentObject<T extends Prevalent> implements Prevalent {
	
	private T prevalentObject;
	private Long id;
	private static Cloner cloner = new Cloner();
	
	public boolean save() {
		prevalentObject = whoAmI();
		if (isPrevalentObjectNotPersistedYet())
			generateIdForThisPrevalentObject();
		addPrevalentObjectToRepository(prevalentObject);
		return true;
	}
	
	public boolean delete() {
		prevalentObject = whoAmI();
		if (this.id == null) return false;
		deletePrevalentObject(prevalentObject, this.id);
		return true;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Prevalent> void addPrevalentObjectToRepository(T prevalentObject) {
		prevalentObject = cloner.deepClone(prevalentObject); // clone object to another reference
		Repository.addPrevalentObject(prevalentObject, 
				((PrevalentObject<T>) prevalentObject).getId());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> boolean save(T prevalentObject) {
		if (((PrevalentObject<T>) prevalentObject).getId() == null)
			((PrevalentObject<T>) prevalentObject).id = Sequence.getNextIdFor(prevalentObject.getClass());
		
		addPrevalentObjectToRepository(prevalentObject);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> boolean delete(T prevalentObject) {
		Long id = ((PrevalentObject<T>) prevalentObject).getId();
		if (id == null) return false;
		deletePrevalentObject(prevalentObject, id);
		return true;
	}

	private static <T extends Prevalent> void deletePrevalentObject(T prevalentObject, Long oId) {
		Repository.deletePrevalentObject(prevalentObject, oId);
	}

	private void generateIdForThisPrevalentObject() {
		this.id = Sequence.getNextIdFor(prevalentObject.getClass());
	}

	private boolean isPrevalentObjectNotPersistedYet() {
		return this.id == null;
	}

	protected abstract T whoAmI();
	
	public Long getId() {
		return id;
	}
}
