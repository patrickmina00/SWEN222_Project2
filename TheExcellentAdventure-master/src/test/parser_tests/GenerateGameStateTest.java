package test.parser_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import parser.GsParser;
import datastorage.GameState;

public class GenerateGameStateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		GameState gs = GsParser.generateGameState("RUN S R1 L C O C I1TA I2FR R2 O C C L I3DA I4TR P1 1 E I5FA P2 2 W I6DR I7TA M EMPTY X");
		String expected = "RUN S R1 L C O C I1TA I2FR R2 O C C L I3DA I4TR P1 1 E I5FA P2 2 W I6DR I7TA M EMPTY X";
		String real = GsParser.generateExp(gs);
		
		boolean isEqual = expected.equals(real);
		
		assertTrue("The generated expression is the same as the expected", isEqual);
		
	}
	
}
