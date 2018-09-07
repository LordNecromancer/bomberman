import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasingRadiusPoison extends Poison implements Serializable {

    final private String type = "decreaseRadius";
    private static final long serialVersionUID = 1113799434508286969L;


    DecreasingRadiusPoison() {
        super.setType(type);
    }


    public void doYourThing() {
        if (GameBoardCreator.player.getBombRadius() > 1)

            GameBoardCreator.player.setBombRadius(GameBoardCreator.player.getBombRadius() - 1);
    }
}
