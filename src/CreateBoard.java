import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.Instant;
import java.util.*;
import java.util.Timer;

public class CreateBoard extends JFrame implements Serializable {

    int width;
    int height;
    int enemyCount;
    private static final long serialVersionUID = 1113799434508676095L;

    GameManager gameManager;
    GameComponent[][] gameComponents;
    public JLabel[][] labels;
    ArrayList<ObstacleCell> obstacleArray = new ArrayList<>();

    Time gameTime;

    private int obstacle;
    static final int obstacleScore = 10;
    static int points = 0;
    static int bombRadius = 1;
    static long bombExplosionTime = 5;
    private ActionListeners listeners;
    private MainFrameGraphics graphics = new MainFrameGraphics(this);
    EnemyMovementThread enemyMove = new EnemyMovementThread(this);
    EnemyMovementThreadTypeTwo enemyMove2 = new EnemyMovementThreadTypeTwo(this);
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    static Player player = null;
    private int dimension;
    static int bombNum = 1;
    static CreateBoard createBoard;
    int bombCount = 0;
    private int level;
    private boolean isMoving = false;
    ArrayList<BombCell> bombCells = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    private ImageIcon imageIcon = null;
    Date date;
    boolean isOnline = false;

    public CreateBoard(GameManager gameManager, int w, int h, GameComponent[][] gameComponents, Player player, int bombNum, boolean isOnline) {

        this.width = w;
        this.height = h;
        this.gameComponents = gameComponents;
        this.obstacle = gameManager.getObstacleCount();
        this.dimension = gameManager.getDimension();
        this.bombNum = bombNum;
        CreateBoard.createBoard = this;
        this.isOnline = isOnline;
        CreateBoard.bombNum = gameManager.getBombLimit();
        if (!isOnline && this.gameTime == null) {
            this.gameTime = new Time(300);
        }
        CreateBoard.points = gameManager.getPoints();
        this.level = gameManager.getLevel();
        isMoving = false;
        date = Date.from(Instant.now());
        this.player = player;


        int realSizeWidth = dimension * (2 + w);
        int realSizeHeight = dimension * (2 + h);
        this.createBoard = this;
        this.gameManager = gameManager;
        //addKeyListenerToFrame();
        labels = new JLabel[width + 2][height + 2];
        listeners = new ActionListeners(this);
        graphics.crateGameFrame(w, h, realSizeWidth, realSizeHeight);
    }


    public void init() {

        // if (gameComponents == null) {
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
            createFrame();
        }
    }

    private void setUpLabels() {
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                labels[i][j] = new JLabel(imageIcon);
                labels[i][j].setBorder(border);
                graphics.center.add(labels[i][j]);
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
                    player.playerPositionY = 1;
                    player.playerPositionX = 1;
                } else if (i == 0 || j == 0 || i == w + 1 || j == h + 1) {


                    gameComponent = new WallCell();
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

        int enemyNum = Math.min(width, height) / 2 - 5;
        enemyCount = enemyNum;
        for (int i = 0; i < enemyNum; ) {
            Random r = new Random();
            int n = r.nextInt(width - 1) + 1;
            int m = r.nextInt(height - 1) + 1;
            if (gameComponents[n][m].type == "field") {

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
        if (level > 4) {
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
            if (gameComponents[n][m].type.equals("field")) {

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
        powerUps.add(new IncreaseBombs());
        powerUps.add(new IncreasePoints());
        powerUps.add(new IncreaseRadius());
        powerUps.add(new IncreaseSpeed());
        powerUps.add(new BombControl());
        powerUps.add(new GhostAbility(this));

        Random r = new Random();
        int m = r.nextInt(powerUps.size() - 1);
        return powerUps.get(m);
    }

    private Poison getRandomPoison() {
        ArrayList<Poison> poisons = new ArrayList<>();
        poisons.add(new DecreaseBombs());
        poisons.add(new DecreasePoints());
        poisons.add(new DecreaseRadius());
        poisons.add(new DecreaseSpeed());
        poisons.add(new LoseBombControl());

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


                label = graphics.showTheObject(i, j);
                if (label.getIcon() != null) {
                    labels[i][j].setIcon(label.getIcon());
                } else {
                    labels[i][j].setIcon(null);
                    labels[i][j].setText(label.getText());
                }

                u++;
            }
            graphics.center.repaint();
            if (!isOnline) {
                if (!isMoving) {
                    setTimer();
                    enemyMove.start();
                    enemyMove2.start();
                    isMoving = true;

                }
            }
        }
    }


    public void killPlayer() {
        player.die();
        JOptionPane.showMessageDialog(this, "Game Over!!!", "Game Over!", 1);

        enemyMove.stop();
        enemyMove2.stop();
        gameComponents[player.playerPositionX][player.playerPositionY] = new FieldCell();
        this.dispose();

        createFrame();

    }


    boolean isPassed() {
        return Date.from(Instant.now()).getTime() - date.getTime() > player.playerSpeed * 100;
    }

    void checkIfIsStatChanger(GameComponent cell) {

        StatChanger statChanger = (StatChanger) cell;
        statChanger.passable = true;
        statChanger.doYourThing();
    }

    void plantBomb() {
        if (bombCount <= bombNum && !player.bombSet) {
            bombCount++;
            player.bombSet = true;
            BombCell bomb = new BombCell(bombRadius, bombExplosionTime, this, player.playerPositionX, player.playerPositionY);
            bombCells.add(bomb);
            gameComponents[player.playerPositionX][player.playerPositionY] = bomb;
            player.currentBomb = bomb;

            createFrame();
        }
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

        graphics.time.setText(Integer.toString((int) time / 60) + "  :  " + (int) time % 60);
    }

    void refreshScore(String score) {

        graphics.score.setText(score);
    }

    boolean checkIfPassable() {
        if (enemyCount == 0) {
            return true;
        }
        return false;
    }

    void goToNextLevel() {
        player.playerPositionX = 1;
        player.playerPositionY = 1;
        gameManager.player = player;
        bombCount = 0;
        gameManager.setPoints(CreateBoard.points);

        gameManager.goToNextLevel(this);

        this.dispose();
    }
}
