import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL2 extends Enemy implements Serializable {
    final private String type = "enemyLvL2";
    int level = 2;
    boolean isRandom = false;
    static int sleep = 8;

    public EnemyLvL2() {
        super.level = level;
        super.type = type;
        super.passable = false;
        super.neverPassable = true;


    }
}
