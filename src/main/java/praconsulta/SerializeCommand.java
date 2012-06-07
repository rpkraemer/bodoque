package praconsulta;

public class SerializeCommand<T> implements Command<T> {

	private ObjectSerializer<T> objectSerializer;
	private T prevalentObject;
	
	public SerializeCommand(T prevalentObject) {
		this.prevalentObject = prevalentObject;
	}
	
	@Override
	public void execute() {
		objectSerializer = new ObjectSerializer<T>();
		objectSerializer.serialize(prevalentObject);
	}

}
