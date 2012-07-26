package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Customer;
import helpers.Person;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import br.bodoque.CannotDeletePrevalentObjectException;
import br.bodoque.Filter;
import br.bodoque.Find;
import br.bodoque.Repository;

public class PrevalentObjectTest extends UnitTestCase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldRemoveAPersonFromPeopleRepository(){
		Person person = givenAPerson(30);
		person.save();
		assertEquals(1, Repository.getMapFor(Person.class).size());
		person.delete();
		assertEquals(0, Repository.getMapFor(Person.class).size());
	}
	
	@Test(expected = CannotDeletePrevalentObjectException.class)
	public void shouldRaiseExceptionWhenRemoveAPersonNotSavedYet(){
		Person person = givenAPerson(30);
		person.delete(true);
	}
	
	@Test
	public void shouldNotRaiseExceptionWhenRemoveAPersonNotSavedYet(){
		Person person = givenAPerson(30);
		person.delete();
	}
	
	@Test
	public void shouldRemoveAPersonFromPeopleRepositoryStaticWay(){
		Person person = givenAPerson(30);
		person.save();
		assertEquals(1, Repository.getMapFor(Person.class).size());
		Person.delete(person);
		assertEquals(0, Repository.getMapFor(Person.class).size());
	}
	
	@Test
	public void shouldReturnAListOfSavedPeople() {
		Person p1 = givenAPerson(30);
		Person p2 = givenAPerson(30);
		p1.save();
		Person.save(p2);
		
		List<Person> people = Find.from(Person.class).all();
		Assert.assertNotNull(people);
		Assert.assertEquals(2, people.size());
	}
	
	@Test
	public void shouldReturnAListOfSavedPeople2() {
		Person p1 = givenAPerson(30);
		Person p2 = givenAPerson(30);
		p1.save();
		Person.save(p2);
		
		List<Person> people = Find.from(Person.class).all();
		Assert.assertNotNull(people);
		Assert.assertEquals(2, people.size());
		
		Person.delete(p1);
		
		people = Find.from(Person.class).all();
		Assert.assertNotNull(people);
		Assert.assertEquals(1, people.size());
	}
	
	@Test
	public void shouldReturnEmptyListWhenNotExistisPersistedObjects() {
		List<Person> people = Find.from(Person.class).all();
		Assert.assertNotNull(people);
	}
	
	@Ignore
	//TODO Verificar como salvar sub-classes de classes que estendem PrevalentObject
	public void shouldSaveACustomerAtRepository() {
		Customer customer = new Customer("Edipo", 23);
		customer.setAddress("Rua sem nome, Bairro Jardim América, 1231, Curitiba - PR");
		customer.setDtCadastro(new Date());
		customer.save();

		Assert.assertEquals(1, Repository.getListFor(Customer.class).size());
	}
	
	@Test
	public void shouldReturnTrueWhenSaveAPersonAtRepository() {
		Person p = givenAPerson(20);
		Assert.assertTrue(p.save());
	}
	
	@Test
	public void shouldReturnTrueWhenUpdateAPersonAtRepository() {
		Person p = givenAPerson(20);
		Assert.assertTrue(Person.save(p));
		p.setAge(21);
		Assert.assertTrue(p.save());
	}
	
	@Test
	public void shouldDeleteAPersonFromRepository() {
		Person p = givenAPerson(18);
		Person.save(p);
		Assert.assertTrue(Person.delete(p));
	}
	
	@Test
	public void shouldReturnFalseWhenDeleteNotSavedObject() {
		Person p = givenAPerson(18);
		Assert.assertFalse(Person.delete(p));
	}

	@Test(expected = CannotDeletePrevalentObjectException.class)
	public void shouldRaiseExceptionWhenTryToDeleteNotSavedObject() {
		Person p = givenAPerson(12);
		p.delete(true);
	}
	
	@Test
	public void shouldRaiseExceptionWhenTryToDeleteNotSavedObjectTestExceptionMessage() {
		Person p = givenAPerson(12);
		String message = "Cannot remove object of class Person. Only allowed to remove objects saved.";
		try {
			p.delete(true);
		} catch (CannotDeletePrevalentObjectException e) {
			Assert.assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	public void shouldNotRaiseExceptionWhenTryToDeleteNotSavedObject() {
		Person p = givenAPerson(12);
		p.delete(false);
		p.delete();
	}
	
	@Test(expected = CannotDeletePrevalentObjectException.class)
	public void shouldRaiseExceptionWhenTryToStaticDeleteNotSavedObject() {
		Person p = givenAPerson(12);
		Person.delete(p, true);
	}
	
	@Test
	public void shouldRaiseExceptionWhenTryToStaticDeleteNotSavedObjectTestExceptionMessage() {
		Person p = givenAPerson(12);
		String message = "Cannot remove object of class Person. Only allowed to remove objects saved.";
		try {
			Person.delete(p, true);
			Assert.fail("Not captured exception!");
		} catch (CannotDeletePrevalentObjectException e) {
			Assert.assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	public void shouldFindAPersonAtRepository() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person p3 = givenAPerson(18);
		p1.save(); p2.save(); p3.save();
		child.save(); child2.save();
		
		List<Person> adults = Find.from(Person.class).all(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		});
		Assert.assertEquals(3, adults.size());
	}
	
	@Test
	public void shouldFindAllPeople() {
		Person p = givenAPerson(18);
		Person p2 = givenAPerson(33);
		Person p3 = givenAPerson(50);
		p.save(); p2.save(); p3.save();
		
		List<Person> people = Find.from(Person.class).all();
		Assert.assertNotNull(people);
		Assert.assertEquals(3, people.size());
		Assert.assertEquals(50, people.get(2).getAge());
	}
	
	@Test
	public void shouldFindPersonByOID() {
		for (int i = 0; i < 1000; i++) {
			Person p = givenAPerson(i);
			Person.save(p);
		}
		Person person662 = Find.from(Person.class).byOID(662L);
		Assert.assertNotNull(person662);
		Assert.assertEquals(661, person662.getAge());
	}
	
	@Test
	public void shouldMaintainSameReference() {
		Person p = givenAPerson(18);
		p.save();
		Person anotherReferenceButSameObject = Find.from(Person.class).byOID(1L);
		Assert.assertSame(p, anotherReferenceButSameObject);
	}
	
	@Test
	public void shouldSaveAPerson() {
		Person p = givenAPerson(30);
		p.save();
		Assert.assertEquals(1, Repository.getRepository().size());
	}
}