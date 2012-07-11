package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Dog;
import helpers.Person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.bodoque.Command;
import br.bodoque.CommandLogList;
import br.bodoque.Prevalent;
import br.bodoque.Repository;
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
	
}
