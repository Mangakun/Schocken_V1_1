package schocken.schocken_v1_1.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import schocken.schocken_v1_1.player.dice.Dice;
import schocken.schocken_v1_1.player.exceptions.DiceAlreadyOutException;
import schocken.schocken_v1_1.player.exceptions.MaxPenaltyException;
import schocken.schocken_v1_1.player.impl.PlayerImpl;

/**
 * Created by marco on 14.08.17.
 */


public class PlayerTest {

    /**
     * An object of the class {@link Player}.
     */
    private static Player player;

    /**
     * A player name.
     */
    private static final String playerName = "Marco";

    /**
     * This method creates a new player.
     */
    @BeforeClass
    public static void createGame() {
        player = new PlayerImpl(playerName,null,null);
    }

    /**
     * This method resets a player.
     */
    @Before
    public void resetPlayer(){
        player.reset();
    }


    /**
     * This method tests the name of a player.
     */
    @Test
    public void playerNameTest(){
        Assert.assertEquals("The player name should be " ,playerName ,player.getName());
    }

    /**
     * This method tests the addPenalties method of a player.
     */
    @Test
    public void addPenaltiesTest(){
       for(int i=0; i <13;++i){
            try {
                player.addPenalties(1);
            } catch (final MaxPenaltyException e) {
                Assert.fail(e.getMessage());
            }
        }
        Assert.assertEquals("The player should have " + 13 + " penalties",13,player.getPenalties());
    }

    /**
     * This method tests the addPenalties method of a player with too much adding.
     */
    @Test
    public void addTooMuchPenaltiesTest(){
        int tooMuch = 20;
        for(int i=1; i <=tooMuch;++i){
            try {
                player.addPenalties(1);
            } catch (final MaxPenaltyException e) {
                if(i<13) {
                    Assert.fail(e.getMessage());
                }else{
                    Assert.assertTrue("The player should have more than " + 13 + " penalties. The MaxPenaltyException is thrown",i>13);
                    return;
                }
            }
        }
    }

    /**
     * This method tests the
     */
    @Test
    public void setPenaltiesTest(){
        try {
            player.setPenalties(5);
        } catch (MaxPenaltyException e) {
            Assert.fail(e.getMessage());
        }
        try {
            player.setPenalties(10);
        } catch (MaxPenaltyException e) {
            Assert.fail(e.getMessage());
        }
        try {
            player.setPenalties(14);
            Assert.fail("I cant set the penalties more than "+13);
        } catch (MaxPenaltyException e) {

        }
        try {
            player.setPenalties(20);
            Assert.fail("I cant set the penalties more than "+13);
        } catch (MaxPenaltyException e) {

        }
        try {
            player.setPenalties(1);
        } catch (MaxPenaltyException e) {
            Assert.fail(e.getMessage());
        }
    }

   /**
     * This method tests the
     */
    @Test
    public void playerDiceTest(){
        // roll the dice first
        Assert.assertTrue(player.rollTheDice());
        List<Dice> dicesIn = new ArrayList<>(player.getDicesIn());
        Assert.assertEquals("Es können keine Würfel draussen sein!",player.getDicesOut().size(),0);
        Assert.assertEquals("Es müssen 3 Würfel im Becher sein!",player.getDicesIn().size(),3);
        // take a dice out
        final Dice dice1 = player.getDicesIn().get(0);
        try {
            Assert.assertTrue(player.takeDiceOut(dice1));
        } catch (DiceAlreadyOutException e) {
            e.printStackTrace();
            Assert.fail("Der Würfel darf nicht schon in der Liste enthalten sein!");
        }
        Assert.assertEquals("Es darf nur 1 Würfel draussen sein!",player.getDicesOut().size(),1);
        Assert.assertEquals("Es müssen 2 Würfel im Becher sein!",player.getDicesIn().size(),2);
        final Dice dice2 = player.getDicesIn().get(0);
        Assert.assertNotEquals("Die beiden Würfel dürfen nicht gleich sein!",dice1,dice2);


    }




}
