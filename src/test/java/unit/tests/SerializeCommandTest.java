package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Dog;
import helpers.Person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.bodoque.CommandLogList;
import br.bodoque.Prevalent;
import br.bodoque.SerializeCommand;

public class SerializeCommandTest extends UnitTestCase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldCreateTwoSerializeCommandLog() {
		Person person = createAPerson();
		Dog dog = createADog();
		person.save();
		dog.save();
		
		assertEquals(2, CommandLogList.getLogList().size());
	}

	private Dog createADog() {
		return new Dog("Dog");
	}

	private Person createAPerson() {
		return new Person("Pessoa", 30);
	}
	
	@Test
	public void shouldGenerateAValidJSONRepresentation() {
		Person person = createAPerson();
		person.save();
		getAndVerifyCommand();
	}

	
	@SuppressWarnings("unchecked")
	private void getAndVerifyCommand() {
		SerializeCommand<Prevalent> command = 
			(SerializeCommand<Prevalent>) CommandLogList.getLogList().get(0);
		
		Assert.assertNotNull(command);
		Assert.assertEquals("{\"OID\":1,\"age\":30,\"class\":\"helpers.Person\"," +
				            "\"name\":\"Pessoa\"}", command.getJSONRepresentation());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldGenerateAndUpdateToAValidJSONRepresentation() {
		Person person = createAPerson();
		person.save();
		getAndVerifyCommand();
		
		person.setAge(22);
		person.setName("John Lennon");
		Person.save(person);
		
		SerializeCommand<Prevalent> command = 
			(SerializeCommand<Prevalent>) CommandLogList.getLogList().get(0);
		
		Assert.assertNotNull(command);
		Assert.assertEquals(1, CommandLogList.getLogList().size());
		Assert.assertEquals("{\"OID\":1,\"age\":22,\"class\":\"helpers.Person\"," +
	            "\"name\":\"John Lennon\"}", command.getJSONRepresentation());
	}
	
	@Test
	public void shouldWriteJSONToDisk() {
		excludeSnapshotFile();

		Person person = createAPerson();
		person.save();
		
		assertHasSnapshotFile();
	}
}
