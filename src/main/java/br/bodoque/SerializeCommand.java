package br.bodoque;

public class SerializeCommand<T extends Prevalent> implements Command {

	private Long oID;
	private T prevalentObject;
	
	public SerializeCommand(Long oID, T prevalentObject) {
		this.oID = oID;
		this.prevalentObject = prevalentObject;
	}

	@Override
	public void execute() {
		Repository.addPrevalentObject(prevalentObject, oID);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return ((obj instanceof Command) && 
				((SerializeCommand<T>) obj).oID == this.oID);
	}
}
