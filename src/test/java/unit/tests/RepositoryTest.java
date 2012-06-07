package unit.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import helpers.Person;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.bodoque.Repository;


public class RepositoryTest extends GlobalSetUp {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldRepositoryMapBeEmptyOnStart() {
		assertNotNull(Repository.getRepository());
		assertEquals(0, Repository.getRepository().size());
	}
	
	@Test
	public void shouldCreateNewMapWhenNotExists() {
		Person person = createEdipoPerson();
		Repository.addPrevalentObject(person, 1L);
		
		assertEquals(1, Repository.getRepository().size());
		assertSame(person, Repository.getRepository().get(Person.class).get(1L));
	}
	
	@Test
	public void shouldNotCreateANewMapWhenAlreadyExists() {
		Person edipo = createEdipoPerson();
		Person robson = createRobsonPerson();
		insertPeopleOnRepositoryMap(edipo, robson);
		
		assertEquals(1, Repository.getRepository().size());
		assertEquals(2, Repository.getRepository().get(Person.class).size());
	}

	private Person createRobsonPerson() {
		Person person = new Person("Robson", 21);
		return person;
	}
	
	private Person createEdipoPerson() {
		Person person = new Person("Edipo", 23);
		return person;
	}
	
	private void insertPeopleOnRepositoryMap(Person... people) {
		Long oID = 1L;
		for (Person person : people) {
			Repository.addPrevalentObject(person, oID);
			oID++;
		}
	}
	
	@Test
	public void shouldReturnCorrectObjectsGivenOIDs() {
		Person edipo = createEdipoPerson();
		Person robson = createRobsonPerson();
		insertPeopleOnRepositoryMap(edipo, robson);
		
		assertEquals(edipo.getName(), ((Person) Repository.getRepository().get(Person.class).get(1L)).getName());
		assertEquals(robson.getName(), ((Person) Repository.getRepository().get(Person.class).get(2L)).getName());
	}

	@Test
	public void shouldReturnAListOfExistingPeople() {
		Person edipo = createEdipoPerson();
		Person robson = createRobsonPerson();
		insertPeopleOnRepositoryMap(edipo, robson);
		
		List<Person> people = Repository.getListFor(Person.class);
		assertFalse(people.isEmpty());
		assertEquals(2, people.size());
	}

	@Test
	public void shouldReturnAMapWithCorrectObjectsGivenOIDs() {
		Person edipo = createEdipoPerson();
		Person robson = createRobsonPerson();
		insertPeopleOnRepositoryMap(edipo, robson);
		
		Map<Long, Person> people = Repository.getMapFor(Person.class);
		
		assertEquals("Robson", people.get(2L).getName());
		assertEquals(21, people.get(2L).getAge());
		assertEquals("Edipo", people.get(1L).getName());
		assertEquals(23, people.get(1L).getAge());
	}
		
}
