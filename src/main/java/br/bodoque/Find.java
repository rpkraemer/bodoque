package br.bodoque;

import java.util.ArrayList;
import java.util.List;

public class Find<T extends Prevalent> {
	
	private Class<? extends Prevalent> prevalentObjectClass;
	private Filter<T> filter;
	
	private Find(Class<? extends Prevalent> prevalentObjectClass) {
		this.prevalentObjectClass = prevalentObjectClass;
	}
	
	public static <T extends Prevalent> Find<T> from(Class<T> prevalentObjectClass) {
		return new Find<T>(prevalentObjectClass);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> all() {
		List<T> allObjects = (List<T>) Repository.getListFor(this.prevalentObjectClass);
		return (isFilterInformed()) ? applyFilter(allObjects) : allObjects;
	}
	
	private boolean isFilterInformed() {
		return this.filter != null;
	}

	private List<T> applyFilter(List<T> allObjects) {
		List<T> filteredObjects = new ArrayList<T>();
		for (T object : allObjects)
			if (filter.accept(object))
				filteredObjects.add(object);
		return filteredObjects;
	}
	
	public Find<T> filter(Filter<T> filter) {
		this.filter = filter;
		return this;
	}
	
	public T byId(Long id) {
		return Repository.getPrevalentObjectFromRepository(prevalentObjectClass, id);
	}

	@SuppressWarnings("unchecked")
	public T first() {
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		return (isFilterInformed()) ? applyFilter(allObjects).get(0) : allObjects.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public T last() {
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		int lastObject = allObjects.size() - 1;
		if (isFilterInformed()) {
			List<T> filteredObjects = applyFilter(allObjects);
			lastObject = filteredObjects.size() - 1;
			return filteredObjects.get(lastObject);
		} else {
			return allObjects.get(lastObject);
		}
	}

	@SuppressWarnings("unchecked")
	public long count() {
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		return (isFilterInformed()) ? applyFilter(allObjects).size() : allObjects.size();
	}
}
