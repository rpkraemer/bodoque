package unit.tests;

import helpers.Person;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.bodoque.Filter;
import br.bodoque.Find;

public class FindTest extends UnitTestCase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
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
		Person person662 = Find.from(Person.class).byId(662L);
		Assert.assertNotNull(person662);
		Assert.assertEquals(661, person662.getAge());
	}
	
}
