import javax.swing.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.TimerTask;


public class Player extends GameComponent implements Serializable {

    private int playerPositionX = 1;
    private int playerPositionY = 1;
    private static final long serialVersionUID = 1114799434508306969L;

    private String name;
    private boolean bombSet = false;
    private boolean bombControl = true;
    private long playerSpeed = 4;
    private BombCell currentBomb = null;
    private final String type = "player";
    private boolean isAlive;
    private int bombNum = 1;
    private int bombCount = 0;
    private int bombRadius = 1;
    boolean hasShield = true;
    private boolean usedShield = false;
    private Time shieldTime = new Time(30);
    private ArrayList<BombCell> bombCells = new ArrayList<>();
    private ArrayList<String> passableObjects = new ArrayList<>();


    Player(String name) {
        this.name = name;
        super.setType(type);
        super.setPassable(true);

        passableObjects.add("FieldCell");
        passableObjects.add("StatChanger");


        this.isAlive = true;

    }

    void die() {
        this.isAlive = false;
        bombCount = 0;
        bombNum = 1;
    }

    void plantBomb() {
        if (bombCount <= bombNum && !bombSet) {
            bombCount++;
            bombSet = true;
            BombCell bomb = new BombCell(bombRadius, GameBoardCreator.getBombExplosionTime(), GameBoardCreator.gameBoardCreator, playerPositionX, playerPositionY);
            bombCells.add(bomb);
            GameBoardCreator.gameBoardCreator.gameComponents[playerPositionX][playerPositionY] = bomb;
            currentBomb = bomb;

            GameBoardCreator.gameBoardCreator.createFrame();
        }
    }

    int getPlayerPositionX() {
        return playerPositionX;
    }

    void setPlayerPositionX(int playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    int getPlayerPositionY() {
        return playerPositionY;
    }

    void setPlayerPositionY(int playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    boolean isBombSet() {
        return bombSet;
    }

    void setBombSet(boolean bombSet) {
        this.bombSet = bombSet;
    }

    boolean isBombControl() {
        return bombControl;
    }

    void setBombControl(boolean bombControl) {
        this.bombControl = bombControl;
    }

    long getPlayerSpeed() {
        return playerSpeed;
    }

    void setPlayerSpeed(long playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    BombCell getCurrentBomb() {
        return currentBomb;
    }

    void setCurrentBomb(BombCell currentBomb) {
        this.currentBomb = currentBomb;
    }

    void killPlayer() {
        if (!hasShield) {

            die();
            JOptionPane.showMessageDialog(null, "Game Over!!!", "Game Over!", 1);

            GameBoardCreator.gameBoardCreator.getEnemyMove().stop();
            GameBoardCreator.gameBoardCreator.getEnemyMove2().stop();
            GameBoardCreator.gameBoardCreator.music.stop();
            GameBoardCreator.gameBoardCreator.gameComponents[getPlayerPositionX()][getPlayerPositionY()] = new FieldCell();
            GameBoardCreator.gameBoardCreator.dispose();
        } else {
            setShieldTimer();
        }

        GameBoardCreator.gameBoardCreator.createFrame();

    }

    private void setShieldTimer() {
        if (!usedShield) {
            GameBoardCreator.gameBoardCreator.getMainFrameGraphics().man.setDescription("Shield");
            usedShield = true;
            shieldTime = new Time(30);
            java.util.Timer timer = new java.util.Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    keepShieldAbility();
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }


    private void keepShieldAbility() {


        if (shieldTime.getTime() > 0) {
            shieldTime.setTime(shieldTime.getTime() - 1);
            // refreshTimer(gameTime.getTime());
        } else {
            hasShield = false;
            usedShield = false;
            GameBoardCreator.gameBoardCreator.getMainFrameGraphics().man.setDescription("");

        }
    }

    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {
        //  gameBoardCreator.gameComponents[i][j] = new FieldCell();
        killPlayer();
    }

    @Override
    public String getType() {
        return type;
    }

    boolean isAlive() {
        return isAlive;
    }

    void setAlive(boolean alive) {
        isAlive = alive;
    }

    int getBombNum() {
        return bombNum;
    }

    void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }

    int getBombCount() {
        return bombCount;
    }

    void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    int getBombRadius() {
        return bombRadius;
    }

    void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    ArrayList<BombCell> getBombCells() {
        return bombCells;
    }

    void putPlayerOnShield() {
        hasShield = true;
    }

}