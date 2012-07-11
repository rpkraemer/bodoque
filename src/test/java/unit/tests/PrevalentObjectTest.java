package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Person;

import org.junit.Before;
import org.junit.Test;

import br.bodoque.CannotRemovePrevalentObjectException;
import br.bodoque.CommandLogList;
import br.bodoque.Repository;

public class PrevalentObjectTest extends UnitTestCase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
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
	public void shouldRemoveAPersonFromPeopleRepositoryAndCommandLogList(){
		Person person = createAPerson();
		person.save();
		assertEquals(1, Repository.getMapFor(Person.class).size());
		person.delete();
		assertEquals(0, Repository.getMapFor(Person.class).size());
		assertEquals(0, CommandLogList.getLogList().size());
	}
	
	@Test(expected = CannotRemovePrevalentObjectException.class)
	public void shouldRaiseExceptionWhenRemoveAPersonNotSavedYet(){
		Person person = createAPerson();
		person.delete(true);
	}
	
	@Test
	public void shouldNotRaiseExceptionWhenRemoveAPersonNotSavedYet(){
		Person person = createAPerson();
		person.delete();
	}
	
	@Test
	public void shouldRemoveAPersonFromPeopleRepositoryAndCommandLogListStaticWay(){
		Person person = createAPerson();
		person.save();
		assertEquals(1, Repository.getMapFor(Person.class).size());
		Person.delete(person);
		assertEquals(0, Repository.getMapFor(Person.class).size());
		assertEquals(0, CommandLogList.getLogList().size());
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

}
