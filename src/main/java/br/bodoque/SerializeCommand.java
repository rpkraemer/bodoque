package br.bodoque;

import flexjson.JSONSerializer;


public class SerializeCommand<T extends Prevalent> implements Command {

	private T prevalentObject;
	private String prevalentObjectJSONRepresentation;
	private boolean isRemovable;
	
	public SerializeCommand(T prevalentObject) {
		this.prevalentObject = prevalentObject;
		this.isRemovable = false;
	}

	@Override
	public void execute() {
		generateJSONRepresentation();
	}
	
	private void generateJSONRepresentation() {
		JSONSerializer serializer = new JSONSerializer();
		this.prevalentObjectJSONRepresentation = 
			serializer.include("*").serialize(prevalentObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SerializeCommand)) 
			return false;
		
		SerializeCommand<T> objSerializeCommand = (SerializeCommand<T>) obj;
		PrevalentObject<T> objPrevalentObject = (PrevalentObject<T>) objSerializeCommand.prevalentObject;
		PrevalentObject<T> thisPrevalentObject = (PrevalentObject<T>) this.prevalentObject;
		
		return thisPrevalentObject.getClass() == objPrevalentObject.getClass() &&
			   thisPrevalentObject.getOID().equals(objPrevalentObject.getOID());
	}

	public void setRemovable(boolean isRemovable) {
		this.isRemovable = isRemovable;
	}

	public String getJSONRepresentation() {
		return this.prevalentObjectJSONRepresentation;
	}

	@Override
	public boolean isRemovable() {
		return this.isRemovable;
	}
}
