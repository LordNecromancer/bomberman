import java.io.Serializable;

/**
 * Created by Sun on 08/23/2018.
 */
public class GhostAbility extends PowerUps implements Serializable {
    CreatingGameBoard creatingGameBoard;
    final private String type = "ghostAbility";


    public GhostAbility(CreatingGameBoard creatingGameBoard) {
        this.creatingGameBoard = creatingGameBoard;
        super.type = type;
    }

    @Override
    public void doYourThing() {

        creatingGameBoard.player.isGhosting = true;
    }
}
