import java.io.Serializable;

/**
 * Created by Sun on 06/06/2018.
 */
public abstract class GameComponent implements Serializable {
    private static final long serialVersionUID = 1114799434505296969L;

    private String type;
    private Boolean passable = false;
    private boolean neverPassable = false;
    private boolean isExplosive = true;

    public abstract void destroy(GameBoardCreator gameBoardCreator, int i, int j);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    Boolean getPassable() {
        return passable;
    }

    void setPassable(Boolean passable) {
        this.passable = passable;
    }

    boolean isNeverPassable() {
        return neverPassable;
    }

    public void setNeverPassable(boolean neverPassable) {
        this.neverPassable = neverPassable;
    }

    boolean isExplosive() {
        return isExplosive;
    }

    void setExplosive(boolean explosive) {
        isExplosive = explosive;
    }
}
