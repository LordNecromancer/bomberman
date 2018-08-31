import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sun on 07/25/2018.
 */
public class MovingEnemyRandomly implements Serializable {
    CreatingGameBoard creatingGameBoard;

    public synchronized void move(CreatingGameBoard creatingGameBoard, int i, int j, Enemy enemy, int randomNum, ArrayList<GameComponent> chooseDirection) {

        this.creatingGameBoard = creatingGameBoard;
        GameComponent up = creatingGameBoard.gameComponents[i - 1][j];
        GameComponent right = creatingGameBoard.gameComponents[i][j + 1];
        GameComponent down = creatingGameBoard.gameComponents[i + 1][j];
        GameComponent left = creatingGameBoard.gameComponents[i][j - 1];
        if (randomNum != -1) {
            if (chooseDirection.get(randomNum) == creatingGameBoard.player) {
                creatingGameBoard.killPlayer();
                creatingGameBoard.enemyMove2.stop();
                creatingGameBoard.enemyMove.stop();
            }


            if (chooseDirection.get(randomNum) == up) {

                if (up.passable && !enemy.isGhosting) {
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i - 1, j);

                }
                creatingGameBoard.gameComponents[i - 1][j] = enemy;


            } else if (chooseDirection.get(randomNum) == right) {
                if (right.passable && !enemy.isGhosting) {
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i, j + 1);

                }
                creatingGameBoard.gameComponents[i][j + 1] = enemy;


            } else if (chooseDirection.get(randomNum) == down) {

                if (down.passable && !enemy.isGhosting) {
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i + 1, j);

                }
                creatingGameBoard.gameComponents[i + 1][j] = enemy;


            } else if (chooseDirection.get(randomNum) == left) {

                if (left.passable && !enemy.isGhosting) {
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();
                } else {
                    currentLocation(enemy, i, j);
                    nextLocation(enemy, i, j - 1);

                }
                creatingGameBoard.gameComponents[i][j - 1] = enemy;


            }
        }
    }

    private void nextLocation(Enemy enemy, int i, int j) {
        if (!creatingGameBoard.gameComponents[i][j].neverPassable) {

            enemy.disappearedObject = creatingGameBoard.gameComponents[i][j];

        }
    }

    private void currentLocation(Enemy enemy, int i, int j) {


        creatingGameBoard.setGameComponents(i, j, enemy.disappearedObject);
        enemy.disappearedObject = null;


    }
}
