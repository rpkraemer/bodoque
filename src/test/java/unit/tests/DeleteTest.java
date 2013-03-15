package unit.tests;

import helpers.Person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.bodoque.Delete;
import br.bodoque.Filter;
import br.bodoque.Find;

public class DeleteTest extends UnitTestCase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldDeleteAPerson() {
		Person p = givenAPerson(20);
		Person.save(p);
		Assert.assertTrue(Delete.from(Person.class).byId(1l));
		Person savedPerson = Find.from(Person.class).byId(1l);
		Assert.assertTrue(savedPerson == null);
	}
	
	@Test
	public void shouldNotRaiseExceptionWhenTryDeleteNullObject(){
		Delete.from(Person.class).byId(6660L);
	}
	
	@Test
	public void shouldDeleteAllPeople() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person p3 = givenAPerson(18);
		p1.save(); p2.save(); p3.save();
		child.save(); child2.save();
		
		Assert.assertTrue(Find.from(Person.class).count() == 5);
		Assert.assertTrue(Delete.from(Person.class).all());
		Assert.assertTrue(Find.from(Person.class).count() == 0);
	}
	
	@Test
	public void shouldDeleteFirstPersonSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		Assert.assertTrue(Delete.from(Person.class).first());
		Assert.assertEquals(2, Find.from(Person.class).count());
		Assert.assertTrue(Find.from(Person.class).all().get(0).getAge() != 37);
		Assert.assertTrue(Find.from(Person.class).all().get(1).getAge() != 37);
	}
	
	@Test
	public void shouldDeleteTwoFirstPeopleSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		Assert.assertTrue(Delete.from(Person.class).first());
		Assert.assertTrue(Delete.from(Person.class).first());
		Assert.assertEquals(1, Find.from(Person.class).count());
		Assert.assertTrue(Find.from(Person.class).all().get(0).getAge() == 23);
	}
	
	@Test
	public void shouldDeleteTwoFirstPeopleSavedShortcut() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		// Shortcut, pass the number of objects do delete
		Assert.assertTrue(Delete.from(Person.class).first(2));
	
		Assert.assertEquals(1, Find.from(Person.class).count());
		Assert.assertTrue(Find.from(Person.class).all().get(0).getAge() == 23);
	}
	
	@Test
	public void shouldDeleteLastPersonSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		Assert.assertTrue(Delete.from(Person.class).last());
		Assert.assertEquals(2, Find.from(Person.class).count());
		Assert.assertTrue(Find.from(Person.class).all().get(0).getAge() != 23);
		Assert.assertTrue(Find.from(Person.class).all().get(1).getAge() != 23);
	}
	
	@Test
	public void shouldDeleteTwoLastPeopleSaved() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		Assert.assertTrue(Delete.from(Person.class).last());
		Assert.assertTrue(Delete.from(Person.class).last());
		Assert.assertEquals(1, Find.from(Person.class).count());
		Assert.assertTrue(Find.from(Person.class).all().get(0).getAge() == 37);
	}
	
	@Test
	public void shouldDeleteTwoLastPeopleSavedShortcut() {
		Person p1 = givenAPerson(23);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		p3.save(); p2.save(); p1.save();
		
		// Shortcut, pass the number of objects do delete
		Assert.assertTrue(Delete.from(Person.class).last(2));
		
		Assert.assertEquals(1, Find.from(Person.class).count());
		Assert.assertTrue(Find.from(Person.class).all().get(0).getAge() == 37);
	}
	
	@Test
	public void shouldDeleteAllNotAdultPeople() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person p3 = givenAPerson(18);
		p1.save(); p2.save(); p3.save();
		child.save(); child2.save();
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() < 18;
			}
		}).all());
		Assert.assertEquals(3, Find.from(Person.class).count());
	}
	
	@Test
	public void shouldDeleteAllPeopleWhoHasADog() {
		Person paul = givenAPersonWithADog(27, "Bob");
		Person john = givenAPersonWithADog(35, "Spike");
		Person mike = givenAPersonWithADog(15, "Pitu");
		Person josh = givenAPerson(12);
		Person marcelino = givenAPerson(8);
		
		Person.save(paul, john, mike, josh, marcelino);
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getDog() != null;
			}
		}).all());
		Assert.assertEquals(2, Find.from(Person.class).count());
	}
	
	@Test
	public void shouldDeleteFirstPeopleWhoHasADog() {
		Person paul = givenAPersonWithADog(27, "Bob");
		Person john = givenAPersonWithADog(35, "Spike");
		Person mike = givenAPersonWithADog(15, "Pitu");
		Person josh = givenAPerson(12);
		Person marcelino = givenAPerson(8);
		
		Person.save(paul, john, mike, josh, marcelino);
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getDog() != null;
			}
		}).first());
		Assert.assertEquals(4, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getDog() != null && p.getDog().getName().equals("Bob"))
				Assert.fail("First saved person with dog was not deleted.");
	}
	
	@Test
	public void shouldDeleteLastThreePeopleWhoHasADog() {
		Person paul = givenAPersonWithADog(27, "Bob");
		Person john = givenAPersonWithADog(35, "Spike");
		Person mike = givenAPersonWithADog(15, "Pitu");
		Person josh = givenAPerson(12);
		Person marcelino = givenAPerson(8);
		
		Person.save(josh, paul, marcelino, john, mike);
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getDog() != null;
			}
		}).last(3));
		Assert.assertEquals(2, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getDog() != null)
				Assert.fail("Last three people with dog were not deleted.");
	}
	
	@Test
	public void shouldDeleteFirstAdultSaved() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person p3 = givenAPerson(18);
		child.save(); child2.save();
		p2.save(); p1.save(); p3.save();
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).first());
		Assert.assertEquals(4, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getAge() == 50)
				Assert.fail("First adult person saved not deleted.");
	}
	
	@Test
	public void shouldDeleteLastAdultSaved() {
		Person p1 = givenAPerson(19);
		Person p2 = givenAPerson(50);
		Person child = givenAPerson(5);
		Person child2 = givenAPerson(11);
		Person p3 = givenAPerson(18);
		child.save(); p2.save(); 
		p3.save(); p1.save(); child2.save(); 
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).last());
		Assert.assertEquals(4, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getAge() == 19)
				Assert.fail("Last adult person saved not deleted.");
	}
	
	@Test
	public void shouldDeleteTwoFirstAdultSaved() {
		Person p1 = givenAPerson(17);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		Person p4 = givenAPerson(52);
		p1.save(); p3.save(); p2.save(); 
		Person.save(p4); 
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).first());
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).first());
		
		Assert.assertEquals(2, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getAge() == 25 || p.getAge() == 37)
				Assert.fail("Two first adult people saved not deleted.");
	}
	
	@Test
	public void shouldDeleteTwoFirstAdultSavedShortcut() {
		Person p1 = givenAPerson(17);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		Person p4 = givenAPerson(52);
		p1.save(); p3.save(); p2.save(); 
		Person.save(p4); 
		
		// Shortcut, pass the number of objects do delete
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).first(2));
		
		Assert.assertEquals(2, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getAge() == 25 || p.getAge() == 37)
				Assert.fail("Two first adult people saved not deleted.");
	}
	
	@Test
	public void shouldDeleteTwoLastAdultSaved() {
		Person p1 = givenAPerson(17);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		Person p4 = givenAPerson(52);
		p1.save(); p3.save(); p2.save(); 
		Person.save(p4); 
		
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).last());
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).last());
		
		Assert.assertEquals(2, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getAge() == 25 || p.getAge() == 52)
				Assert.fail("Two last adult people saved not deleted.");
	}
	
	@Test
	public void shouldDeleteTwoLastAdultSavedShortcut() {
		Person p1 = givenAPerson(17);
		Person p2 = givenAPerson(25);
		Person p3 = givenAPerson(37);
		Person p4 = givenAPerson(52);
		p1.save(); p3.save(); p2.save(); 
		Person.save(p4); 
		
		// Shortcut, pass the number of objects do delete
		Assert.assertTrue(Delete.from(Person.class).filter(new Filter<Person>() {
			public boolean accept(Person p) {
				return p.getAge() >= 18;
			}
		}).last(2));
		
		Assert.assertEquals(2, Find.from(Person.class).count());
		for (Person p : Find.from(Person.class).all())
			if (p.getAge() == 25 || p.getAge() == 52)
				Assert.fail("Two first adult people saved not deleted.");
	}
}