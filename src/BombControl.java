import java.io.Serializable;


public class BombControl extends PowerUps implements Serializable {
    private static final long serialVersionUID = 1113799434508676069L;

    final private String type = "bombControl";

    CreatingGameBoard creatingGameBoard;

    BombControl() {


        super.type = type;
    }

    public void doYourThing() {
        CreatingGameBoard.player.bombControl = true;
    }
}
