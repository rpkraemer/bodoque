package unit.tests;

import static org.junit.Assert.*;
import helpers.Person;
import org.junit.Before;
import org.junit.Test;
import br.bodoque.Sequence;


public class SequenceTest {

	@Before
	public void setUp() {
		Sequence.clearSequenceFor(Person.class);
	}
	
	@Test
	public void shouldInitializeIDsInOneWhenSequenceForThatClassIsEmpty() {
		Long nextOID = Sequence.getNextOIDFor(Person.class);
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
		nextOID = Sequence.getNextOIDFor(Person.class); //should reset sequence
		assertEquals(1L, nextOID.longValue());
	}
	
	private Long createAndIncrementTwoIndexes() {
		Sequence.getNextOIDFor(Person.class); // create the sequence
		Long nextOID = Sequence.getNextOIDFor(Person.class); //should increment
		return nextOID;
	}
}
