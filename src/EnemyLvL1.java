import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL1 extends Enemy implements Serializable {
    private static final long serialVersionUID = 1119342734505296969L;


    final private String type = "enemyLvL1";
    private int level = 1;
    static int sleep = 8;

    public EnemyLvL1() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setPassable(false);
        super.setNeverPassable(true);
    }
}
