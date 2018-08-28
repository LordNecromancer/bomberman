import java.io.Serializable;

/**
 * Created by Sun on 08/23/2018.
 */
public class GhostAbility extends PowerUps implements Serializable {
    CreateBoard createBoard;
    final private String type = "ghostAbility";


    public GhostAbility(CreateBoard createBoard) {
        this.createBoard = createBoard;
        super.type = type;
    }

    @Override
    public void doYourThing() {

        createBoard.player.isGhosting = true;
    }
}
