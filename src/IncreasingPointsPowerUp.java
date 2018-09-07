import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingPointsPowerUp extends PowerUps implements Serializable {
    final private String type = "increasePoints";
    private static final long serialVersionUID = 1113799434508316969L;

    GameBoardCreator gameBoardCreator;

    IncreasingPointsPowerUp() {
        super.setType(type);
    }

    public void doYourThing() {
        GameBoardCreator.gameBoardCreator.addScore(100);
    }
}
