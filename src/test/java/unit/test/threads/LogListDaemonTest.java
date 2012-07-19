package unit.test.threads;

import helpers.Person;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import unit.tests.UnitTestCase;
import br.bodoque.Command;
import br.bodoque.CommandLogList;
import br.bodoque.Prevalent;
import br.bodoque.Repository;
import br.bodoque.SerializeCommand;

public class LogListDaemonTest extends UnitTestCase {
	
	
	@SuppressWarnings("unchecked")
	@Ignore
	public void shouldGenerateAndUpdateToAValidJSONRepresentation() {
		Person person = createAPerson(30);
		person.save();
		getAndVerifyCommand();
		
		person.setAge(22);
		person.setName("John Lennon");
		Person.save(person);
		
		waitForLogListDaemon(); //until update json representation
		
		SerializeCommand<Prevalent> command = 
			(SerializeCommand<Prevalent>) CommandLogList.getLogList().get(0);
		
		Assert.assertNotNull(command);
		Assert.assertEquals(1, CommandLogList.getLogList().size());
		Assert.assertEquals("{\"OID\":1,\"age\":22,\"class\":\"helpers.Person\"," +
	            "\"name\":\"John Lennon\"}", command.getJSONRepresentation());
	}
	
	@Ignore
	public void shouldGenerateAValidJSONRepresentation() {
		Person person = createAPerson(30);
		person.save();
		getAndVerifyCommand();
	}
	
	@Ignore
	public void shouldRemoveCommandsWhenLogListIsBig() {
		for (int i = 0; i < 2000; i++) {
			Person person = createAPerson(30);
			person.save();
		}
		
		Assert.assertEquals(2000, CommandLogList.getLogList().size());
		waitForLogListDaemon(); //thread should clean the big list
		Assert.assertEquals(0, CommandLogList.getLogList().size());
		assertHasSnapshotFile();
		assertSnapshotFileHas(2000).records();
	}
	
	
	@Ignore
	public void shouldWriteTwoJSONObjectsLogToSnapshotFile() {
		excludeSnapshotFile();

		Person person = createAPerson(30);
		Person anotherPerson = new Person("robson", 21);
		person.save();
		anotherPerson.save();
		
		Assert.assertEquals(2, Repository.getRepository().get(Person.class).size());
		assertHasSnapshotFile();
		assertSnapshotFileHas(2).records();
	}
	
	@Ignore
	public void shouldReturnFalseInEqualsComparation() {
		Command validCommand = new SerializeCommand<Prevalent>(createAPerson(30));
		String invalidCommand = "invalidCommand";
		Integer otherInvalidCommand = 10;
		
		Assert.assertFalse(validCommand.equals(invalidCommand));
		Assert.assertFalse(invalidCommand.equals(validCommand));
		Assert.assertFalse(validCommand.equals(otherInvalidCommand));
	}

	@Ignore
	public void shouldWriteJSONObjectLogToSnapshotFile() {
		excludeSnapshotFile();

		Person person = createAPerson(30);
		person.save();
		
		assertHasSnapshotFile();
	}

	@SuppressWarnings("unchecked")
	private void getAndVerifyCommand() {
		waitForLogListDaemon();
		SerializeCommand<Prevalent> command = 
			(SerializeCommand<Prevalent>) CommandLogList.getLogList().get(0);
		
		Assert.assertNotNull(command);
		Assert.assertEquals("{\"OID\":1,\"age\":30,\"class\":\"helpers.Person\"," +
				            "\"name\":\"Pessoa\"}", command.getJSONRepresentation());
	}
	
}