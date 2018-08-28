import java.io.Serializable;


public abstract class Enemy extends GameComponent implements Serializable {
    int level;

    Enemy() {
        super.neverPassable = true;
    }

}
