import java.io.Serializable;
import java.sql.Time;

/**
 * Creating the Main class. Almost everything happens here.
 * It implements Serializable so that we can load and save objects.
 */
public class GameManager implements Serializable {


    private int dimension = 50;
    private int bombExplosionTime = 5;
    final private Time gameTime = new Time(300);
    private int obstacleCount;
    private int boardWidth;
    private int boardHeight;
    private int bombLimit = 1;
    private int level = 1;
    private int points = 0;
    private boolean isOnline;
    Player player = null;
    GameBoardCreator gameBoardCreator;
    GameComponent[][] gameComponents = null;
    private static int gameId;


    /**
     * Constructing the frame.
     * If there exists a board-meaning we want to load a saved game-we would call the method init() ; passing the
     * existing board. If no board exist a new board is made.
     */

    GameManager(int w, int h, GameComponent[][] gameComponents, Boolean isOnline) {


        this.boardWidth = w;
        this.boardHeight = h;
        this.isOnline = isOnline;
        this.gameComponents = gameComponents;
        obstacleCount = (w + h) / 2;

    }

    void createBoard() {

        gameBoardCreator = new GameBoardCreator(this, boardWidth, boardHeight, gameComponents, player, isOnline);
    }

    void init() {
        gameBoardCreator.init();
    }


    int getDimension() {
        return dimension;
    }


    int getObstacleCount() {
        return obstacleCount;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    int getLevel() {
        return level;
    }

    void goToNextLevel(GameBoardCreator gameBoardCreator) {
        level++;

        this.gameBoardCreator = new GameBoardCreator(this, gameBoardCreator.width, gameBoardCreator.height, null, gameBoardCreator.player, isOnline);
        init();
    }


    static void saveFile(GameBoardCreator gameBoardCreator) {

        GameSaver gameSaver = new GameSaver();
        gameSaver.saveFile(gameBoardCreator);
    }

    static void load() {
        GameLoader gameLoader = new GameLoader();
        gameLoader.load();
    }


    int getPoints() {
        return points;
    }

    void setPoints(int points) {
        this.points = points;
    }

    int getBombExplosionTime() {
        return bombExplosionTime;
    }
}
