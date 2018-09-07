import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.Instant;
import java.util.*;
import java.util.Timer;

public class GameBoardCreator extends JFrame implements Serializable {

    int width;
    int height;
    private int enemyCount;
    private static final long serialVersionUID = 1113799434508676095L;

    private GameManager gameManager;
    GameComponent[][] gameComponents;
    JLabel[][] labels;
    private ArrayList<ObstacleCell> obstacleArray = new ArrayList<>();

    private Time gameTime;

    private int obstacle;
    private static final int obstacleScore = 10;
    static int points = 0;
    private static long bombExplosionTime = 5;
    private ActionListeners listeners;
    private MainFrameGraphics mainFrameGraphics = new MainFrameGraphics(this);
    private EnemyMovementThread enemyMove = new EnemyMovementThread(this);
    private EnemyMovementThreadTypeTwo enemyMove2 = new EnemyMovementThreadTypeTwo(this);
    Music music = new Music();
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    static Player player = null;
    private int dimension;
    // static int bombNum = 1;
    static GameBoardCreator gameBoardCreator;
    //  int bombCount = 0;
    private int level;
    private boolean isMoving = false;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ImageIcon imageIcon = null;
    private Date date;
    private boolean isOnline = false;
    private int maximumEnemyLevel = 4;
    private Time shieldTime = new Time(30);

    public GameBoardCreator(GameManager gameManager, int w, int h, GameComponent[][] gameComponents, Player player, boolean isOnline) {

        this.width = w;
        this.height = h;
        this.gameComponents = gameComponents;
        this.obstacle = gameManager.getObstacleCount();
        this.dimension = gameManager.getDimension();
        GameBoardCreator.gameBoardCreator = this;
        this.isOnline = isOnline;
        if (!isOnline && this.gameTime == null) {
            this.gameTime = new Time(300);
        }
        GameBoardCreator.points = gameManager.getPoints();
        this.level = gameManager.getLevel();

        isMoving = false;
        date = Date.from(Instant.now());
        GameBoardCreator.player = player;


        int realSizeWidth = dimension * (2 + w);
        int realSizeHeight = dimension * (2 + h);
        this.gameBoardCreator = this;
        this.gameManager = gameManager;
        //addKeyListenerToFrame();
        labels = new JLabel[width + 2][height + 2];
        listeners = new ActionListeners(this);
        mainFrameGraphics.crateGameFrame(w, h, realSizeWidth, realSizeHeight);
    }

    static int getObstacleScore() {
        return obstacleScore;
    }

    public static void setBombExplosionTime(long bombExplosionTime) {
        GameBoardCreator.bombExplosionTime = bombExplosionTime;
    }


    void init() {

        if (!isOnline && gameComponents == null) {
            gameComponents = new GameComponent[width + 2][height + 2];
            createNewBoard(width, height);
        } else {
            loadExistingBoard();
        }
    }

    private void loadExistingBoard() {
        setUpLabels();
        if (!isOnline) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (gameComponents[i][j].getType().equals("obstacle")) {
                        obstacleArray.add((ObstacleCell) gameComponents[i][j]);
                    }

                }

            }
            setUpStatChangers();
            // createFrame();
        }
    }

    private void setUpLabels() {
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                labels[i][j] = new JLabel(imageIcon);
                labels[i][j].setBorder(border);
                mainFrameGraphics.getCenter().add(labels[i][j]);
            }
        }
    }

    private void createNewBoard(int w, int h) {

        for (int i = 0; i < w + 2; i++) {

            for (int j = 0; j < h + 2; j++) {

                GameComponent gameComponent;


                if (i == 1 && j == 1) {
                    if (player == null) {
                        player = new Player("");
                    }
                    gameComponent = player;
                    player.setPlayerPositionY(1);
                    player.setPlayerPositionX(1);
                } else if (i == 0 || j == 0 || i == w + 1 || j == h + 1) {

                    WallCell wallCell = new WallCell();
                    gameComponent = wallCell;
                    wallCell.setNeverPassable(true);
                } else if (i % 2 == 0 && j % 2 == 0) {
                    gameComponent = new WallCell();
                } else {
                    gameComponent = new FieldCell();
                }

                this.gameComponents[i][j] = gameComponent;
            }
        }
        setUpLabels();
        createRandomEnemies(width, height);
        createRandomObstacleCell(width, height);
    }

    private void createRandomEnemies(int width, int height) {

        int enemyNum = Math.min(width, height) / 2;
        // enemyNum = 0;
        enemyCount = enemyNum;
        for (int i = 0; i < enemyNum; ) {
            Random r = new Random();
            int n = r.nextInt(width - 1) + 1;
            int m = r.nextInt(height - 1) + 1;
            if (gameComponents[n][m].getType().equals("field")) {

                if (n != 2 && m != 2) {
                    i++;
                    Enemy enemy = createRandomEnemy();
                    this.gameComponents[n][m] = enemy;
                    enemies.add(enemy);

                }
            }
        }
    }

    private Enemy createRandomEnemy() {
        int imaginaryLevel = level;
        if (level > maximumEnemyLevel) {
            imaginaryLevel = 4;
        }
        int randomNum = new Random().nextInt(imaginaryLevel) + 1;


        if (randomNum == 1) {
            return new EnemyLvL1();
        } else if (randomNum == 2) {
            return new EnemyLvL2();
        } else if (randomNum == 3) {
            return new EnemyLvL3();
        } else {
            return new EnemyLvL4();
        }
    }

    private void createRandomObstacleCell(int w, int h) {

        for (int k = 0; k < obstacle; ) {

            Random r = new Random();
            int n = r.nextInt(w - 1) + 1;
            int m = r.nextInt(h - 1) + 1;
            if (gameComponents[n][m].getType().equals("field")) {

                if (n != 2 && m != 2) {
                    k++;
                    ObstacleCell obstacleCell = new ObstacleCell();
                    this.gameComponents[n][m] = obstacleCell;
                    obstacleArray.add(obstacleCell);

                }
            }
        }
        int randomNum = getRandomNumber(obstacleArray.size());
        ObstacleCell obstacleCell = obstacleArray.get(randomNum);
        obstacleCell.hasDoor = true;
        setUpStatChangers();
    }

    private PowerUps getRandomPowerUp() {
        ArrayList<PowerUps> powerUps = new ArrayList<>();
        powerUps.add(new IncreasingBombsPowerUp());
        powerUps.add(new IncreasingPointsPowerUp());
        powerUps.add(new IncreasingRadiusPowerUp());
        powerUps.add(new IncreasingSpeedPowerUp());
        powerUps.add(new BombControlPowerUp());
        powerUps.add(new ShieldAbilityPowerUp());


        Random r = new Random();
        int m = r.nextInt(powerUps.size() - 1);
        return powerUps.get(m);
    }

    private Poison getRandomPoison() {
        ArrayList<Poison> poisons = new ArrayList<>();
        poisons.add(new DecreasingBombsPoison());
        poisons.add(new DecreasingPointsPoison());
        poisons.add(new DecreasingRadiusPoison());
        poisons.add(new DecreasingSpeedPoison());
        poisons.add(new LosingBombControlPoison());

        Random r = new Random();
        int m = r.nextInt(poisons.size() - 1);
        return poisons.get(m);
    }

    private void setUpStatChangers() {
        for (int k = 0; k <= obstacle / 3; ) {

            Random r = new Random();
            int n = r.nextInt(obstacleArray.size() - 1);
            int m = r.nextInt(2);
            StatChanger statChanger;
            ObstacleCell obstacleCell = obstacleArray.get(n);
            if (m == 1) {
                statChanger = getRandomPowerUp();
                obstacleCell.powerUps = (PowerUps) statChanger;
            }
            if (m == 0) {
                statChanger = getRandomPoison();
                obstacleCell.poison = (Poison) statChanger;
            }
            k++;
        }
        createFrame();
    }


    private int getRandomNumber(int bound) {

        Random random = new Random();

        if (bound == 0) {
            return -1;
        }
        if (bound == 1) {
            return 0;
        } else {
            int randomNum = random.nextInt(bound - 1);
            return randomNum;
        }
    }

    synchronized void setGameComponents(int i, int j, GameComponent gameComponent) {
        gameComponents[i][j] = gameComponent;
    }

    public void createFrame() {
        int u = 0;

        JLabel label;
        for (int i = 0; i < width + 2; i++) {

            for (int j = 0; j < height + 2; j++) {
                if (gameComponents[i][j] == null) {
                    gameComponents[i][j] = new FieldCell();
                }
                labels[i][j].setText("");


                label = mainFrameGraphics.showTheObject(i, j);
                if (label.getIcon() != null) {
                    labels[i][j].setIcon(label.getIcon());
                } else {
                    labels[i][j].setIcon(null);
                    labels[i][j].setText(label.getText());
                }

                u++;
            }
            mainFrameGraphics.getCenter().repaint();
            if (!isOnline) {
                if (!isMoving) {
                    setTimer();
                    enemyMove.start();
                    enemyMove2.start();
                    music.start();
                    isMoving = true;

                }
            }
        }
    }


    boolean isPassed() {
        return Date.from(Instant.now()).getTime() - date.getTime() > player.getPlayerSpeed() * 100;
    }

    void checkIfIsStatChanger(GameComponent cell) {

        StatChanger statChanger = (StatChanger) cell;
        statChanger.setPassable(true);
        statChanger.doYourThing();
    }


    void addScore(int score) {
        System.out.println(score);

        if (points + score > 0) {

            points += score;

        } else {
            points = 0;
        }
        refreshScore(String.valueOf(points));

    }

    void setTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                calculateTime();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void calculateTime() {


        if (gameTime.getTime() > 0) {
            gameTime.setTime(gameTime.getTime() - 1);
            refreshTimer(gameTime.getTime());
        } else {
            addScore(-1);
        }
    }

    void refreshTimer(long time) {

        mainFrameGraphics.getTime().setText(Integer.toString((int) time / 60) + "  :  " + (int) time % 60);
    }

    void refreshScore(String score) {

        mainFrameGraphics.getScore().setText(score);
    }

    boolean checkIfICanGoToNextLevel() {
        if (enemyCount == 0) {
            return true;
        }
        return false;
    }

    void goToNextLevel() {
        player.setPlayerPositionX(1);
        player.setPlayerPositionY(1);
        gameManager.player = player;
        player.setBombCount(0);
        gameManager.setPoints(GameBoardCreator.points);

        gameManager.goToNextLevel(this);

        this.dispose();
    }

    static long getBombExplosionTime() {
        return bombExplosionTime;
    }

    int getEnemyCount() {
        return enemyCount;
    }

    void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    Time getGameTime() {
        return gameTime;
    }

    void setGameTime(Time gameTime) {
        this.gameTime = gameTime;
    }

    EnemyMovementThread getEnemyMove() {
        return enemyMove;
    }

    EnemyMovementThreadTypeTwo getEnemyMove2() {
        return enemyMove2;
    }

    void setDate(Date date) {
        this.date = date;
    }

    boolean isOnline() {
        return isOnline;
    }

    ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    MainFrameGraphics getMainFrameGraphics() {
        return mainFrameGraphics;
    }


}
