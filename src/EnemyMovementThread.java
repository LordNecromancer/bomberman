import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Sun on 07/08/2018.
 */
public class EnemyMovementThread extends Thread implements Serializable {

    GameBoardCreator gameBoardCreator;
    private static final long serialVersionUID = 1193799434508296969L;

    private GameComponent up;
    private GameComponent down;
    private GameComponent right;
    private GameComponent left;
    private MovingEnemyRandomly movingEnemyRandomly = new MovingEnemyRandomly();
    private int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThread(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;
    }

    @Override
    public void run() {

        for (int p = 0; p < gameBoardCreator.getEnemies().size(); p++) {
            round.put(gameBoardCreator.getEnemies().get(p), -1);
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

            for (int p = 0; p < gameBoardCreator.getEnemies().size(); p++) {
                int k = 0;
                Enemy enemy = gameBoardCreator.getEnemies().get(p);
                for (int i = 0; i < gameBoardCreator.width + 2; i++) {
                    for (int j = 0; j < gameBoardCreator.height + 2 && k < 1; j++) {
                        if (gameBoardCreator.gameComponents[i][j] == enemy) {


                            findPossibleDirections(enemy, i, j);

                            k++;


                            if (enemy.getType().equals("enemyLvL1")) {

                                moveEnemyLevelOne((EnemyLvL1) gameBoardCreator.gameComponents[i][j], i, j);

                            }

                            if (enemy.getType().equals("enemyLvL2")) {
                                moveEnemyLevelTwo((EnemyLvL2) enemy, i, j);
                            }
                        }
                    }
                }
            }
            gameBoardCreator.createFrame();
        }
    }

    private void findPossibleDirections(Enemy enemy, int i, int j) {
        chooseDirection = new ArrayList<>();

        up = gameBoardCreator.gameComponents[i - 1][j];
        right = gameBoardCreator.gameComponents[i][j + 1];
        down = gameBoardCreator.gameComponents[i + 1][j];
        left = gameBoardCreator.gameComponents[i][j - 1];

        if (enemy.passableObjects.contains(up.getClass().getName())) {

            chooseDirection.add(up);
        }
        if (enemy.passableObjects.contains(right.getClass().getName())) {

            chooseDirection.add(right);
        }
        if (enemy.passableObjects.contains(down.getClass().getName())) {

            chooseDirection.add(down);
        }
        if (enemy.passableObjects.contains(left.getClass().getName())) {

            chooseDirection.add(left);
        }
    }


    private void moveEnemyLevelOne(EnemyLvL1 enemy, int i, int j) {
        int randomNum = getRandomDirection();
        if (randomNum != -1) {
            movingEnemyRandomly.move(gameBoardCreator, i, j, enemy, randomNum, chooseDirection);
        }
    }


    private void moveEnemyLevelTwo(EnemyLvL2 enemy, int i, int j) {


        if (round.get(enemy) == -1) {
            moveEnemyLevelTwoSmartly(enemy, i, j);
        } else {

            round.put(enemy, round.get(enemy) + 1);
            int randomNum = getRandomDirection();
            if (randomNum != -1) {
                movingEnemyRandomly.move(gameBoardCreator, i, j, enemy, randomNum, chooseDirection);
            }
            if (round.get(enemy) == 9) {
                round.put(enemy, -1);
            }

        }


    }

    private void moveEnemyLevelTwoSmartly(EnemyLvL2 enemy, int i, int j) {
        if (up == gameBoardCreator.player || down == gameBoardCreator.player || right == gameBoardCreator.player || left == gameBoardCreator.player) {
            gameBoardCreator.gameComponents[gameBoardCreator.player.getPlayerPositionX()][gameBoardCreator.player.getPlayerPositionY()] = enemy;
            gameBoardCreator.gameComponents[i][j] = new FieldCell();
            gameBoardCreator.killPlayer();
        }
        if (gameBoardCreator.player.getPlayerPositionX() > i && chooseDirection.contains(down)) {

            gameBoardCreator.gameComponents[i + 1][j] = enemy;
            gameBoardCreator.gameComponents[i][j] = new FieldCell();


        } else if (gameBoardCreator.player.getPlayerPositionX() < i && chooseDirection.contains(up)) {

            gameBoardCreator.gameComponents[i - 1][j] = enemy;
            gameBoardCreator.gameComponents[i][j] = new FieldCell();

        } else if (gameBoardCreator.player.getPlayerPositionY() > j && chooseDirection.contains(right)) {

            gameBoardCreator.gameComponents[i][j + 1] = enemy;
            gameBoardCreator.gameComponents[i][j] = new FieldCell();

        } else if (gameBoardCreator.player.getPlayerPositionY() < j && chooseDirection.contains(left)) {

            gameBoardCreator.gameComponents[i][j - 1] = enemy;
            gameBoardCreator.gameComponents[i][j] = new FieldCell();

        } else {
            round.put(enemy, 0);
            movingEnemyRandomly.move(gameBoardCreator, i, j, enemy, getRandomDirection(), chooseDirection);
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
