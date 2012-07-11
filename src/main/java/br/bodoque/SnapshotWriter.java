package br.bodoque;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SnapshotWriter {

	private File snapshotFile;
	private FileWriter fileWriter;
	
	public SnapshotWriter() {
		this.snapshotFile = new File("src/test/resources", "bodoque.bdq");
		deleteCurrentSnapshotFile();
		createNewSnapshotFile();
	}
	
	private void deleteCurrentSnapshotFile() {
		this.snapshotFile.delete();
	}

	private void createNewSnapshotFile() {
		try {
			this.fileWriter = new FileWriter(snapshotFile, true);
		} catch (IOException e) {
			//TODO where is my logger?
		}
	}
	
	public void writeToSnapshot(String jsonRepresentation) throws IOException {
		fileWriter.write(jsonRepresentation + "\n");
	}

	public void flushAndClose() {
		try {
			this.fileWriter.flush();
			this.fileWriter.close();
		} catch (IOException e) {
			//TODO where is my logger?
		}
	}
}
