import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class EnemyMovementThreadTypeTwo extends Thread implements Serializable {


    CreatingGameBoard creatingGameBoard;
    private static final long serialVersionUID = 1183799434508296969L;

    private GameComponent up;
    private GameComponent down;
    private GameComponent right;
    private GameComponent left;
    private MovingEnemyRandomly movingEnemyRandomly = new MovingEnemyRandomly();
    int k = 0;
    private ArrayList<GameComponent> chooseDirection = new ArrayList<>();
    private Map<Enemy, Integer> round = new HashMap<>();

    EnemyMovementThreadTypeTwo(CreatingGameBoard creatingGameBoard) {
        this.creatingGameBoard = creatingGameBoard;
    }

    @Override
    public void run() {

        for (int p = 0; p < creatingGameBoard.enemies.size(); p++) {
            round.put(creatingGameBoard.enemies.get(p), -1);
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


            for (int p = 0; p < creatingGameBoard.enemies.size(); p++) {
                int k = 0;
                Enemy enemy = creatingGameBoard.enemies.get(p);
                for (int i = 0; i < creatingGameBoard.width + 2; i++) {
                    for (int j = 0; j < creatingGameBoard.height + 2 && k < 1; j++) {
                        if (creatingGameBoard.gameComponents[i][j] == enemy) {


                            chooseDirection = new ArrayList<>();

                            up = creatingGameBoard.gameComponents[i - 1][j];
                            right = creatingGameBoard.gameComponents[i][j + 1];
                            down = creatingGameBoard.gameComponents[i + 1][j];
                            left = creatingGameBoard.gameComponents[i][j - 1];
                            if (enemy.type.equals("enemyLvL4")) {
                                if (i - 1 > 0 && !(creatingGameBoard.gameComponents[i - 1][j].neverPassable)) {
                                    chooseDirection.add(up);
                                }
                                if (i + 1 > 0 && !(creatingGameBoard.gameComponents[i + 1][j].neverPassable)) {
                                    chooseDirection.add(down);
                                }
                                if (j - 1 > 0 && !(creatingGameBoard.gameComponents[i][j - 1].neverPassable)) {

                                    chooseDirection.add(left);
                                }
                                if (j + 1 > 0 && !(creatingGameBoard.gameComponents[i][j + 1].neverPassable)) {
                                    chooseDirection.add(right);
                                }
                            } else {


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
                            }


//                            } else {
//                                if (up.passable && up.type.equals("wall") && up.type.equals("obstacle")) {
//
//                                    chooseDirection.add(up);
//                                }
//                                if (right.passable && right.type.equals("wall") && right.type.equals("obstacle")) {
//
//                                    chooseDirection.add(right);
//                                }
//                                if (down.passable && down.type.equals("wall") && down.type.equals("obstacle")) {
//
//                                    chooseDirection.add(down);
//                                }
//                                if (left.passable && left.type.equals("wall") && left.type.equals("obstacle")) {
//
//                                    chooseDirection.add(left);
//                                }
//                            }
                            if (enemy.type.equals("enemyLvL3") || enemy.type.equals("enemyLvL4")) {

                                moveEnemyLevelThree((Enemy) creatingGameBoard.gameComponents[i][j], i, j);

                            }
                            k++;
                        }

                    }
                }
            }
            creatingGameBoard.createFrame();
        }
    }


    private void moveEnemyLevelThree(Enemy enemy, int i, int j) {


        if (round.get(enemy) == -1) {
            if (up == creatingGameBoard.player || down == creatingGameBoard.player || right == creatingGameBoard.player || left == creatingGameBoard.player) {
                creatingGameBoard.gameComponents[creatingGameBoard.player.playerPositionX][creatingGameBoard.player.playerPositionY] = enemy;
                creatingGameBoard.gameComponents[i][j] = new FieldCell();
                creatingGameBoard.killPlayer();
                this.stop();
            }
            if (creatingGameBoard.player.playerPositionX > i && chooseDirection.contains(down)) {


                if (enemy.type.equals("enemyLvL4")) {

                    currentLocation((EnemyLvL4) enemy, i, j);
                    nextLocation((EnemyLvL4) enemy, i + 1, j);
                } else {
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();

                }
                creatingGameBoard.gameComponents[i + 1][j] = enemy;

            } else if (creatingGameBoard.player.playerPositionX < i && chooseDirection.contains(up)) {

                if (enemy.type.equals("enemyLvL4")) {

                    currentLocation((EnemyLvL4) enemy, i, j);
                    nextLocation((EnemyLvL4) enemy, i - 1, j);
                } else {
                    creatingGameBoard.gameComponents[i][j] = new FieldCell();

                }
                creatingGameBoard.gameComponents[i - 1][j] = enemy;

            } else if (creatingGameBoard.player.playerPositionY > j && chooseDirection.contains(right)) {

                if (enemy.type.equals("enemyLvL4")) {

                    currentLocation((EnemyLvL4) enemy, i, j);
                    nextLocation((EnemyLvL4) enemy, i, j + 1);
                } else {
                    creatingGameBoard.setGameComponents(i, j, new FieldCell());

                }
                creatingGameBoard.gameComponents[i][j + 1] = enemy;

            } else if (creatingGameBoard.player.playerPositionY < j && chooseDirection.contains(left)) {

                if (enemy.type.equals("enemyLvL4")) {

                    currentLocation((EnemyLvL4) enemy, i, j);
                    nextLocation((EnemyLvL4) enemy, i, j - 1);
                } else {
                    creatingGameBoard.setGameComponents(i, j, new FieldCell());

                }
                creatingGameBoard.setGameComponents(i, j - 1, enemy);
            } else {
                round.put(enemy, 0);
            }
        } else {

            round.put(enemy, round.get(enemy) + 1);
//            if (enemy.type.equals("enemyLvL4")) {
//                if (up.type.equals("wall") || up.type.equals("obstacle")) {
//                    up.passable = false;
//                    chooseDirection.remove(up);
//                }
//                if (down.type.equals("wall") || down.type.equals("obstacle")) {
//                    down.passable = false;
//                    chooseDirection.remove(down);
//                }
//                if (right.type.equals("wall") || right.type.equals("obstacle")) {
//                    right.passable = false;
//                    chooseDirection.remove(right);
//                }
//                if (left.type.equals("wall") || left.type.equals("obstacle")) {
//                    left.passable = false;
//                    chooseDirection.remove(left);
//                }
//            }
            int randomNum = getRandomDirection();
            movingEnemyRandomly.move(creatingGameBoard, i, j, enemy, randomNum, chooseDirection);
            if (round.get(enemy) == 9) {
                round.put(enemy, -1);
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


