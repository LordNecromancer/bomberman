import java.io.Serializable;


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

    Enemy() {
        super.setNeverPassable(true);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isGhosting() {
        return isGhosting;
    }

    public void setGhosting(boolean ghosting) {
        isGhosting = ghosting;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public GameComponent getDisappearedObject() {
        return disappearedObject;
    }

    public void setDisappearedObject(GameComponent disappearedObject) {
        this.disappearedObject = disappearedObject;
    }
}
