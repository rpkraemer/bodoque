package br.bodoque;

public interface Filter<T> {

	boolean accept(T obj);
	
}
