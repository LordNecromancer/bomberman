import java.io.Serializable;
import java.util.ArrayList;


public class Player extends GameComponent implements Serializable {

    int playerPositionX = 1;
    int playerPositionY = 1;
    private static final long serialVersionUID = 1114799434508306969L;

    String name;
    boolean bombSet = false;
    boolean bombControl = false;
    long playerSpeed = 4;
    public BombCell currentBomb = null;
    final String type = "player";
    boolean isAlive;
    public boolean ghostAbility = true;
    boolean isGhosting = false;
    GameComponent disappearedObject = null;
    int bombNum = 1;
    int bombCount = 0;
    int bombRadius = 1;
    ArrayList<BombCell> bombCells = new ArrayList<>();


    public Player(String name) {
        this.name = name;
        super.type = type;
        super.passable = true;

//        this.playerPositionX = x;
//        this.playerPositionY = y;

        this.isAlive = true;

    }

    public void die() {
        this.isAlive = false;
        bombCount = 0;
        bombNum = 1;
    }

}