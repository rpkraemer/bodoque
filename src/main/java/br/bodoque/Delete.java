package br.bodoque;

import java.util.ArrayList;
import java.util.List;

public class Delete<T extends Prevalent> {
	
	private Class<T> prevalentObjectClass;
	private Filter<T> filter;
	
	private Delete(Class<T> prevalentObjectClass) {
		this.prevalentObjectClass = prevalentObjectClass;
	}
	
	public static <T extends Prevalent> Delete<T> from(Class<T> prevalentObjectClass) {
		return new Delete<T>(prevalentObjectClass);
	}
	
	public Delete<T> filter(Filter<T> filter) {
		this.filter = filter;
		return this;
	}
	
	public boolean byId(Long id) {
		Repository.deletePrevalentObject(prevalentObjectClass, id);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean all() {
		List<T> allObjects = Find.from(prevalentObjectClass).all();
		List<T> objectsToRemove = (isFilterInformed()) ? applyFilter(allObjects) : allObjects;
		for (T object : objectsToRemove)
			Repository.deletePrevalentObject(prevalentObjectClass, ((PrevalentObject<T>) object).getId());
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean first() {
		long firstId;
		if (isFilterInformed()) {
			firstId = ((PrevalentObject<T>) Find.from(prevalentObjectClass).
					filter(filter).first()).getId();
		} else {
			 firstId = ((PrevalentObject<T>) Find.from(prevalentObjectClass).first()).getId();
		}
		Repository.deletePrevalentObject(prevalentObjectClass, firstId);
		return true;
	}
	
	public boolean first(int numberOfObjects) {
		while (numberOfObjects-- > 0) first();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean last() {
		long lastId;
		if (isFilterInformed()) {
			lastId = ((PrevalentObject<T>) Find.from(prevalentObjectClass).
					filter(filter).last()).getId();
		} else {
			 lastId = ((PrevalentObject<T>) Find.from(prevalentObjectClass).last()).getId();
		}
		Repository.deletePrevalentObject(prevalentObjectClass, lastId);
		return true;
	}
	
	public boolean last(int numberOfObjects) {
		while (numberOfObjects-- > 0) last();
		return true;
	}
	
	private List<T> applyFilter(List<T> allObjects) {
		List<T> filteredObjects = new ArrayList<T>();
		for (T object : allObjects)
			if (filter.accept(object))
				filteredObjects.add(object);
		return filteredObjects;
	}
	
	private boolean isFilterInformed() {
		return this.filter != null;
	}
}
