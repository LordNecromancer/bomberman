import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class ShieldAbilityPowerUp extends PowerUps implements Serializable {
    final private String type = "increasePoints";
    private static final long serialVersionUID = 1213799434508316969L;

    GameBoardCreator gameBoardCreator;

    ShieldAbilityPowerUp() {
        super.setType(type);
    }

    public void doYourThing() {
        GameBoardCreator.player.putPlayerOnShield();
    }
}
