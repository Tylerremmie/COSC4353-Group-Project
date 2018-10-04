package cosc4353;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for Group Project.
 */
public class UnitTest
{

	Player player;
	Dice dice;
	Territory territory;
	Continent continent;
	Card card;
	Hand hand;
	Engine engine;
	
	ArrayList<Territory> territories = new ArrayList<Territory>();

	@Before
	public void setup() throws Exception {
			engine = new Engine();
			player = new Player("Tyler", "Red", 1);
			hand = new Hand();
			dice = new Dice();
			territory = new Territory("Brazil");
			card = new Card("Infantry", territory);
			continent = new Continent("South America", 2, territories);
			
	}
	
	@Test
	public void testPlayer() {
		Assert.assertEquals(player.getName(), "Tyler");
		Assert.assertEquals(player.getColor(), "Red");
		Assert.assertEquals(player.getTurnPosition(), 0);
		
		player.setName("Dan");
		Assert.assertEquals(player.getName(), "Dan");
		player.setColor("yellow");
		Assert.assertEquals(player.getColor(), "yellow");
		
		player.setNumberofArmies(5);
		Assert.assertEquals(player.getNumberofArmies(), 5);
		
		player.setTurnPosition(0);
		Assert.assertEquals(player.getTurnPosition(),0);
		player.setTurnPosition(6);
		Assert.assertEquals(player.getTurnPosition(),6);
	
	}

	@Test
	public void testContinent() {
		Assert.assertEquals(continent.getName(), "South America");
		Assert.assertEquals(continent.getBonusArmyValue(), 2);
		Assert.assertEquals(continent.getIsControlled(), false);
	}

	@Test
	public void testTerritory() {
		Assert.assertEquals(territory.getName(), "Brazil");
		Assert.assertEquals(territory.getOccupied(), false);
		Assert.assertEquals(territory.getnumberofArmies(), 0);
		
		Territory T2 = new Territory("Testing");
		
		T2.setnumberofArmies(5);
		Assert.assertEquals(T2.getnumberofArmies(),5);
		T2.increaseArmies(5);
		Assert.assertEquals(T2.getnumberofArmies(),10);
		T2.decreaseArmies(3);
		Assert.assertEquals(T2.getnumberofArmies(),7);
		
		T2.setPlayer(player);
		Assert.assertEquals(T2.getPlayerOccupying(),player);
		
	}

	@Test
	public void testDice() {
		int result = dice.roll();
		Assert.assertTrue(1 <= result && result <= 6);
		Assert.assertEquals(dice.getval(),0);
	}

	@Test
	public void testCard() {
		Assert.assertEquals(card.getTypeofCard(), "Infantry");
		Assert.assertEquals(card.getTerritory(), territory);
	}
	
	@Test
	public void testDeck() {
		ArrayList<Territory> testTlist= new ArrayList<Territory>();
		Territory T3 = new Territory("Infantry");
		testTlist.add(T3);
		Deck deck = new Deck(testTlist);
		
		Assert.assertEquals((deck.draw()).getTypeofCard(),"Infantry");
	}

	/*
	@Test
	public void testArmies() {
		Assert.assertEquals(engine.Unit_Armies_Place(0),0);
		Assert.assertEquals(engine.Unit_Armies_Place(1),1);
	}
	
	@Test
	public void testCountries() {
		Assert.assertEquals(engine.Unit_Turn_Countries_Neutral(0),0);
		Assert.assertEquals(engine.Unit_Turn_Countries_Attack(0),0);
		Assert.assertEquals(engine.Unit_Turn_Countries_Neutral(1),1);
		Assert.assertEquals(engine.Unit_Turn_Countries_Attack(1),1);
	}
	
	@Test
	public void testReset() {
		Assert.assertEquals(engine.Unit_Turn_Reset(0),0);
		Assert.assertEquals(engine.Unit_Turn_Reset(1),1);
	}
	*/
	
	@Test
	public void testHand(){
		Assert.assertFalse(hand.handSize());
		hand.insert(new Card("Test",territory));
		Assert.assertFalse(hand.handSize());
		hand.insert(new Card("Test2",territory));
		Assert.assertFalse(hand.handSize());
		hand.insert(new Card("Test3",territory));
		Assert.assertFalse(hand.handSize());
		hand.insert(new Card("Wild",territory));
		Assert.assertFalse(hand.handSize());
		hand.insert(new Card("Wild",territory));
		Assert.assertTrue(hand.handSize());
		
		Assert.assertTrue(hand.checkcards(1, 2, 3));
		Assert.assertTrue(hand.checkcards(1, 4, 2));

	}
	
	
}