package unit.tests;

import java.io.File;

import org.junit.Assert;

import br.bodoque.Bodoque;

public class UnitTestCase {

	protected void setUp() {
		Bodoque.initialize();
	}
	
	protected void assertHasSnapshotFile() {
		try {
			Thread.sleep(15 * 1000);
		} catch (InterruptedException e) {}
		
		File snapshot = new File("src/test/resources", "bodoque.bdq");
		if (!snapshot.exists())
			Assert.fail();
		Assert.assertTrue(true);
	}
	
	protected void excludeSnapshotFile() {
		File snapshot = new File("src/test/resources", "bodoque.bdq");
		snapshot.delete();
	}
}
