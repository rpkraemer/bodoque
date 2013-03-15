package br.bodoque;

import java.util.ArrayList;
import java.util.List;

public class Find<T extends Prevalent> {
	
	private Class<T> prevalentObjectClass;
	private Filter<T> filter;
	
	private Find(Class<T> prevalentObjectClass) {
		this.prevalentObjectClass = prevalentObjectClass;
	}
	
	public static <T extends Prevalent> Find<T> from(Class<T> prevalentObjectClass) {
		return new Find<T>(prevalentObjectClass);
	}
	
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
		return Repository.getPrevalentObject(prevalentObjectClass, id);
	}

	public T first() {
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		return (isFilterInformed()) ? applyFilter(allObjects).get(0) : allObjects.get(0);
	}
	
	public List<T> first(int numberOfObjects) {
		List<T> firstObjects = new ArrayList<T>();
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		if (isFilterInformed()) {
			List<T> filteredObjects = applyFilter(allObjects);
			for (int i = 0; i < numberOfObjects && i < filteredObjects.size(); i++)
				firstObjects.add(filteredObjects.get(i));
		} else {
			for (int i = 0; i < numberOfObjects && i < allObjects.size(); i++)
				firstObjects.add(allObjects.get(i));
		}
		return firstObjects;
	}
	
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
	
	public List<T> last(int numberOfObjects) {
		List<T> lastObjects = new ArrayList<T>();
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		if (isFilterInformed()) {
			List<T> filteredObjects = applyFilter(allObjects);
			for (int i = 0, j = filteredObjects.size() - 1; 
				     i < numberOfObjects && i < filteredObjects.size(); i++, j--)
				lastObjects.add(filteredObjects.get(j));
		} else {
			for (int i = 0, j = allObjects.size() - 1; 
					 i < numberOfObjects && i < allObjects.size(); i++, j--)
				lastObjects.add(allObjects.get(j));
		}
		return lastObjects;
	}

	public long count() {
		List<T> allObjects = (List<T>) Repository.getListFor(prevalentObjectClass);
		return (isFilterInformed()) ? applyFilter(allObjects).size() : allObjects.size();
	}
}