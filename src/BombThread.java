import java.io.Serializable;


public class BombThread extends Thread implements Serializable {

    private static final long serialVersionUID = 1113799434508346961L;

    private int X;
    private int Y;
    private BombCell bomb;
    GameBoardCreator gameBoardCreator;

    public BombThread(BombCell bombCell, GameBoardCreator gameBoardCreator, int x, int y) {
        this.bomb = bombCell;
        this.gameBoardCreator = gameBoardCreator;
        this.X = x;
        this.Y = y;
    }

    @Override
    public void run() {
        try {
            if (!gameBoardCreator.player.isBombControl()) {
                Thread.sleep(bomb.getBombExplosionTime() * 1000);

                explode();
                gameBoardCreator.createFrame();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void explode() throws InterruptedException {
        gameBoardCreator.player.setBombCount(gameBoardCreator.player.getBombCount() - 1);

        gameBoardCreator.gameComponents[X][Y] = new FieldCell();
        if (gameBoardCreator.player.isBombSet() && gameBoardCreator.player.getCurrentBomb() == bomb) {
            gameBoardCreator.killPlayer();
        }
        gameBoardCreator.player.getBombCells().remove(bomb);
        for (int i = X - bomb.getBombRadius(); i <= X + bomb.getBombRadius(); i++) {

            destroy(i, Y);
        }

        for (int j = Y - bomb.getBombRadius(); j <= Y + bomb.getBombRadius(); j++) {

            destroy(X, j);

        }
        gameBoardCreator.createFrame();
    }


    private void destroy(int i, int j) throws InterruptedException {

        if (i > -1 && j > -1 && i < gameBoardCreator.width + 2 && j < gameBoardCreator.height + 2) {
            if (gameBoardCreator.gameComponents[i][j].isExplosive()) {

            }
        }
    }


}

