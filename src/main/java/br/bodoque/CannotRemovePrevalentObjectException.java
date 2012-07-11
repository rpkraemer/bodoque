package br.bodoque;

public class CannotRemovePrevalentObjectException extends RuntimeException {

	private static final long serialVersionUID = 4321686471886059033L;
	
	public CannotRemovePrevalentObjectException(String message){
		super(message);
	}
	
}
