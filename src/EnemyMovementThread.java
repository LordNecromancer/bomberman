import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Sun on 07/08/2018.
 */
public class EnemyMovementThread extends Thread implements Serializable {

    CreateBoard createBoard;
    private static final long serialVersionUID = 1193799434508296969L;

    private GameComponent up;
    private GameComponent down;
    private GameComponent right;
    private GameComponent left;
    private MoveEnemyRandomly moveEnemyRandomly = new MoveEnemyRandomly();
    int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    ;
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThread(CreateBoard createBoard) {
        this.createBoard = createBoard;
    }

    @Override
    public void run() {

        for (int p = 0; p < createBoard.enemies.size(); p++) {
            round.put(createBoard.enemies.get(p), -1);
        }

        int t = 0;
        while (true) {
            k = 0;
            t++;

            try {
                Thread.sleep(EnemyLvL1.sleep * 300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int p = 0; p < createBoard.enemies.size(); p++) {
                int k = 0;
                Enemy enemy = createBoard.enemies.get(p);
                for (int i = 0; i < createBoard.width + 2; i++) {
                    for (int j = 0; j < createBoard.height + 2 && k < 1; j++) {
                        if (createBoard.gameComponents[i][j] == enemy) {


                            findPossibleDirections(i, j);

                            k++;


                            if (enemy.type.equals("enemyLvL1")) {

                                moveEnemyLevelOne((EnemyLvL1) createBoard.gameComponents[i][j], i, j);

                            }

                            if (enemy.type.equals("enemyLvL2")) {
                                moveEnemyLevelTwo((EnemyLvL2) enemy, i, j);
                            }
                        }
                    }
                }
            }
            createBoard.createFrame();
        }
    }

    private void findPossibleDirections(int i, int j) {
        chooseDirection = new ArrayList<>();

        up = createBoard.gameComponents[i - 1][j];
        right = createBoard.gameComponents[i][j + 1];
        down = createBoard.gameComponents[i + 1][j];
        left = createBoard.gameComponents[i][j - 1];

        if (up.passable && up.type.equals("wall") && up.type.equals("obstacle")) {

            chooseDirection.add(up);
        }
        if (right.passable && right.type.equals("wall") && right.type.equals("obstacle")) {

            chooseDirection.add(right);
        }
        if (down.passable && down.type.equals("wall") && down.type.equals("obstacle")) {

            chooseDirection.add(down);
        }
        if (left.passable && left.type.equals("wall") && left.type.equals("obstacle")) {

            chooseDirection.add(left);
        }
    }


    private void moveEnemyLevelOne(EnemyLvL1 enemy, int i, int j) {
        int randomNum = getRandomDirection();
        if (randomNum != -1) {
            moveEnemyRandomly.move(createBoard, i, j, enemy, randomNum, chooseDirection);
        }
    }


    private void moveEnemyLevelTwo(EnemyLvL2 enemy, int i, int j) {


        if (round.get(enemy) == -1) {
            moveEnemyLevelTwoSmartly(enemy, i, j);
        } else {

            round.put(enemy, round.get(enemy) + 1);
            int randomNum = getRandomDirection();
            if (randomNum != -1) {
                moveEnemyRandomly.move(createBoard, i, j, enemy, randomNum, chooseDirection);
            }
            if (round.get(enemy) == 9) {
                round.put(enemy, -1);
            }

        }


    }

    private void moveEnemyLevelTwoSmartly(EnemyLvL2 enemy, int i, int j) {
        if (up == createBoard.player || down == createBoard.player || right == createBoard.player || left == createBoard.player) {
            createBoard.gameComponents[createBoard.player.playerPositionX][createBoard.player.playerPositionY] = enemy;
            createBoard.gameComponents[i][j] = new FieldCell();
            createBoard.killPlayer();
        }
        if (createBoard.player.playerPositionX > i && chooseDirection.contains(down)) {

            createBoard.gameComponents[i + 1][j] = enemy;
            createBoard.gameComponents[i][j] = new FieldCell();


        } else if (createBoard.player.playerPositionX < i && chooseDirection.contains(up)) {

            createBoard.gameComponents[i - 1][j] = enemy;
            createBoard.gameComponents[i][j] = new FieldCell();

        } else if (createBoard.player.playerPositionY > j && chooseDirection.contains(right)) {

            createBoard.gameComponents[i][j + 1] = enemy;
            createBoard.gameComponents[i][j] = new FieldCell();

        } else if (createBoard.player.playerPositionY < j && chooseDirection.contains(left)) {

            createBoard.gameComponents[i][j - 1] = enemy;
            createBoard.gameComponents[i][j] = new FieldCell();

        } else {
            round.put(enemy, 0);
            moveEnemyRandomly.move(createBoard, i, j, enemy, getRandomDirection(), chooseDirection);
        }
    }


    public int getRandomDirection() {

        Random random = new Random();

        if (chooseDirection.size() == 0) {
            return -1;
        }
        if (chooseDirection.size() == 1) {
            return 0;
        } else {
            int randomNum = random.nextInt(chooseDirection.size());

            return randomNum;
        }
    }
}
