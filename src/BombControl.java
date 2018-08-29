import java.io.Serializable;


public class BombControl extends PowerUps implements Serializable {
    private static final long serialVersionUID = 1113799434508676069L;

    final private String type = "bombControl";

    CreateBoard createBoard;

    BombControl() {


        super.type = type;
    }

    public void doYourThing() {
        CreateBoard.player.bombControl = true;
    }
}
