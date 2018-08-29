import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreaseSpeed extends Poison implements Serializable {
    final private String type = "decreaseSpeed";
    private static final long serialVersionUID = 1113799434508296969L;

    CreateBoard createBoard;

    public DecreaseSpeed() {
        this.createBoard = createBoard;
        super.type = type;
    }

    public void doYourThing() {
        if (CreateBoard.player.playerSpeed > 0)
            CreateBoard.player.playerSpeed++;
    }
}
