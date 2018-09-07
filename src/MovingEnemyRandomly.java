import java.io.Serializable;
import java.util.ArrayList;

class MovingEnemyRandomly implements Serializable {
    private static final long serialVersionUID = 1113819434508676969L;

    GameBoardCreator gameBoardCreator;

    synchronized void move(GameBoardCreator gameBoardCreator, int i, int j, Enemy enemy, int randomNum, ArrayList<GameComponent> chooseDirection) {

        this.gameBoardCreator = gameBoardCreator;
        GameComponent up = gameBoardCreator.gameComponents[i - 1][j];
        GameComponent right = gameBoardCreator.gameComponents[i][j + 1];
        GameComponent down = gameBoardCreator.gameComponents[i + 1][j];
        GameComponent left = gameBoardCreator.gameComponents[i][j - 1];
        if (randomNum != -1) {
            if (chooseDirection.get(randomNum) == gameBoardCreator.player) {
                gameBoardCreator.player.killPlayer();
//                gameBoardCreator.getEnemyMove2().stop();
//                gameBoardCreator.getEnemyMove().stop();
            }


            else if (chooseDirection.get(randomNum) == up) {

                if (up.getPassable() && !enemy.isGhosting()) {
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i - 1, j);

                }
                gameBoardCreator.gameComponents[i - 1][j] = enemy;


            } else if (chooseDirection.get(randomNum) == right) {
                if (right.getPassable() && !enemy.isGhosting()) {
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i, j + 1);

                }
                gameBoardCreator.gameComponents[i][j + 1] = enemy;


            } else if (chooseDirection.get(randomNum) == down) {

                if (down.getPassable() && !enemy.isGhosting()) {
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i + 1, j);

                }
                gameBoardCreator.gameComponents[i + 1][j] = enemy;


            } else if (chooseDirection.get(randomNum) == left) {

                if (left.getPassable() && !enemy.isGhosting()) {
                    gameBoardCreator.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i, j - 1);

                }
                gameBoardCreator.gameComponents[i][j - 1] = enemy;


            }
        }
    }

    private void nextLocation(Enemy enemy, int i, int j) {
        if (!gameBoardCreator.gameComponents[i][j].isNeverPassable()) {

            enemy.setDisappearedObject(gameBoardCreator.gameComponents[i][j]);

        }
    }

    private void currentLocation(Enemy enemy, int i, int j) {


        gameBoardCreator.setGameComponents(i, j, enemy.getDisappearedObject());
        enemy.setDisappearedObject(new FieldCell());


    }
}
