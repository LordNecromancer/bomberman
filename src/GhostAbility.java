import java.io.Serializable;

/**
 * Created by Sun on 08/23/2018.
 */
public class GhostAbility extends PowerUps implements Serializable {
    GameBoardCreator gameBoardCreator;
    final private String type = "ghostAbility";


    public GhostAbility(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
        super.setType(type);
    }

    @Override
    public void doYourThing() {

        gameBoardCreator.player.setGhosting(true);
    }
}
