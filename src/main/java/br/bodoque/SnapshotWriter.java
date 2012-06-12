package br.bodoque;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SnapshotWriter {

	private File snapshotFile;
	private FileWriter fileWriter;
	
	public SnapshotWriter() {
		this.snapshotFile = new File("src/main/resources", "bodoque.bdq");
		createFileWriter();
	}
	
	private void createFileWriter() {
		try {
			this.fileWriter = new FileWriter(snapshotFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToSnapshot(String jsonRepresentation) {
		try {
			fileWriter.write(jsonRepresentation + "\n");
			fileWriter.flush();
		} catch (IOException e) {
			//TODO where is my log?
		} finally {
			try {
				fileWriter.close();
			} catch (Exception e) {
				//TODO where is my log?
			}
		}
	}
}
