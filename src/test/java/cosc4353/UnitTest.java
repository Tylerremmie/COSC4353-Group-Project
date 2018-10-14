package cosc4353;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test for Group Project.
 */
public class UnitTest
{
	ArrayList<Territory> territories = new ArrayList<Territory>();
	ArrayList<Player> players = new ArrayList<Player>();
	Player player = new Player("Tyler", "Red", 1);
	Dice dice = new Dice();;
	Territory territory = new Territory("Brazil");
	Continent continent = new Continent("South America", 2, territories);
	Card card = new Card("Infantry", territory);
	Hand hand = new Hand();
	Engine engine = new Engine();
	ActionManager actionManager = new ActionManager();
	
	@Test
	public void testPlayer() {
		Assert.assertEquals(player.getName(), "Tyler");
		Assert.assertEquals(player.getColor(), "Red");
		Assert.assertEquals(player.getTurnPosition(), 1);
		
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

		Assert.assertNotNull(player.getTerritories());
		Assert.assertNotNull(player.getContinents());
		Assert.assertNotNull(player.getCardsInHand());
		Assert.assertNotNull(player.getHand());
		int[] cards = {0,1,2};
		Assert.assertTrue(player.turnIn(cards));
	}

	@Test
	public void testBoard() {
		Board B1 = new Board();
		Assert.assertTrue(B1.createBoard());
		Assert.assertNotNull(B1.getAdjacencies());
		Assert.assertNotNull(B1.getContinents());
		Assert.assertNotNull(B1.getTerritories());
	}

	
	@Test
	public void testContinent() {
		Assert.assertEquals(continent.getName(), "South America");
		Assert.assertEquals(continent.getBonusArmyValue(), 2);
		Assert.assertEquals(continent.getIsControlled(), false);
		continent.setControl(player);
		Assert.assertTrue(continent.setControl(player));
		Assert.assertEquals(continent.getControlledBy(), player);
		Assert.assertNotNull(continent.getTerritories());
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

		territories.add(territory);
		Assert.assertTrue(T2.createAdjacencies(territories));
		Assert.assertEquals(T2.getAdjacencies(), territories);
	}

	@Test
	public void testDice() {
		Assert.assertEquals(dice.getval(),0);
		int result = dice.roll();
		Assert.assertTrue(1 <= result && result <= 6);
	}

	@Test
	public void testCard() {
		Assert.assertEquals(card.getTypeofCard(), "Infantry");
		Assert.assertEquals(card.getTerritory(), territory);
		Assert.assertTrue(card.setTypeofCard("Infantry"));
		Assert.assertTrue(card.setTerritory(territory));
	}
	
	@Test
	public void testDeck() {
		ArrayList<Territory> testTlist= new ArrayList<Territory>();
		Territory T3 = new Territory("Infantry");
		testTlist.add(T3);
		Deck deck = new Deck(testTlist);
		
		Assert.assertEquals((deck.draw()).getTypeofCard(),"Infantry");
		Assert.assertTrue(deck.add(card));
		Assert.assertTrue(deck.shuffle());
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

		Assert.assertTrue(hand.turnInCards(0, 1, 2));
		Assert.assertNotNull(hand.getHand());

	}

	@Test
	public void testActionManager() {
		Assert.assertFalse(actionManager.isUndoAvailable());
		Assert.assertFalse(actionManager.isRedoAvailable());
		
		Assert.assertTrue(actionManager.getsize() >=0);
		
	}

	@Test
	public void testTurnManager() {
		players.add(player);
		TurnManager turnManager = new TurnManager(players);
		Assert.assertEquals(turnManager.getnumberofPlayers(), 1);
		Assert.assertEquals(turnManager.getplayersTurn(), 0);
		Assert.assertEquals(turnManager.getturnNumber(), 1);
		Assert.assertEquals(turnManager.getCurrentPlayerName(), "Tyler");
		Assert.assertTrue(turnManager.takeTurn());
		Assert.assertTrue(turnManager.undo());
		Assert.assertTrue(turnManager.redo());
		Assert.assertTrue(turnManager.setplayersTurn(0));
		Assert.assertTrue(turnManager.setturnNumber(1));
		Assert.assertTrue(turnManager.setnumberofPlayers(1));
		Assert.assertTrue(turnManager.incrementplayer());
		Assert.assertTrue(turnManager.decrementplayer());
		Assert.assertTrue(turnManager.incrementturn());
		Assert.assertTrue(turnManager.decrementturn());
		Assert.assertNotNull(turnManager.getPlayersObject());
	}
	
}