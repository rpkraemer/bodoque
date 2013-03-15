package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Customer;
import helpers.Person;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
		Assert.assertNotNull(people.get(0).getId());
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
	
	@Test
	//TODO Verificar como salvar sub-classes de classes que estendem PrevalentObject
	public void shouldSaveACustomerAtRepository() {
		Customer customer = new Customer("Edipo", 23);
		customer.setAddress("Rua sem nome, Bairro Jardim América, 1231, Curitiba - PR");
		customer.setDtCadastro(new Date());
		customer.save();
		Assert.assertEquals(0, Repository.getListFor(Person.class).size());
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
		Assert.assertFalse(p.delete());
	}
	
	@Test
	public void shouldReturnFalseWhenDeleteNotSavedObjectStaticWay() {
		Person p = givenAPerson(18);
		Assert.assertFalse(Person.delete(p));
	}
	
	@Test
	public void shouldNotMaintainSameReferenceWhenSaveToRepository() {
		Person p = givenAPerson(18);
		p.save();
		Person anotherReferenceButSameObject = Find.from(Person.class).byId(1L);
		Assert.assertNotSame(p, anotherReferenceButSameObject);
		
		p.setName("Bob");
		Assert.assertEquals("Pessoa", anotherReferenceButSameObject.getName());
		p.save();
		anotherReferenceButSameObject = Find.from(Person.class).byId(1L);
		Assert.assertEquals("Bob", anotherReferenceButSameObject.getName());
	}
	
	@Test
	public void shouldNotMaintainSameReferenceWhenSaveToRepositoryStaticWay() {
		Person p = givenAPerson(18);
		Person.save(p);
		Person anotherReferenceButSameObject = Find.from(Person.class).byId(1L);
		Assert.assertNotSame(p, anotherReferenceButSameObject);
		
		p.setName("Bob");
		Assert.assertEquals("Pessoa", anotherReferenceButSameObject.getName());
		p.save();
		anotherReferenceButSameObject = Find.from(Person.class).byId(1L);
		Assert.assertEquals("Bob", anotherReferenceButSameObject.getName());
	}
	
	@Test
	public void shouldSaveAPerson() {
		Person p = givenAPerson(30);
		p.save();
		Assert.assertEquals(1, Repository.getRepository().size());
	}
	
	
	@Test
	public void shouldNotImplicitSavePerson(){
	  Person obj = givenAPerson(74);
	  obj.save();
	  obj = null;
	  obj = Find.from(Person.class).byId(1L);
	  obj.setAge(12);
	  Person obj2 = Find.from(Person.class).byId(1L);
	  Assert.assertTrue(obj2.getAge() == 74);
	  
	}
	
	@Test
	public void shouldTrySaveASavedPerson(){
		Person p = givenAPerson(30);
		Person.save(p);
		Person.save(p);
	}
}