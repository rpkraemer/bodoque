package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Dog;
import helpers.Person;
import org.junit.Before;
import org.junit.Test;
import br.bodoque.CommandLogList;

public class SerializeCommandTest extends GlobalSetUp {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldCreateTwoSerializeCommandLog() {
		Person person = new Person("Pessoa", 30);
		Dog dog = new Dog("Dog");
		person.save();
		dog.save();
		
		assertEquals(2, CommandLogList.getLogList().size());
	}
}
