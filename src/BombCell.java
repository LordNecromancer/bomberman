import java.io.Serializable;

/**
 * Created by Sun on 03/31/2018.
 */
public class BombCell extends Cell implements Serializable {

    private final String type = "bomb";
    private static final long serialVersionUID = 1113799434508346960L;

    CreateBoard createBoard;
    int bombRadius;
    long bombExplosionTime = 5;
    private int X;

    private int Y;
    BombThread bombThread;

    public BombCell(int bombRadius, long bombExplosionTime, CreateBoard createBoard, int x, int y) {
        super.type = type;
        super.passable = false;
        super.neverPassable = true;

        this.bombRadius = bombRadius;
        this.bombExplosionTime = bombExplosionTime;
        this.createBoard = createBoard;
        this.X = x;
        this.Y = y;
        explode();


    }

    private void explode() {
        bombThread = new BombThread(this, createBoard, X, Y);
        bombThread.start();
    }

    public void explodeNow() throws InterruptedException {
        bombThread.explode();
    }
}