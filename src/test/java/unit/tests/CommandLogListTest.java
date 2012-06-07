package unit.tests;

import helpers.Person;

import org.junit.Before;
import org.junit.Test;

import br.bodoque.Command;
import br.bodoque.CommandLogList;
import br.bodoque.SerializeCommand;

public class CommandLogListTest extends GlobalSetUp {
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRiseExceptionWhenTryToInvokeCommandNotAdded() {
		Person person = new Person("Pessoa", 30);
		Command commandNotAdded = new SerializeCommand<Person>(1L, person);
		CommandLogList.executeCommand(commandNotAdded);
	}
}
