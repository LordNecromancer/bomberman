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

    public Boolean getPassable() {
        return passable;
    }

    public void setPassable(Boolean passable) {
        this.passable = passable;
    }

    public boolean isNeverPassable() {
        return neverPassable;
    }

    public void setNeverPassable(boolean neverPassable) {
        this.neverPassable = neverPassable;
    }

    public boolean isExplosive() {
        return isExplosive;
    }

    public void setExplosive(boolean explosive) {
        isExplosive = explosive;
    }
}
