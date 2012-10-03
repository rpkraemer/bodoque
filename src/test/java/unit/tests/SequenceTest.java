package unit.tests;

import static org.junit.Assert.assertEquals;
import helpers.Person;

import org.junit.Before;
import org.junit.Test;

import br.bodoque.Sequence;


public class SequenceTest extends UnitTestCase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void shouldInitializeIDsInOneWhenSequenceForThatClassIsEmpty() {
		Long nextOID = Sequence.getNextIdFor(Person.class);
		assertEquals(1L, nextOID.longValue());
	}
	
	@Test
	public void shouldIncrementSequenceForAExistingSequenceClass() {
		Long nextOID = createAndIncrementTwoIndexes();
		assertEquals(2L, nextOID.longValue());
	}
	
	@Test
	public void shouldClearExistingSequence() {
		Long nextOID = createAndIncrementTwoIndexes();
		assertEquals(2L, nextOID.longValue());
		
		Sequence.clearSequenceFor(Person.class);
		nextOID = Sequence.getNextIdFor(Person.class); //should reset sequence
		assertEquals(1L, nextOID.longValue());
	}
	
	private Long createAndIncrementTwoIndexes() {
		Sequence.getNextIdFor(Person.class); // create the sequence
		Long nextOID = Sequence.getNextIdFor(Person.class); //should increment
		return nextOID;
	}
}
