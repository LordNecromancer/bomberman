import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingBombsPowerUp extends PowerUps implements Serializable {

    final private String type = "increaseBombs";
    private static final long serialVersionUID = 1113799434508306969L;


    IncreasingBombsPowerUp() {
        super.setType(type);
    }

    public void doYourThing() {
        GameBoardCreator.player.setBombNum(GameBoardCreator.player.getBombNum() + 1);
    }

}
