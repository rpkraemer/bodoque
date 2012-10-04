package br.bodoque;

import java.util.ArrayList;
import java.util.Collections;
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
	
	public static <T extends Prevalent> void addPrevalentObject(T prevalentObject, Long oID) {
		Map<Long, Prevalent> prevalentObjectMap = repository.get(prevalentObject.getClass());
		
		if (prevalentObjectMap == null)
			prevalentObjectMap = new HashMap<Long, Prevalent>();
		
		prevalentObjectMap.put(oID, prevalentObject);
		repository.put(prevalentObject.getClass(), prevalentObjectMap);
	}
	
	public static <T extends Prevalent> void deletePrevalentObject(Class<? extends Prevalent> prevalentObjectClass, Long id){
		Map<Long, Prevalent> prevalentObjectMap = repository.get(prevalentObjectClass);
		if (prevalentObjectMap == null) { return; }
		prevalentObjectMap.remove(id);
	}
	
	public static <T extends Prevalent> void deletePrevalentObjectsFrom(Class<? extends Prevalent> prevalentObjectClass) {
		repository.remove(prevalentObjectClass);
	}
	
	public static void clearRepositoryFor(Class<? extends Prevalent> prevalentObjectClass) {
		repository.remove(prevalentObjectClass);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> List<T> getListFor(Class<T> prevalentObjectClass) {
		Map<Long, T> prevalentObjectMap = (Map<Long, T>) repository.get(prevalentObjectClass);
		return (List<T>) ((prevalentObjectMap != null) ? 
				new ArrayList<T>(prevalentObjectMap.values()) : Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> Map<Long, T> getMapFor(Class<T> prevalentObjectClass) {
		return (Map<Long, T>) repository.get(prevalentObjectClass);
	}

	public static void clearRepository() {
		repository.clear();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Prevalent> T getPrevalentObjectFromRepository(
			Class<? extends Prevalent> prevalentObjectClass, Long id) {
		return (T) repository.get(prevalentObjectClass).get(id);
	}
}