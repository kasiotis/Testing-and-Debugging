package test.java;

import static org.junit.Assert.*;



import org.junit.Test;
import main.java.Suit;
import main.java.Value;
import main.java.Card;
import main.java.Player;
import main.java.Switch;
import main.java.UserInterface;

public class JUnitTest {


	public void test() {
		fail("Not yet implemented");
	}
	
		@Test
		public void testCardClass(){
			//testing isSameSuit
			Card a = new Card(Suit.CLUBS,Value.ACE);
			Card b = new Card(Suit.CLUBS,Value.ACE);
			
			assertTrue(a.isSameSuit(b));
			//assertFalse(a.isSameSuit(b));
			
			
			//testing isSameValue
			Card c = new Card(Suit.CLUBS,Value.ACE);
			Card d = new Card(Suit.HEARTS,Value.ACE);
			
			assertTrue(c.isSameValue(d));
			//assertFalse(c.isSameValue(d));
			
			assertTrue(a.equals(b));	
		
		}
		
		
		
		
	}