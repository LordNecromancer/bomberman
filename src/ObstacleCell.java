import java.io.Serializable;

/**
 * Created by Sun on 05/31/2018.
 */
public class ObstacleCell extends Cell implements Serializable {

    private final String type = "obstacle";
    boolean hasDoor = false;
    PowerUps powerUps = null;
    Poison poison = null;

    public ObstacleCell() {
        super.type = type;
        super.passable = false;


    }
}
