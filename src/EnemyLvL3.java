import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL3 extends Enemy implements Serializable {
    private static final long serialVersionUID = 1193799634508296969L;

    final private String type = "enemyLvL3";
    private int level = 3;
    static int sleep = 4;

    public EnemyLvL3() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setNeverPassable(true);
        passableObjects.add("FieldCell");
        passableObjects.add("Player");


    }
}
