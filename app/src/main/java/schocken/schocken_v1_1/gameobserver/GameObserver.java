package schocken.schocken_v1_1.gameobserver;

/**
 * Created by marco on 16.08.17.
 */

public interface GameObserver {

    /**
     * This method returns the max shots of the round
     * @return The max shots of the round.
     */
    int getMaxShotsOfRound();

    /**
     * This method tells the game observer that the player has finished.
     */
    void currentPlayerHasFinished();

}
