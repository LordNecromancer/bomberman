import java.io.Serializable;

/**
 * Created by Sun on 03/31/2018.
 */
public class BombCell extends GameComponent implements Serializable {

    private final String type = "bomb";
    private static final long serialVersionUID = 1113799434508346960L;

    private GameBoardCreator gameBoardCreator;
    private int bombRadius;
    private long bombExplosionTime = 5;
    private int X;

    private int Y;
    private BombThread bombThread;

    public BombCell(int bombRadius, long bombExplosionTime, GameBoardCreator gameBoardCreator, int x, int y) {
        super.setType(type);
        super.setPassable(false);
        super.setNeverPassable(true);

        this.bombRadius = bombRadius;
        this.bombExplosionTime = bombExplosionTime;
        this.gameBoardCreator = gameBoardCreator;
        this.X = x;
        this.Y = y;
        explode();


    }

    private void explode() {
        bombThread = new BombThread(this, gameBoardCreator, X, Y);
        bombThread.start();
    }

    public void explodeNow() throws InterruptedException {
        bombThread.explode();
    }

    public GameBoardCreator getGameBoardCreator() {
        return gameBoardCreator;
    }

    public void setGameBoardCreator(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public long getBombExplosionTime() {
        return bombExplosionTime;
    }

    public void setBombExplosionTime(long bombExplosionTime) {
        this.bombExplosionTime = bombExplosionTime;
    }

    public BombThread getBombThread() {
        return bombThread;
    }

    public void setBombThread(BombThread bombThread) {
        this.bombThread = bombThread;
    }

    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {
        BombCell bombCell = this;

        try {
            bombCell.getBombThread().explode();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

