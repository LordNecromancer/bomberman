import java.io.Serializable;


public abstract class Enemy extends GameComponent implements Serializable {
    int level;
    boolean isGhosting = false;
    GameComponent disappearedObject = null;

    Enemy() {
        super.neverPassable = true;
    }

}
