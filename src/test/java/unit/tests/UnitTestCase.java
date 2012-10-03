package unit.tests;

import br.bodoque.Bodoque;
import helpers.Person;

public class UnitTestCase {

	public void setUp() {
		Bodoque.initialize();
	}
	
	public Person givenAPerson(int idade) {
		return new Person("Pessoa", idade);
	}
}