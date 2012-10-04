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
	public void shouldFindAdultPeopleAtRepository() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person p3 = givenAPerson(18);
		p1.save(); p2.save(); p3.save();
		child.save(); child2.save();
		
		List<Person> adults = Find.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(final Person p) {
				return p.getAge() >= 18;
			}
		}).all();
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
	public void shouldFindPersonById() {
		for (int i = 0; i < 1000; i++) {
			Person p = givenAPerson(i);
			Person.save(p);
		}
		Person person662 = Find.from(Person.class).byId(662L);
		Assert.assertNotNull(person662);
		Assert.assertEquals(661, person662.getAge());
	}
	
	@Test
	public void shouldFindFirstPersonSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		
		p3.save(); p2.save(); p1.save();
		
		Person firstSaved = Find.from(Person.class).first();
		Assert.assertEquals(37, firstSaved.getAge());
	}
	
	@Test
	public void shouldFindFirstAdultSaved() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person p3 = givenAPerson(18);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		child.save(); child2.save();
		p2.save(); p1.save(); p3.save();
		
		Person firstAdult = Find.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(final Person p) {
				return p.getAge() >= 18;
			}
		}).first();
		Assert.assertEquals(50, firstAdult.getAge());
	}
	
	@Test
	public void shouldFindLastPersonSaved() {
		Person p1 = givenAPerson(23);
		Person p3 = givenAPerson(37);
		
		p3.save(); p1.save(); p1.save();
		
		Person lastSaved = Find.from(Person.class).last();
		Assert.assertEquals(23, lastSaved.getAge());
	}
	
	@Test
	public void shouldFindLastAdultSaved() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person p3 = givenAPerson(18);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		p2.save(); p3.save(); p1.save();
		child.save(); child2.save();
		
		Person lastAdult = Find.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(final Person p) {
				return p.getAge() >= 18;
			}
		}).last();
		Assert.assertEquals(19, lastAdult.getAge());
	}
	
	@Test
	public void shouldCountAdultPeople() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person p3 = givenAPerson(18);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person.save(p1, p2, p3);
		child.save(); child2.save();
		
		long countAdultPeople = Find.from(Person.class).
			filter(new Filter<Person>() {
				public boolean accept(final Person p) {
					return p.getAge() >= 18;
				}
			}).count();
		Assert.assertEquals(3, countAdultPeople);
	}
	
	@Test
	public void shouldCountFilteredSavedPeople() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person p3 = givenAPerson(18);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person.save(p1, p2, p3);
		child.save(); child2.save();
		
		Assert.assertEquals(5, Find.from(Person.class).count());
	}
}
