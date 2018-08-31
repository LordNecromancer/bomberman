import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public class EnemyLvL4 extends Enemy implements Serializable {
    final private String type = "enemyLvL4";
    int level = 4;
    static int sleep = 4;
    boolean isGhosting = true;
    GameComponent disappearedObject=new FieldCell();

    public EnemyLvL4() {
        super.level = level;
        super.type = type;
        super.isGhosting=isGhosting;
        super.disappearedObject=disappearedObject;
        super.passable = false;
        super.neverPassable = true;


    }
}
