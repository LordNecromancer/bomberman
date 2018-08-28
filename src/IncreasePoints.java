import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasePoints extends PowerUps implements Serializable {
    final private String type = "increasePoints";
    private static final long serialVersionUID = 1113799434508316969L;

    CreateBoard createBoard;

    public IncreasePoints(CreateBoard createBoard) {
        super.type = type;
        this.createBoard = createBoard;
    }

    public void doYourThing() {
        createBoard.addScore(100);
    }
}
