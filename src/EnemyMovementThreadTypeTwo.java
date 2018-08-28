import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class EnemyMovementThreadTypeTwo extends Thread implements Serializable {


    CreateBoard createBoard;
    private static final long serialVersionUID = 1183799434508296969L;

    private GameComponent up;
    private GameComponent down;
    private GameComponent right;
    private GameComponent left;
    private MoveEnemyRandomly moveEnemyRandomly = new MoveEnemyRandomly();
    int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThreadTypeTwo(CreateBoard createBoard) {
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
                Thread.sleep(EnemyLvL3.sleep * 300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (int p = 0; p < createBoard.enemies.size(); p++) {
                int k = 0;
                Enemy enemy = createBoard.enemies.get(p);
                for (int i = 0; i < createBoard.width + 2; i++) {
                    for (int j = 0; j < createBoard.height + 2 && k < 1; j++) {
                        if (createBoard.gameComponents[i][j] == enemy) {


                            chooseDirection = new ArrayList<>();

                            up = createBoard.gameComponents[i - 1][j];
                            right = createBoard.gameComponents[i][j + 1];
                            down = createBoard.gameComponents[i + 1][j];
                            left = createBoard.gameComponents[i][j - 1];
                            if (enemy.type.equals("enemyLvL4")) {
                                if (i - 1 > 0 && !(createBoard.gameComponents[i - 1][j].neverPassable)) {
                                    up.passable = true;
                                }
                                if (i + 1 > 0 && !(createBoard.gameComponents[i + 1][j].neverPassable)) {
                                    down.passable = true;
                                }
                                if (j - 1 > 0 && !(createBoard.gameComponents[i][j - 1].neverPassable)) {

                                    left.passable = true;
                                }
                                if (j + 1 > 0 && !(createBoard.gameComponents[i][j + 1].neverPassable)) {
                                    right.passable = true;
                                }


                                if (up.passable) {

                                    chooseDirection.add(up);

                                }
                                if (right.passable) {

                                    chooseDirection.add(right);

                                }
                                if (down.passable) {

                                    chooseDirection.add(down);

                                }
                                if (left.passable) {


                                    chooseDirection.add(left);

                                }


                            } else {
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
                            if (enemy.type.equals("enemyLvL3") || enemy.type.equals("enemyLvL4")) {

                                moveEnemyLevelThree((Enemy) createBoard.gameComponents[i][j], i, j);

                            }
                            k++;
                        }

                    }
                }
            }
            createBoard.createFrame();
        }
    }


    private void moveEnemyLevelThree(Enemy enemy, int i, int j) {


        if (round.get(enemy) == -1) {
            if (up == createBoard.player || down == createBoard.player || right == createBoard.player || left == createBoard.player) {
                createBoard.gameComponents[createBoard.player.playerPositionX][createBoard.player.playerPositionY] = enemy;
                createBoard.gameComponents[i][j] = new FieldCell();
                createBoard.killPlayer();
                this.stop();
            }
            if (createBoard.player.playerPositionX > i && chooseDirection.contains(down)) {


                if (enemy.type.equals("enemyLvL4")) {

                    nextLocation((EnemyLvL4) enemy, i, j);
                    currentLocation((EnemyLvL4) enemy, i + 1, j);
                } else {
                    createBoard.gameComponents[i][j] = new FieldCell();

                }
                createBoard.gameComponents[i + 1][j] = enemy;

            } else if (createBoard.player.playerPositionX < i && chooseDirection.contains(up)) {

                if (enemy.type.equals("enemyLvL4")) {

                    nextLocation((EnemyLvL4) enemy, i, j);
                    currentLocation((EnemyLvL4) enemy, i - 1, j);
                } else {
                    createBoard.gameComponents[i][j] = new FieldCell();

                }
                createBoard.gameComponents[i - 1][j] = enemy;

            } else if (createBoard.player.playerPositionY > j && chooseDirection.contains(right)) {

                if (enemy.type.equals("enemyLvL4")) {

                    nextLocation((EnemyLvL4) enemy, i, j);
                    currentLocation((EnemyLvL4) enemy, i, j + 1);
                } else {
                    createBoard.setGameComponents(i, j, new FieldCell());

                }
                createBoard.gameComponents[i][j + 1] = enemy;

            } else if (createBoard.player.playerPositionY < j && chooseDirection.contains(left)) {

                if (enemy.type.equals("enemyLvL4")) {

                    nextLocation((EnemyLvL4) enemy, i, j);
                    currentLocation((EnemyLvL4) enemy, i, j - 1);
                } else {
                    createBoard.setGameComponents(i, j, new FieldCell());

                }
                createBoard.setGameComponents(i, j - 1, enemy);
            } else {
                round.put(enemy, 0);
            }
        } else {

            round.put(enemy, round.get(enemy) + 1);
            if (enemy.type.equals("enemyLvL4")) {
                if (up.type.equals("wall") || up.type.equals("obstacle")) {
                    up.passable = false;
                    chooseDirection.remove(up);
                }
                if (down.type.equals("wall") || down.type.equals("obstacle")) {
                    down.passable = false;
                    chooseDirection.remove(down);
                }
                if (right.type.equals("wall") || right.type.equals("obstacle")) {
                    right.passable = false;
                    chooseDirection.remove(right);
                }
                if (left.type.equals("wall") || left.type.equals("obstacle")) {
                    left.passable = false;
                    chooseDirection.remove(left);
                }
            }
            int randomNum = getRandomDirection();
            moveEnemyRandomly.move(createBoard, i, j, enemy, randomNum, chooseDirection);
            if (round.get(enemy) == 9) {
                round.put(enemy, -1);
            }
        }
    }

    private void currentLocation(EnemyLvL4 enemy, int i, int j) {
        if (!createBoard.gameComponents[i][j].neverPassable) {

            enemy.disappearedObject = (Cell) createBoard.gameComponents[i][j];
            enemy.isGhosting = true;
        }
    }

    private void nextLocation(EnemyLvL4 enemy, int i, int j) {
        if (enemy.isGhosting) {
            enemy.isGhosting = false;
            if (createBoard.gameComponents[i][j].type.equals("wall") && createBoard.gameComponents[i][j].type.equals("obstacle")) {
                enemy.disappearedObject.passable = false;
                createBoard.gameComponents[i][j].passable = false;

            }

            createBoard.setGameComponents(i, j, enemy.disappearedObject);
            enemy.disappearedObject = null;

        } else {
            createBoard.setGameComponents(i, j, new FieldCell());
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


