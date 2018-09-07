import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingSpeedPoison extends Poison implements Serializable {
    final private String type = "decreaseSpeed";
    private static final long serialVersionUID = 1113799434508296969L;

    GameBoardCreator gameBoardCreator;

    DecreasingSpeedPoison() {
        this.gameBoardCreator = gameBoardCreator;
        super.setType(type);
    }


    public void doYourThing() {
        if (GameBoardCreator.player.getPlayerSpeed() > 0)
            GameBoardCreator.player.setPlayerSpeed(GameBoardCreator.player.getPlayerSpeed() + 1);
    }
}
