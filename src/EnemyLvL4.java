import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL4 extends Enemy implements Serializable {
    int level = 4;
    static int sleep = 4;
    boolean isGhosting = false;
    Cell disappearedObject;

    public EnemyLvL4() {
        super.level = level;
        super.type = type;
        super.passable = false;
        super.neverPassable = true;


    }
}
