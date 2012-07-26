package br.bodoque;

public class CannotDeletePrevalentObjectException extends RuntimeException {

	private static final long serialVersionUID = 4321686471886059033L;
	
	public CannotDeletePrevalentObjectException(String message){
		super(message);
	}
	
}
