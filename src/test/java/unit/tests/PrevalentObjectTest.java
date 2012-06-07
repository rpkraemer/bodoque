package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Person;
import org.junit.Before;
import org.junit.Test;
import br.bodoque.CommandLogList;
import br.bodoque.Repository;
import br.bodoque.Sequence;

public class PrevalentObjectTest {

	@Before
	public void setUp() {
		CommandLogList.clearLogList();
		Repository.clearRepositoryFor(Person.class);
		Sequence.clearSequenceFor(Person.class);
	}
	
	@Test
	public void shouldCreateASerializeCommandAndInsertOnLogListWhenSaveIsInvoked() {
		Person person = createAPerson();
		person.save();
		assertEquals(1, CommandLogList.getLogList().size());
		assertEquals("Pessoa", Repository.getMapFor(Person.class).get(1L).getName());
		assertEquals(1L, person.getOID().longValue());
	}
	
	@Test
	public void shouldMaintainOneCommandOnLogListWhenObjectStateIsUpdated() {
		Person person = createAPerson();
		person.save();
		assertEquals(1, CommandLogList.getLogList().size());
		assertEquals("Pessoa", Repository.getMapFor(Person.class).get(1L).getName());
		assertEquals(1L, person.getOID().longValue());
		
		person.setName("New name");
		person.save();
		assertEquals(1, CommandLogList.getLogList().size());
		assertEquals("New name", Repository.getMapFor(Person.class).get(1L).getName());
		assertEquals(1L, person.getOID().longValue());
	}

	private Person createAPerson() {
		Person person = new Person("Pessoa", 40);
		return person;
	}
}
