import java.io.Serializable;


public class BombThread extends Thread implements Serializable {

    private static final long serialVersionUID = 1113799434508346961L;

    private int X;
    private int Y;
    private BombCell bomb;
    CreatingGameBoard creatingGameBoard;

    public BombThread(BombCell bombCell, CreatingGameBoard creatingGameBoard, int x, int y) {
        this.bomb = bombCell;
        this.creatingGameBoard = creatingGameBoard;
        this.X = x;
        this.Y = y;
    }

    @Override
    public void run() {
        try {
            if (!creatingGameBoard.player.bombControl) {
                Thread.sleep(bomb.bombExplosionTime * 1000);

                explode();
                // creatingGameBoard.createFrame();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void explode() throws InterruptedException {
        creatingGameBoard.player.bombCount--;


        creatingGameBoard.player.bombCells.remove(bomb);
        for (int i = X - bomb.bombRadius; i <= X + bomb.bombRadius; i++) {

            destroy(i, Y);
        }

        for (int j = Y - bomb.bombRadius; j <= Y + bomb.bombRadius; j++) {

            destroy(X, j);

        }
        creatingGameBoard.createFrame();
    }


    private void destroy(int i, int j) throws InterruptedException {

        if (i > -1 && j > -1 && i < creatingGameBoard.width + 2 && j < creatingGameBoard.height + 2) {
            if (creatingGameBoard.gameComponents[i][j].isExplosive) {
                if (creatingGameBoard.gameComponents[i][j].type.equals("obstacle")) {
                    destroyObstacle(creatingGameBoard.gameComponents[i], j);


                } else {
                    if (creatingGameBoard.player.bombSet && creatingGameBoard.player.currentBomb == bomb) {
                        creatingGameBoard.killPlayer();
                    }
                    if (creatingGameBoard.gameComponents[i][j].type.equals("player")) {
                        creatingGameBoard.killPlayer();
                    }

                    if (creatingGameBoard.gameComponents[i][j].type.equals("bomb") && (i != X || j != Y)) {

                        destroyOtherBombs(creatingGameBoard.gameComponents[i][j]);
                    }
                    if (creatingGameBoard.gameComponents[i][j] instanceof Enemy) {

                        destroyEnemy(creatingGameBoard.gameComponents[i][j]);
                    }

                    creatingGameBoard.gameComponents[i][j] = new FieldCell();
                }
            }
        }
    }

    private void destroyEnemy(GameComponent gameComponent) {
        Enemy enemy = (Enemy) gameComponent;
        creatingGameBoard.enemyCount--;
        creatingGameBoard.addScore(20 * enemy.level);
    }

    private void destroyOtherBombs(GameComponent gameComponent) throws InterruptedException {
        BombCell bombCell = (BombCell) gameComponent;
        if (creatingGameBoard.player.bombSet && creatingGameBoard.player.currentBomb == bomb) {
            creatingGameBoard.killPlayer();
        }
        bombCell.bombThread.explode();
    }

    private void destroyObstacle(GameComponent[] gameComponent, int j) {
        ObstacleCell obstacleCell = (ObstacleCell) gameComponent[j];
        creatingGameBoard.addScore(CreatingGameBoard.obstacleScore);
        if (obstacleCell.hasDoor) {
            gameComponent[j] = new Door();


        } else if (obstacleCell.powerUps != null) {
            gameComponent[j] = obstacleCell.powerUps;


        } else if (obstacleCell.poison != null) {
            gameComponent[j] = obstacleCell.poison;


        } else {
            gameComponent[j] = new FieldCell();
        }
    }

}

