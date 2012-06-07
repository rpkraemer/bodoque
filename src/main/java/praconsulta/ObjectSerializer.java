package praconsulta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class ObjectSerializer<T> {

	private JSONSerializer serializer;
	private JSONDeserializer<T> deserializer;
	
	public ObjectSerializer() {
		this.serializer = new JSONSerializer();
		this.deserializer = new JSONDeserializer<T>();
	}
	
	public void serialize(T object) {
		String json = serializer.include("*").serialize(object);
		File objectsFile = new File("C:/workspaces/prevalencia", 
									object.getClass().getSimpleName() + ".objects");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(objectsFile, true));
			writer.append(json);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deserialize(T object) {
		
	}
	
}
