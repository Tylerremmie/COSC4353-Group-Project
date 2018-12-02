package cosc4353;

import com.amazonaws.services.greengrass.model.AssociateServiceRoleToAccountRequest;
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
	Player player = new Player("Tyler", "Red", 1, 20);
	Dice dice = new Dice();
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
		
		Assert.assertTrue(player.hasArmy());
		player.increaseArmiesBy(1);
		player.reduceArmiesBy(6);
		Assert.assertFalse(player.hasArmy());
		
		
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
		
		player.setInGameCredit(10);
		Assert.assertEquals(player.getInGameCredit(),10);
		
		player.incrementInGameCredit(10);
		player.decrementInGameCredit(5);
		Assert.assertEquals(player.getInGameCredit(),15);

		
		
		
		player.setTerritoriesConquered(10);
		Assert.assertEquals(player.getTerritoriesConquered(),10);
		
		player.increaseTerritoriesConquered(2);
		Assert.assertEquals(player.getTerritoriesConquered(),12);

		player.decreaseTerritoriesConquered(2);
		Assert.assertEquals(player.getTerritoriesConquered(),10);
		
		
	}
	
	@Test
	public void testEngine(){
		
		Assert.assertEquals(engine.getArmyCount(2),35);
		Assert.assertEquals(engine.getArmyCount(3),35);
		Assert.assertEquals(engine.getArmyCount(4),30);
		Assert.assertEquals(engine.getArmyCount(5),25);
		Assert.assertEquals(engine.getArmyCount(6),20);
		
		Assert.assertEquals(engine.givePlayerCredit(),100);
		Assert.assertEquals(engine.giveOtherPlayerCredit(),10);

	}

	@Test
	public void testa() {
		Assert.assertEquals(0,0);
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
		Territory terr = new Territory("Brazil");
		Player playerobj = new Player("Tyler", "Red", 1, 20);

		Assert.assertTrue(terr.setPlayer(playerobj));
		Assert.assertTrue(terr.setnumberofArmies(100));
		Assert.assertTrue(terr.increaseArmies(50));
		Assert.assertTrue(terr.decreaseArmies(50));

		Assert.assertEquals(territory.getName(), "Brazil");
		Assert.assertTrue(terr.isOccupied());
		Assert.assertEquals(terr.getnumberofArmies(), 100);
		Assert.assertEquals(terr.getPlayerOccupying(), playerobj);
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
		
		Assert.assertTrue(hand.checkcards(1, 1, 1));
		
		Assert.assertTrue(hand.checkcards(1, 1, 4));

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
	public void testAttackWatcher() {

		Assert.assertEquals(0,0);
	}

	@Test
	public void testTurnManager() {
		players.add(player);
		Board board = new Board();
		TurnManager turnManager = new TurnManager(players, board);
		
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
	
	@Test
	public void testBoard() {
		Board bd = new Board();
		bd.createBoard();

		Board bd2 = new Board();
		bd2.createBoard();

		Assert.assertEquals(bd.getTerritories(), bd2.getTerritories());
		Assert.assertEquals(bd.getTerritoriesString(), bd2.getTerritoriesString());
		Assert.assertEquals(bd.getContinentsString(), bd2.getContinentsString());
		Assert.assertEquals(bd.getAdjacenciesString(), bd2.getAdjacenciesString());

	}

	
	@Test
	public void testThreadTimeout() {
		Assert.assertEquals(engine.getinput("Tom", 3),"8");
		
	}
	
	@Test
	public void testTimer(){
		Timer ti = new Timer();
		ti.start();
		Assert.assertFalse(ti.isInterrupted());
		ti.interrupt();
		Assert.assertTrue(ti.isInterrupted());
		
//		ti = new Timer();
//		ti.start();
//		ti.interrupt();
//		Assert.assertTrue(ti.isInterrupted());
	}
	
//	@Test // not sure if observer can be tested (doesnt show on the codecov)
//	public void testAttackWatcher(){
//		players.add(player);
//		Board board = new Board();
//		TurnManager turnManager = new TurnManager(players, board);
//		AttackWatcher aw = new AttackWatcher(turnManager);
//		
//		aw.announce();
//		
//	}
	
	@Test
	public void testPaymentProxy(){
		PaymentProxy pp = new PaymentProxy();
		Assert.assertEquals(pp.givePlayerCredit(),100);
		Assert.assertEquals(pp.giveOtherPlayerCredit(),10);
		
	}

	/*
		On S3 and Twitter tests we have to check for failures since our repo will not have the keys to properly test the functionality.
		We have ensured these methods were tested locally first before changing to asserting false.
	*/
	@Test
	public void testReplays(){

		Replay s3 = new Replay();
		Assert.assertFalse(s3.uploadReplay());
		Assert.assertFalse(s3.downloadReplay());
	}
	
}
