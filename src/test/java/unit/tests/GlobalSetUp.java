package unit.tests;

import helpers.Dog;
import helpers.Person;
import br.bodoque.CommandLogList;
import br.bodoque.Repository;
import br.bodoque.Sequence;

public class GlobalSetUp {

	protected void setUp() {
		CommandLogList.clearLogList();
		Repository.clearRepositoryFor(Person.class);
		Repository.clearRepositoryFor(Dog.class);
		Sequence.clearSequenceFor(Person.class);
	}
}
