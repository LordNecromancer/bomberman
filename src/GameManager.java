import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

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

    public GameManager(int w, int h, GameComponent[][] gameComponents, Boolean isOnline) {


        this.boardWidth = w;
        this.boardHeight = h;
        this.isOnline = isOnline;
        this.gameComponents = gameComponents;
        obstacleCount = (w + h) / 2;
        // player = new Player(playerx, playery);
        // gameBoardCreator();

    }

    public void createBoard() {

        gameBoardCreator = new GameBoardCreator(this, boardWidth, boardHeight, gameComponents, player, isOnline);

    }

    void init() {
        gameBoardCreator.init();
    }


    public int getDimension() {
        return dimension;
    }

    public Time getGameTime() {
        return gameTime;
    }

    public int getObstacleCount() {
        return obstacleCount;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public int getLevel() {
        return level;
    }

    public void goToNextLevel(GameBoardCreator gameBoardCreator) {
        level++;

        this.gameBoardCreator = new GameBoardCreator(this, gameBoardCreator.width, gameBoardCreator.height, null, gameBoardCreator.player, isOnline);
        init();
    }

    private static File getFile(int n) {
        JFrame loadAndSave = new JFrame();
        JFileChooser loaderAndSaver = new JFileChooser();
        loadAndSave.add(loaderAndSaver);
        int num = -1;
        if (n == 0) {
            num = loaderAndSaver.showOpenDialog(loaderAndSaver);
        }
        if (n == 1) {
            num = loaderAndSaver.showSaveDialog(loaderAndSaver);
        }
        File file = null;
        if (num == JFileChooser.APPROVE_OPTION) {
            file = loaderAndSaver.getSelectedFile();
        }
        return file;
    }


    static void saveFile(GameBoardCreator gameBoardCreator) {

        String url = "jdbc:mysql://localhost:3306/bomberman";
        String username = "root";
        String password = "";
        Statement statement;
        Connection connection;

//
        String name = JOptionPane.showInputDialog("Enter your file name  ");
        System.out.println("Connecting database...");

        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        addToGameTable(gameBoardCreator, statement, connection, name);
        addToGameSpecs(gameBoardCreator, connection);
        addToGameComponentsArray(gameBoardCreator, connection);
    }

    private static void addToGameComponentsArray(GameBoardCreator gameBoardCreator, Connection connection) {
        Statement statement;
        try {
            statement = connection.createStatement();

            StringBuilder string = new StringBuilder("CREATE TABLE IF NOT EXISTS GameComponentsArray");
            string.append("(gameId INT(50),");
            string.append("objectName VARCHAR(50),");
            string.append("XComponent INT(50),");
            string.append("YComponent INT(50))");


            statement.executeUpdate(string.toString());
            String str2 = "insert into gameComponentsArray (gameId,objectName,XComponent,YComponent) " + "values (?,?,?,?)";

            insertIntoGameComponentTable(gameBoardCreator, connection, str2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoGameComponentTable(GameBoardCreator gameBoardCreator, Connection connection, String str2) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(str2);
        for (int i = 0; i < gameBoardCreator.width + 2; i++) {
            for (int j = 0; j < gameBoardCreator.height + 2; j++) {
                preparedStatement.setInt(1, gameId);
                preparedStatement.setString(2, gameBoardCreator.gameComponents[i][j].getClass().getName());
                preparedStatement.setInt(3, i);
                preparedStatement.setInt(4, j);
                preparedStatement.execute();
            }
        }
    }


//        File file = getFile(1);
//        FileOutputStream fos = null;
//        try {
//            if (file != null)
//                fos = new FileOutputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ObjectOutputStream oos;
//        try {
//            if (fos != null) {
//                oos = new ObjectOutputStream(fos);
//                oos.writeObject(gameBoardCreator);
//                oos.close();
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
    // }


    private static void addToGameSpecs(GameBoardCreator gameBoardCreator, Connection connection) {
        Statement statement;
        try {
            statement = connection.createStatement();

            StringBuilder string = new StringBuilder("CREATE TABLE IF NOT EXISTS bomberManSpecs");
            string.append("(gameId INT(64),");
            string.append("bombSet BOOL,");
            string.append("bombControl BOOL,");
            string.append("playerSpeed INT(64),");
            string.append("isAlive BOOL,");
            string.append("bombNum INT(10),");
            string.append("bombCount INT(10),");
            string.append("gameTime INT(10),");
            string.append("score INT(64),");
            string.append("bombRadius INT(10))");


            statement.executeUpdate(string.toString());

            insertIntoGameSpecsTable(gameBoardCreator, connection);

            System.out.println("Database connected!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Not Good syntax", e);

        }
    }

    private static void insertIntoGameSpecsTable(GameBoardCreator gameBoardCreator, Connection connection) throws SQLException {
        String str2 = "insert into bomberManSpecs (gameId,bombSet,bombControl,playerSpeed,isAlive,bombNum,bombCount,gameTime,score,bombRadius) " + "values (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(str2);
        preparedStatement.setInt(1, gameId);
        preparedStatement.setBoolean(2, gameBoardCreator.player.isBombSet());
        preparedStatement.setBoolean(3, gameBoardCreator.player.isBombControl());
        preparedStatement.setInt(4, (int) gameBoardCreator.player.getPlayerSpeed());
        preparedStatement.setBoolean(5, gameBoardCreator.player.isAlive());
        preparedStatement.setInt(6, GameBoardCreator.player.getBombNum());
        preparedStatement.setInt(7, GameBoardCreator.player.getBombCount());
        preparedStatement.setInt(8, (int) gameBoardCreator.getGameTime().getTime());
        preparedStatement.setInt(9, gameBoardCreator.points);
        preparedStatement.setInt(10, gameBoardCreator.player.getBombRadius());


        preparedStatement.execute();
    }

    private static void addToGameTable(GameBoardCreator gameBoardCreator, Statement statement, Connection connection, String name) {
        try {
            StringBuilder string = new StringBuilder("CREATE TABLE IF NOT EXISTS " + "bomberManTest");
            string.append("(gameId INT(64)  AUTO_INCREMENT PRIMARY KEY NOT NULL  ,");
            string.append("gameName VARCHAR(50)  ,");
            string.append("width INT(64) ,");
            string.append("height INT(64)) ");


            statement.executeUpdate(string.toString());

            PreparedStatement preparedStatement = insertIntoGameTable(gameBoardCreator, connection, name);

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            gameId = rs.getInt(1);

            preparedStatement.close();
            statement.close();

            System.out.println("Database connected!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Not Good syntax", e);

        }
    }

    private static PreparedStatement insertIntoGameTable(GameBoardCreator gameBoardCreator, Connection connection, String name) throws SQLException {
        String str2 = "insert into bomberManTest (gameName,width,height) " + "values (?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(str2, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, gameBoardCreator.width);
        preparedStatement.setInt(3, gameBoardCreator.height);

        preparedStatement.execute();
        return preparedStatement;
    }

    static void load()

    {

        String url = "jdbc:mysql://localhost:3306/bomberman";
        String username = "root";
        String password = "";
        String name = JOptionPane.showInputDialog("Enter your file name  ");
        Player player = new Player("");
        Boolean bombSet = false;
        Boolean bombControl = false;
        boolean isAlive = true;
        int playerSpeed = 0;

        System.out.println("Connecting database...");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            int k = 0;
            GameComponent[][] gameComponents = null;
            int width = 0;
            int height = 0;
            int bombNum = 0;
            int bombCount = 0;
            int gameTime = 0;
            int score = 0;
            int bombRadius = 1;
            ArrayList<Integer> bombsX = new ArrayList<>();
            ArrayList<Integer> bombsY = new ArrayList<>();
            ArrayList<Enemy> enemies = new ArrayList<>();


            ResultSet result = statement.executeQuery("select * from bombermantest WHERE gameName='" + name + "'");
            while (result.next()) {
                if (k == 0) {
                    gameId = result.getInt("gameId");
                    width = result.getInt("width");
                    height = result.getInt("height");
                    gameComponents = new GameComponent[width + 2][height + 2];
                    k++;

                }
            }
            result = statement.executeQuery("select * from bombermanspecs WHERE gameId='" + gameId + "'");
            while (result.next()) {

                bombSet = result.getBoolean("bombSet");
                bombControl = result.getBoolean("bombControl");
                playerSpeed = result.getInt("playerSpeed");
                isAlive = result.getBoolean("isAlive");
                bombNum = result.getInt("bombNum");
                bombCount = result.getInt("bombCount");
                gameTime = result.getInt("gameTime");
                score = result.getInt("score");
                bombRadius = result.getInt("bombRadius");


            }
            player.setBombSet(bombSet);
            player.setAlive(isAlive);
            player.setBombControl(bombControl);
            player.setPlayerSpeed(playerSpeed);
            player.setBombRadius(bombRadius);
            player.setBombCount(bombCount);
            player.setBombNum(bombNum);

            result = statement.executeQuery("SELECT  * FROM gameComponentsArray WHERE gameId='" + gameId + "'");
            while (result.next()) {
                String className = result.getString("objectName");
                int XComponent = result.getInt("XComponent");
                int YComponent = result.getInt("YComponent");

                if (!className.equals("Player") && !className.equals("BombCell")) {
                    GameComponent gameComponent = (GameComponent) Class.forName(className).newInstance();
                    gameComponents[XComponent][YComponent] = gameComponent;
                    if (gameComponent instanceof Enemy) {
                        enemies.add((Enemy) gameComponent);
                    }
                } else if (className.equals("BombCell")) {
                    bombsX.add(XComponent);
                    bombsY.add(YComponent);

                } else {
                    gameComponents[XComponent][YComponent] = player;
                    player.setPlayerPositionX(XComponent);
                    player.setPlayerPositionY(YComponent);

                }
            }
            GameManager gameManager = new GameManager(width, height, gameComponents, false);
            gameManager.player = player;


            gameManager.createBoard();
            for (int i = 0; i < enemies.size(); i++) {
                gameManager.gameBoardCreator.getEnemies().add(enemies.get(i));
            }
            for (int i = 0; i < bombsX.size(); i++) {
                // String className = resultSet.getString("objectName");
                int XComponent = bombsX.get(i);
                int YComponent = bombsY.get(i);
                BombCell bombCell = new BombCell(player.getBombRadius(), gameManager.bombExplosionTime, gameManager.gameBoardCreator, XComponent, YComponent);
                gameComponents[XComponent][YComponent] = bombCell;
                player.getBombCells().add(bombCell);
                if (i == 0) {
                    player.setCurrentBomb(bombCell);
                }


            }
            gameManager.gameBoardCreator.gameComponents = gameComponents;
            gameManager.gameBoardCreator.setGameTime(new Time(gameTime));
            gameManager.gameBoardCreator.points = score;
            gameManager.init();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//        {
//            File file = getFile(0);
//
//            try {
//
//                FileInputStream fis = new FileInputStream(file.getPath());
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                GameBoardCreator result = (GameBoardCreator) ois.readObject();
//
//                GameBoardCreator newCreateBoard = new GameBoardCreator(result.gameManager, result.width, result.height, result.gameComponents, result.player, result.bombCount, result.isOnline);
//                newCreateBoard.init();
//                ois.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
