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
	public void shouldFindFirstTwoPeopleSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		Assert.assertEquals(2, Find.from(Person.class).first(2).size());
		Assert.assertEquals(37, Find.from(Person.class).first(2).get(0).getAge());
		Assert.assertEquals(25, Find.from(Person.class).first(2).get(1).getAge());
	}
	
	@Test
	public void shouldFindFirstTwoAdultPeopleSaved() {
		Person p1 = givenAPerson(17);
		Person p2 = givenAPerson(50);
		Person p3 = givenAPerson(18);
		Person p4 = givenAPerson(75);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person.save(child, p1, p2, p4, child2, p3);
		
		List<Person> firstTwoAdults = Find.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(final Person p) {
				return p.getAge() >= 18;
			}
		}).first(2);
		
		Assert.assertEquals(2, firstTwoAdults.size());
		Assert.assertEquals(50, firstTwoAdults.get(0).getAge());
		Assert.assertEquals(75, firstTwoAdults.get(1).getAge());
	}
	
	/*
	 * Will try return 3 people, but there's only 2 at repository
	 * then will return only these 2
	 */
	@Test
	public void shouldFindFirstThreePeopleSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		p2.save(); p1.save();
		
		Assert.assertEquals(2, Find.from(Person.class).first(3).size());
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
	public void shouldFindLastTwoPeopleSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		Assert.assertEquals(2, Find.from(Person.class).last(2).size());
		Assert.assertEquals(23, Find.from(Person.class).last(2).get(0).getAge());
		Assert.assertEquals(25, Find.from(Person.class).last(2).get(1).getAge());
	}
	
	@Test
	public void shouldFindLastTwoAdultPeopleSaved() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person p3 = givenAPerson(18);
		Person p4 = givenAPerson(75);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person.save(child, p1, p2, p4, child2, p3);
		
		List<Person> lastTwoAdults = Find.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(final Person p) {
				return p.getAge() >= 18;
			}
		}).last(2);
		Assert.assertEquals(2, lastTwoAdults.size());
		Assert.assertEquals(18, lastTwoAdults.get(0).getAge());
		Assert.assertEquals(75, lastTwoAdults.get(1).getAge());
	}
	
	/*
	 * Will try return 3 people, but there's only 2 at repository
	 * then will return only these 2
	 */
	@Test
	public void shouldFindLastThreePeopleSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		p2.save(); p1.save();
		
		Assert.assertEquals(2, Find.from(Person.class).last(3).size());
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
	public void shouldCountPeople() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person p3 = givenAPerson(18);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person.save(p1, p2, p3);
		child.save(); child2.save();
		
		Assert.assertEquals(5, Find.from(Person.class).count());
	}
	
	@Test
	public void shouldFindPeopleWhoHasADog() {
		Person paul = givenAPersonWithADog(27, "Bob");
		Person john = givenAPersonWithADog(35, "Spike");
		Person mike = givenAPersonWithADog(15, "Pitu");
		Person josh = givenAPerson(12);
		Person marcelino = givenAPerson(8);
		Person.save(paul, josh, john, mike, marcelino);
		
		List<Person> peopleWithDog = Find.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getDog() != null;
			}
		}).all();
		Assert.assertNotNull(peopleWithDog);
		Assert.assertEquals(3, peopleWithDog.size());
	}
}
