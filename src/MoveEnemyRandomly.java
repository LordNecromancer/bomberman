import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sun on 07/25/2018.
 */
public class MoveEnemyRandomly implements Serializable {
    public synchronized void move(CreateBoard createBoard, int i, int j, Enemy enemy, int randomNum, ArrayList<GameComponent> chooseDirection) {

        GameComponent up = createBoard.gameComponents[i - 1][j];
        GameComponent right = createBoard.gameComponents[i][j + 1];
        GameComponent down = createBoard.gameComponents[i + 1][j];
        GameComponent left = createBoard.gameComponents[i][j - 1];
        if (randomNum != -1) {
            if (chooseDirection.get(randomNum) == createBoard.player) {
                createBoard.killPlayer();
                createBoard.enemyMove2.stop();
                createBoard.enemyMove.stop();
            }


            if (chooseDirection.get(randomNum) == up) {

                createBoard.gameComponents[i - 1][j] = enemy;
                createBoard.gameComponents[i][j] = new FieldCell();

            } else if (chooseDirection.get(randomNum) == right) {

                createBoard.gameComponents[i][j + 1] = enemy;
                createBoard.gameComponents[i][j] = new FieldCell();

            } else if (chooseDirection.get(randomNum) == down) {


                createBoard.gameComponents[i + 1][j] = enemy;
                createBoard.gameComponents[i][j] = new FieldCell();

            } else if (chooseDirection.get(randomNum) == left) {

                createBoard.gameComponents[i][j - 1] = enemy;
                createBoard.gameComponents[i][j] = new FieldCell();

            }
        }
    }
}
