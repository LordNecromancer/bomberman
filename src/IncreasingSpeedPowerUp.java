import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingSpeedPowerUp extends PowerUps implements Serializable {
    final private String type = "increaseSpeed";
    private static final long serialVersionUID = 1113799434508336969L;

    GameBoardCreator gameBoardCreator;

    IncreasingSpeedPowerUp() {
        super.setType(type);
    }

    public void doYourThing() {
        if (GameBoardCreator.player.getPlayerSpeed() > 0)
            GameBoardCreator.player.setPlayerSpeed(GameBoardCreator.player.getPlayerSpeed() - 1);
    }
}
