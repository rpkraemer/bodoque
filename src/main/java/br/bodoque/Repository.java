package br.bodoque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

	private static Map<Class<? extends Prevalent>, Map<Long, Prevalent>> repository;
	
	static {
		repository = new HashMap<Class<? extends Prevalent>, Map<Long, Prevalent>>();
	}
	
	public static Map<Class<? extends Prevalent>, Map<Long, Prevalent>> getRepository() {
		return repository;
	}
	
	public synchronized static <T extends Prevalent> void addPrevalentObject(T prevalentObject, Long oID) {
		Map<Long, Prevalent> prevalentObjectMap = repository.get(prevalentObject.getClass());
		
		if (prevalentObjectMap == null)
			prevalentObjectMap = new HashMap<Long, Prevalent>();
		
		prevalentObjectMap.put(oID, prevalentObject);
		repository.put(prevalentObject.getClass(), prevalentObjectMap);
	}

	public synchronized static void clearRepositoryFor(Class<? extends Prevalent> prevalentObjectClass) {
		repository.remove(prevalentObjectClass);
	}

	@SuppressWarnings("unchecked")
	public synchronized static <T extends Prevalent> List<T> getListFor(Class<T> prevalentObjectClass) {
		Map<Long, T> prevalentObjectMap = (Map<Long, T>) repository.get(prevalentObjectClass);
		return new ArrayList<T>(prevalentObjectMap.values());
	}

	@SuppressWarnings("unchecked")
	public synchronized static <T extends Prevalent> Map<Long, T> getMapFor(Class<T> prevalentObjectClass) {
		return (Map<Long, T>) repository.get(prevalentObjectClass);
	}

	public synchronized static void clearRepository() {
		repository.clear();
	}
}
