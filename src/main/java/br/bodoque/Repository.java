package br.bodoque;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rits.cloning.Cloner;

public class Repository {

	private static Map<Class<? extends Prevalent>, Map<Long, Prevalent>> repository;
	private static Cloner cloner = new Cloner();
	
	static {
		repository = new HashMap<Class<? extends Prevalent>, Map<Long, Prevalent>>();
	}
	
	static Map<Class<? extends Prevalent>, Map<Long, Prevalent>> getRepository() {
		return Collections.unmodifiableMap(repository);
	}
	
	static <T extends Prevalent> void addPrevalentObject(T prevalentObject, Long oID) {
		Map<Long, Prevalent> prevalentObjectMap = repository.get(prevalentObject.getClass());
		
		if (prevalentObjectMap == null)
			prevalentObjectMap = new HashMap<Long, Prevalent>();
		
		prevalentObjectMap.put(oID, prevalentObject);
		repository.put(prevalentObject.getClass(), prevalentObjectMap);
	}
	
	static <T extends Prevalent> void deletePrevalentObject(Class<? extends Prevalent> prevalentObjectClass, Long id){
		Map<Long, Prevalent> prevalentObjectMap = repository.get(prevalentObjectClass);
		if (prevalentObjectMap == null) { return; }
		prevalentObjectMap.remove(id);
	}
	
	static <T extends Prevalent> void deletePrevalentObjectsFrom(Class<? extends Prevalent> prevalentObjectClass) {
		repository.remove(prevalentObjectClass);
	}
	
	static void clearRepositoryFor(Class<? extends Prevalent> prevalentObjectClass) {
		repository.remove(prevalentObjectClass);
	}

	@SuppressWarnings("unchecked")
	static <T extends Prevalent> List<T> getListFor(Class<T> prevalentObjectClass) {
		Map<Long, T> prevalentObjectMap = (Map<Long, T>) repository.get(prevalentObjectClass);
		return (List<T>) ((prevalentObjectMap != null) ? 
				new ArrayList<T>(prevalentObjectMap.values()) : Collections.emptyList());
	}

	static void clearRepository() {
		repository.clear();
	}

	@SuppressWarnings("unchecked")
	static <T extends Prevalent> T getPrevalentObject(
			  Class<? extends Prevalent> prevalentObjectClass, Long id) {
	       return (T) cloner.deepClone(repository.get(prevalentObjectClass).get(id));
	}
}