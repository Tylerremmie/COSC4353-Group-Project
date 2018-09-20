package cosc4353;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

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
	ArrayList<Territory> territories = new ArrayList<Territory>();

	@Before
	public void setup() throws Exception {
			player = new Player("Tyler", "Red");
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
	}

	@Test
	public void testRoll() {
		int result = dice.roll();
		Assert.assertTrue(1 <= result && result <= 6);
	}

	@Test
	public void testCard() {
		Assert.assertEquals(card.getTypeofCard(), "Infantry");
		Assert.assertEquals(card.getTerritory(), territory);
	}



}
