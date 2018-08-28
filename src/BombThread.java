import java.io.Serializable;


public class BombThread extends Thread implements Serializable {

    private static final long serialVersionUID = 1113799434508346961L;

    private int X;
    private int Y;
    private BombCell bomb;
    CreateBoard createBoard;

    public BombThread(BombCell bombCell, CreateBoard createBoard, int x, int y) {
        this.bomb = bombCell;
        this.createBoard = createBoard;
        this.X = x;
        this.Y = y;
    }

    @Override
    public void run() {
        try {
            if (!createBoard.player.bombControl) {
                Thread.sleep(bomb.bombExplosionTime * 1000);

                explode();
                createBoard.bombCount--;
                // createBoard.createFrame();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void explode() throws InterruptedException {

        createBoard.bombCells.remove(bomb);
        for (int i = X - bomb.bombRadius; i <= X + bomb.bombRadius; i++) {

            destroy(i, Y);
        }

        for (int j = Y - bomb.bombRadius; j <= Y + bomb.bombRadius; j++) {

            destroy(X, j);

        }
        createBoard.createFrame();
    }


    private void destroy(int i, int j) throws InterruptedException {

        if (i > -1 && j > -1 && i < createBoard.width + 2 && j < createBoard.height + 2) {
            if (createBoard.gameComponents[i][j].isExplosive) {
                if (createBoard.gameComponents[i][j].type.equals("obstacle")) {
                    destroyObstacle(createBoard.gameComponents[i], j);


                } else {
                    if (createBoard.player.bombSet && createBoard.player.currentBomb == bomb) {
                        createBoard.killPlayer();
                    }
                    if (createBoard.gameComponents[i][j].type.equals("player")) {
                        createBoard.killPlayer();
                    }

                    if (createBoard.gameComponents[i][j].type.equals("bomb") && (i != X || j != Y)) {

                        destroyOtherBombs(createBoard.gameComponents[i][j]);
                    }
                    if (createBoard.gameComponents[i][j] instanceof Enemy) {

                        destroyEnemy(createBoard.gameComponents[i][j]);
                    }

                    createBoard.gameComponents[i][j] = new FieldCell();
                }
            }
        }
    }

    private void destroyEnemy(GameComponent gameComponent) {
        Enemy enemy = (Enemy) gameComponent;
        createBoard.enemyCount--;
        createBoard.addScore(20 * enemy.level);
    }

    private void destroyOtherBombs(GameComponent gameComponent) throws InterruptedException {
        BombCell bombCell = (BombCell) gameComponent;
        if (createBoard.player.bombSet && createBoard.player.currentBomb == bomb) {
            createBoard.killPlayer();
        }
        bombCell.bombThread.explode();
    }

    private void destroyObstacle(GameComponent[] gameComponent, int j) {
        ObstacleCell obstacleCell = (ObstacleCell) gameComponent[j];
        createBoard.addScore(CreateBoard.obstacleScore);
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

