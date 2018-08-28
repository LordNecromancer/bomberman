import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreaseSpeed extends PowerUps implements Serializable {
    final private String type = "increaseSpeed";
    private static final long serialVersionUID = 1113799434508336969L;

    CreateBoard createBoard;

    public IncreaseSpeed(CreateBoard createBoard) {
        this.createBoard = createBoard;
        super.type = type;
    }

    public void doYourThing() {
        if (createBoard.player.playerSpeed > 0)
            createBoard.player.playerSpeed--;
    }
}
