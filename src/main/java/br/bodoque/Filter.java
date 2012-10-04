package br.bodoque;

public interface Filter<T extends Prevalent> {

	boolean accept(final T obj);
	
}
