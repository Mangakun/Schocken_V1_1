package schocken.schocken_v1_1.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        // TODO: reset einbauen !!
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
     * This method tests the block method.
     */
    @Test
    public void isBlockedTest(){
        // call method block
        player.block();
        // first the block flag should be true
        Assert.assertTrue("The block flag should be true", player.isBlocked());
        // second the player is finished
        Assert.assertTrue("The player should be finished", player.isFinished());
    }

    /**
     * This method tests the stay method.
     */
    @Test
    public void stayTest(){
        // call method stay
        try {
            player.stay();
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        // the player is finished
        Assert.assertTrue("The player should be finished", player.isFinished());
    }

    /**
     * This method tests the stay method.
     */
    @Test
    public void blindTest(){
        try {
            player.blind();
        } catch (final Exception e) {
            Assert.fail("The player can call \"blind\"");
        }
        // the player is finished
        Assert.assertTrue("The player should be finished", player.isFinished());
    }

    /**
     * This method tests the roll the dice method.
     */
    @Test
    public void rollTheDiceTest1(){
        // roll the dice first
        Assert.assertTrue(player.rollTheDice());
        Assert.assertFalse("The player should not be finished", player.isFinished());
        Assert.assertEquals("The player has played the dice once",1,player.getCurrentShots());
        // roll the dice second
        Assert.assertTrue(player.rollTheDice());
        Assert.assertFalse("The player should not be finished", player.isFinished());
        Assert.assertEquals("The player has played the dice twice",2,player.getCurrentShots() );
        // roll the dice third.
        Assert.assertTrue(player.rollTheDice());
        Assert.assertTrue("The player should be finished", player.isFinished());
        Assert.assertEquals("The player has played the dice three times",3,player.getCurrentShots() );
    }

    /**
     * This method tests the roll the dice method.
     */
    @Test
    public void rollTheDiceTest2(){
        // roll the dice first
        Assert.assertTrue(player.rollTheDice());
        Assert.assertFalse("The player should not be finished", player.isFinished());
        Assert.assertEquals("The player has played the dice once",1,player.getCurrentShots());
        // try to call blind
        try {
            player.blind();
            Assert.fail("The player cant call blind");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        // roll the dice first
        Assert.assertTrue(player.rollTheDice());
        Assert.assertFalse("The player should not be finished", player.isFinished());
        Assert.assertEquals("The player has played the dice twice",2,player.getCurrentShots());

        try {
            player.stay();
        } catch (Exception e) {
            Assert.fail("The player can call stay");
        }
        Assert.assertTrue("The player should be finished", player.isFinished());
        Assert.assertEquals("The player has played the dice twice",2,player.getCurrentShots());
        try {
            player.blind();
            Assert.fail("The player cant call blind");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        try {
            player.stay();
            Assert.fail("The player cant call stay");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the roll the dice method.
     */
    @Test
    public void rollTheDiceTest3(){
        // TODO Michelle fragen
    }

    /**
     * This method tests the roll the dice method.
     */
    @Test
    public void rollTheDiceTest4(){
        // TODO Michelle fragen
    }

    /**
     * This method tests the roll the dice method.
     */
    @Test
    public void rollTheDiceTest5(){
        // TODO Michelle fragen
    }

    /**
     * This method tests the roll the dice method.
     */
    @Test
    public void rollTheDiceTest6(){
        // TODO Michelle fragen
    }

    /**
     * This method tests the combination of the player and the dices.
     */
    @Test
    public void playerDiceTest(){

    }

}
