import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL3 extends Enemy implements Serializable {
    final private String type = "enemyLvL3";
    private int level = 3;
    static int sleep = 4;

    public EnemyLvL3() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setPassable(false);
        super.setNeverPassable(true);


    }
}
