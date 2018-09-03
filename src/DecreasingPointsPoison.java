import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingPointsPoison extends Poison implements Serializable {
    final private String type = "decreasePoints";
    private static final long serialVersionUID = 1113799434508276969L;


    GameBoardCreator gameBoardCreator;


    public DecreasingPointsPoison() {
        super.setType(type);
        gameBoardCreator = GameBoardCreator.gameBoardCreator;
    }

    public void doYourThing() {

        gameBoardCreator.addScore(-100);
    }
}



