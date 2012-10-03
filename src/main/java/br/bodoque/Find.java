package br.bodoque;

import java.util.ArrayList;
import java.util.List;

public class Find<T extends Prevalent> {
	
	private Class<? extends Prevalent> prevalentObjectClass;
	
	private Find(Class<? extends Prevalent> prevalentObjectClass) {
		this.prevalentObjectClass = prevalentObjectClass;
	}
	
	public static <T extends Prevalent> Find<T> from(Class<T> prevalentObjectClass) {
		return new Find<T>(prevalentObjectClass);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> all() {
		List<T> allObjects = (List<T>) Repository.getListFor(this.prevalentObjectClass);
		return allObjects;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> all(Filter<T> filter) {
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		List<T> filteredObjects = new ArrayList<T>();
		for (T object : allObjects) {
			if (filter.accept(object)) {
				filteredObjects.add(object);
			}
		}
		return filteredObjects;
	}
	
	public T byId(Long id) {
		return Repository.getPrevalentObjectFromRepository(prevalentObjectClass, id);
	}
}
