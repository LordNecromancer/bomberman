import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL4 extends Enemy implements Serializable {
    private static final long serialVersionUID = 1193799534508296969L;

    final private String type = "enemyLvL4";
    private int level = 4;
    static int sleep = 4;
    private boolean isGhosting = true;
    private GameComponent disappearedObject = new FieldCell();

    EnemyLvL4() {
        super.setLevel(level);
        super.setType(type);
        super.setSleep(sleep);
        super.setGhosting(isGhosting);
        super.setDisappearedObject(disappearedObject);
        super.setNeverPassable(true);
        super.setPassable(false);

        passableObjects.add("FieldCell");
        passableObjects.add("ObstacleCell");
        passableObjects.add("WallCell");
        passableObjects.add("Player");


    }
}
