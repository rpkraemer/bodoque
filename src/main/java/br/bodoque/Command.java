package br.bodoque;

public interface Command {

	/**
	 * Method containing Command logic
	 */
	void execute();
	
	/**
	 * Determine if command can be removed
	 * @return true if the command is liable to be removed. false otherwise
	 */
	boolean isRemovable();
	
	/**
	 * 
	 * @param isRemovable
	 */
	void setRemovable(boolean isRemovable);
}
