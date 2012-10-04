package unit.tests;

import helpers.Dog;
import helpers.Person;
import br.bodoque.Bodoque;

public class UnitTestCase {

	public void setUp() {
		Bodoque.initialize();
	}
	
	public Person givenAPerson(int idade) {
		return new Person("Pessoa", idade);
	}
	
	public Person givenAPersonWithADog(int idade, String dogName) {
		Person p = new Person("Pessoa", idade);
		p.setDog(new Dog(dogName));
		return p;
	}
}