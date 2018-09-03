import java.io.Serializable;
import java.util.ArrayList;


public class Player extends GameComponent implements Serializable {

    private int playerPositionX = 1;
    private int playerPositionY = 1;
    private static final long serialVersionUID = 1114799434508306969L;

    private String name;
    private boolean bombSet = false;
    private boolean bombControl = false;
    private long playerSpeed = 4;
    private BombCell currentBomb = null;
    private final String type = "player";
    private boolean isAlive;
    private boolean ghostAbility = true;
    private boolean isGhosting = false;
    private GameComponent disappearedObject = null;
    private int bombNum = 1;
    private int bombCount = 0;
    private int bombRadius = 1;
    private ArrayList<BombCell> bombCells = new ArrayList<>();


    public Player(String name) {
        this.name = name;
        super.setType(type);
        super.setPassable(true);

//        this.playerPositionX = x;
//        this.playerPositionY = y;

        this.isAlive = true;

    }

    public void die() {
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

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public void setPlayerPositionX(int playerPositionX) {
        this.playerPositionX = playerPositionX;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }

    public void setPlayerPositionY(int playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBombSet() {
        return bombSet;
    }

    public void setBombSet(boolean bombSet) {
        this.bombSet = bombSet;
    }

    public boolean isBombControl() {
        return bombControl;
    }

    public void setBombControl(boolean bombControl) {
        this.bombControl = bombControl;
    }

    public long getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(long playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public BombCell getCurrentBomb() {
        return currentBomb;
    }

    public void setCurrentBomb(BombCell currentBomb) {
        this.currentBomb = currentBomb;
    }

    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {
        gameBoardCreator.gameComponents[i][j] = new FieldCell();
        gameBoardCreator.killPlayer();
    }

    @Override
    public String getType() {
        return type;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isGhostAbility() {
        return ghostAbility;
    }

    public void setGhostAbility(boolean ghostAbility) {
        this.ghostAbility = ghostAbility;
    }

    public boolean isGhosting() {
        return isGhosting;
    }

    public void setGhosting(boolean ghosting) {
        isGhosting = ghosting;
    }

    public GameComponent getDisappearedObject() {
        return disappearedObject;
    }

    public void setDisappearedObject(GameComponent disappearedObject) {
        this.disappearedObject = disappearedObject;
    }

    public int getBombNum() {
        return bombNum;
    }

    public void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public ArrayList<BombCell> getBombCells() {
        return bombCells;
    }

    public void setBombCells(ArrayList<BombCell> bombCells) {
        this.bombCells = bombCells;
    }
}