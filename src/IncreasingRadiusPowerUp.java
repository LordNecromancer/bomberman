import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingRadiusPowerUp extends PowerUps implements Serializable {
    final private String type = "increaseRadius";
    private static final long serialVersionUID = 1114799434508316969L;


    IncreasingRadiusPowerUp() {
        super.setType(type);
    }

    public void doYourThing() {
        GameBoardCreator.player.setBombRadius(GameBoardCreator.player.getBombRadius() + 1);
    }
}
