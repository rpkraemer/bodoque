package br.bodoque;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SnapshotWriter {

	private File snapshotFile;
	private FileWriter fileWriter;
	
	public SnapshotWriter() {
		this.snapshotFile = new File("src/test/resources", "bodoque.bdq");
		createFileWriter();
	}
	
	private void createFileWriter() {
		try {
			this.fileWriter = new FileWriter(snapshotFile, true);
		} catch (IOException e) {
			//TODO where is my logger?
		}
	}
	
	public void writeToSnapshot(String jsonRepresentation) throws IOException {
		try {
			fileWriter.write(jsonRepresentation + "\n");
			fileWriter.flush();
		} finally {
			fileWriter.close();
		}
	}
}
