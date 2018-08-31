import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingRadius extends PowerUps implements Serializable {
    final private String type = "increaseRadius";
    private static final long serialVersionUID = 1114799434508316969L;


    public IncreasingRadius() {
        super.type = type;
    }

    public void doYourThing() {
        CreatingGameBoard.player.bombRadius++;
    }
}
