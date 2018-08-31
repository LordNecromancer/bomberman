import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreaseRadius extends Poison implements Serializable {

    final private String type = "decreaseRadius";
    private static final long serialVersionUID = 1113799434508286969L;


    public DecreaseRadius() {
        super.type = type;
    }

    public void doYourThing() {
        if (CreatingGameBoard.player.bombRadius > 1)

            CreatingGameBoard.player.bombRadius--;
    }
}
