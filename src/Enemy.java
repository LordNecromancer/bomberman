import java.io.Serializable;
import java.util.ArrayList;


public abstract class Enemy extends GameComponent implements Serializable {
    private static final long serialVersionUID = 1114799734505296969L;

    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {
        Enemy enemy = this;
        gameBoardCreator.setEnemyCount(gameBoardCreator.getEnemyCount() - 1);
        gameBoardCreator.addScore(20 * enemy.getLevel());
        gameBoardCreator.gameComponents[i][j] = new FieldCell();

    }

    private int level;
    private boolean isGhosting = false;
    private int sleep;
    private GameComponent disappearedObject = null;
    ArrayList<String> passableObjects = new ArrayList<>();

    Enemy() {
        super.setNeverPassable(true);
        super.setPassable(false);
    }

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    boolean isGhosting() {
        return isGhosting;
    }

    void setGhosting(boolean ghosting) {
        isGhosting = ghosting;
    }

    void setSleep(int sleep) {
        this.sleep = sleep;
    }

    GameComponent getDisappearedObject() {
        return disappearedObject;
    }

    void setDisappearedObject(GameComponent disappearedObject) {
        this.disappearedObject = disappearedObject;
    }
}
