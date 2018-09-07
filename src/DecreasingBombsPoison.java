import java.io.Serializable;


public class DecreasingBombsPoison extends Poison implements Serializable {
    final private String type = "decreaseBombs";
    private static final long serialVersionUID = 1113799434508676969L;


    DecreasingBombsPoison() {

        super.setType(type);
    }


    public void doYourThing() {
        if (GameBoardCreator.player.getBombNum() >= 1)

            GameBoardCreator.player.setBombNum(GameBoardCreator.player.getBombNum() - 1);
    }
}
