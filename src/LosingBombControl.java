import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class LosingBombControl extends Poison implements Serializable {
    final private String type = "loseBombControl";
    private static final long serialVersionUID = 1113799434508346969L;


    public LosingBombControl() {

        super.type = type;
    }

    public void doYourThing() {
        CreatingGameBoard.player.bombControl = false;
    }
}
