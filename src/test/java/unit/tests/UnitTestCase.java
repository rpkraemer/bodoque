package unit.tests;

import helpers.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;

import br.bodoque.Bodoque;

public class UnitTestCase {

	private int numberOfRecords;
	private File snapshot = new File("src/test/resources", "bodoque.bdq");
	
	protected void setUp() {
		Bodoque.initialize();
	}
	
	protected void assertHasSnapshotFile() {
		waitForLogListDaemon();
		if (!snapshot.exists())
			Assert.fail();
		Assert.assertTrue(true);
	}
	
	protected UnitTestCase assertSnapshotFileHas(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
		return this;
	}
	
	public void records() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(snapshot));
			int records = 0;
			while (reader.readLine() != null) records++;
			Assert.assertEquals(numberOfRecords, records);
		} catch (IOException e) {}
	}

	public void waitForLogListDaemon() {
		try {
			Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {}
	}
	
	public void excludeSnapshotFile() {
		File snapshot = new File("src/test/resources", "bodoque.bdq");
		snapshot.delete();
	}
	
	public Person createAPerson(int idade) {
		return new Person("Pessoa", idade);
	}
	
}
