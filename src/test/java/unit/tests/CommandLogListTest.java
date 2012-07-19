package unit.tests;

import helpers.Person;

import org.junit.Before;
import org.junit.Test;

import br.bodoque.Command;
import br.bodoque.CommandLogList;
import br.bodoque.SerializeCommand;

public class CommandLogListTest extends UnitTestCase {
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRiseExceptionWhenTryToInvokeCommandNotAdded() {
		Person person = createAPerson(18);
		Command commandNotAdded = new SerializeCommand<Person>(person);
		CommandLogList.executeCommand(commandNotAdded);
	}
	
}
