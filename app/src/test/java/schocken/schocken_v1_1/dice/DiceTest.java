package schocken.schocken_v1_1.dice;

import android.content.SyncStatusObserver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.player.dice.Dice;
import schocken.schocken_v1_1.player.dice.impl.DiceImpl;
import schocken.schocken_v1_1.player.exceptions.MaxPenaltyException;
import schocken.schocken_v1_1.player.impl.PlayerImpl;

/**
 * Created by marco on 14.08.17.
 */

public class DiceTest {

    /**
     * This method tests the initial dice test.
     */
    @Test
    public void initialDiceTest(){
        Dice dice = new DiceImpl();
        Assert.assertEquals(0,dice.getValue());
    }

    /**
     * This method tests the comparable interface
     */
    @Test
    public void comparableTest(){
        final List<Dice> dices = new ArrayList<Dice>();
        int maxDices = 5;
        for(int i=0; i< maxDices;++i){
            dices.add(new DiceImpl(i));
        }
        Collections.sort(dices);
        for(int i=0; i< maxDices;++i){
            Assert.assertEquals(maxDices-1-i,dices.get(i).getValue());
        }
    }

    /**
     * This method tests the comparable interface
     */
    @Test
    public void comparableTest2(){
        final List<Dice> dices = new ArrayList<Dice>();
        int maxDices = 5;
        final Random random = new Random();
        for(int i=0; i<maxDices;++i){
            dices.add(new DiceImpl(random.nextInt(6) + 1));

        }
        Collections.sort(dices);
        for(int i=1; i<maxDices;++i){
            if(dices.get(i-1).getValue()< dices.get(i).getValue()){
                Assert.fail("Es ist nicht absteigend sortiert worden!");
            }
        }
    }

    /**
     * This method tests the color of the dice.
     */
    @Test
    public void colorTest(){
        /**
         * TODO: implementation of test
         */

    }

    /**
     * This method tests the picture of the dice.
     */
    @Test
    public void pictureTest(){
        /**
         * TODO: implementation of test
         */
    }

}
