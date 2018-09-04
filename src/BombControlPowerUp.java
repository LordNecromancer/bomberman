import java.io.Serializable;


public class BombControlPowerUp extends PowerUps implements Serializable {
    private static final long serialVersionUID = 1113799434508676069L;

    final private String type = "bombControl";

    GameBoardCreator gameBoardCreator;

    BombControlPowerUp() {


        super.setType(type);
    }


    public void doYourThing() {
        GameBoardCreator.player.setBombControl(true);
    }
}
