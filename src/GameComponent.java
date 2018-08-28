import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public abstract class GameComponent implements Serializable {
    String type;
    Boolean passable = false;
    boolean neverPassable = false;
    boolean isExplosive = true;
}
