import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL1 extends Enemy implements Serializable {

    final private String type = "enemyLvL1";
    int level = 1;
    static int sleep = 8;

    public EnemyLvL1() {
        super.level = level;
        super.type = type;
        super.passable = false;
        super.neverPassable = true;
    }
}
