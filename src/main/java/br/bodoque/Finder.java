package br.bodoque;

import java.util.ArrayList;
import java.util.List;

public class Finder<T extends Prevalent> {
	
	private Class<? extends Prevalent> prevalentObjectClass;
	
	public Finder(Class<? extends Prevalent> prevalentObjectClass) {
		this.prevalentObjectClass = prevalentObjectClass;
	}
	
	public static <T extends Prevalent> Finder<T> from(Class<T> prevalentObjectClass) {
		return new Finder<T>(prevalentObjectClass);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		List<T> allObjects = (List<T>) Repository.getListFor(this.prevalentObjectClass);
		return allObjects;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(Filter<T> filter) {
		List<T> objects = (List<T>) Repository.getListFor(prevalentObjectClass);
		List<T> filtered = new ArrayList<T>();
		for (T obj : objects) {
			if (filter.accept(obj)) {
				filtered.add(obj);
			}
		}
		return filtered;
	}
	
	public T getByOID(Long oID) {
		return Repository.getPrevalentObjectFromRepository(prevalentObjectClass, oID);
	}
}
