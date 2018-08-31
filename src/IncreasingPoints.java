import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingPoints extends PowerUps implements Serializable {
    final private String type = "increasePoints";
    private static final long serialVersionUID = 1113799434508316969L;

    CreatingGameBoard creatingGameBoard;

    public IncreasingPoints() {
        super.type = type;
    }

    public void doYourThing() {
        CreatingGameBoard.creatingGameBoard.addScore(100);
    }
}
