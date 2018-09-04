import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL2 extends Enemy implements Serializable {
    final private String type = "enemyLvL2";
    private int level = 2;
    static int sleep = 8;

    public EnemyLvL2() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setNeverPassable(true);
        passableObjects.add("FieldCell");



    }
}
