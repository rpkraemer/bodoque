package br.bodoque;

import com.rits.cloning.Cloner;

public abstract class PrevalentObject<T extends Prevalent> implements Prevalent {
	
	private Long id;
	private static Cloner cloner = new Cloner();
	
	public boolean save() {
		if (isPrevalentObjectNotPersistedYet())
			generateIdForThisPrevalentObject();
		addPrevalentObjectToRepository(this);
		return true;
	}
	
	public boolean delete() {
		if (this.id == null) return false;
		deletePrevalentObject(this, this.id);
		return true;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Prevalent> void addPrevalentObjectToRepository(T prevalentObject) {
		prevalentObject = cloner.deepClone(prevalentObject); // clone object to another reference
		Repository.addPrevalentObject(prevalentObject, 
				((PrevalentObject<T>) prevalentObject).getId());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> boolean save(T ...prevalentObject) {
		for (T object : prevalentObject) {
			if (((PrevalentObject<T>) object).getId() == null)
				((PrevalentObject<T>) object).id = Sequence.getNextIdFor(object.getClass());
			
			addPrevalentObjectToRepository(object);
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> boolean delete(T prevalentObject) {
		Long id = ((PrevalentObject<T>) prevalentObject).getId();
		if (id == null) return false;
		deletePrevalentObject(prevalentObject, id);
		return true;
	}

	private static <T extends Prevalent> void deletePrevalentObject(T prevalentObject, Long id) {
		Repository.deletePrevalentObject(prevalentObject.getClass(), id);
	}

	private void generateIdForThisPrevalentObject() {
		this.id = Sequence.getNextIdFor(this.getClass());
	}

	private boolean isPrevalentObjectNotPersistedYet() {
		return this.id == null;
	}

	//protected abstract T whoAmI();
	
	public Long getId() {
		return id;
	}
}
