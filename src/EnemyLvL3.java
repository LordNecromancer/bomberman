import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL3 extends Enemy implements Serializable {
    final private String type = "enemyLvL3";
    int level = 3;
    static int sleep = 4;

    public EnemyLvL3() {
        super.level = level;
        super.type = type;
        super.passable = false;
        super.neverPassable = true;


    }
}
