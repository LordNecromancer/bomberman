import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Sun on 04/14/2018.
 */
public class GameLoader extends JFrame {
    private String url = "jdbc:mysql://localhost:3306/bomberman";
    private String username = "root";
    private String password = "";
    private String name = JOptionPane.showInputDialog("Enter your file name  ");
    Player player = new Player("");
    private Boolean bombSet = false;
    private Boolean bombControl = false;
    private boolean isAlive = true;
    private int playerSpeed = 0;
    GameComponent[][] gameComponents = null;
    private int width = 0;
    private int height = 0;
    private int bombNum = 0;
    private int bombCount = 0;
    private int gameTime = 0;
    private int score = 0;
    private int bombRadius = 1;
    private ArrayList<Integer> bombsX = new ArrayList<>();
    private ArrayList<Integer> bombsY = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int gameId;

    void load()

    {
        System.out.println("Connecting database...");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            int k = 0;


            loadGameIdentification(statement, k);
            loadGameSpecs(statement);
            loadGameComponentArray(statement);
            GameManager gameManager = new GameManager(width, height, gameComponents, false);
            gameManager.player = player;


            gameManager.createBoard();
            setUpEnemyArray(gameManager);
            setUpBombCells(gameManager);
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

    private void setUpEnemyArray(GameManager gameManager) {
        for (int i = 0; i < enemies.size(); i++) {
            gameManager.gameBoardCreator.getEnemies().add(enemies.get(i));
        }
    }

    private void setUpBombCells(GameManager gameManager) {
        for (int i = 0; i < bombsX.size(); i++) {
            int XComponent = bombsX.get(i);
            int YComponent = bombsY.get(i);
            BombCell bombCell = new BombCell(player.getBombRadius(), gameManager.getBombExplosionTime(), gameManager.gameBoardCreator, XComponent, YComponent);
            gameComponents[XComponent][YComponent] = bombCell;
            player.getBombCells().add(bombCell);
            if (i == 0) {
                player.setCurrentBomb(bombCell);
            }


        }
    }

    private void loadGameComponentArray(Statement statement) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        ResultSet result;

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
    }

    private void loadGameSpecs(Statement statement) throws SQLException {
        ResultSet result;
        result = statement.executeQuery("select * from bombermanspecs WHERE gameId='" + gameId + "'");
        while (result.next()) {

            getSpecs(result);
        }
        setPlayerSpecs();
    }

    private void setPlayerSpecs() {
        player.setBombSet(bombSet);
        player.setAlive(isAlive);
        player.setBombControl(bombControl);
        player.setPlayerSpeed(playerSpeed);
        player.setBombRadius(bombRadius);
        player.setBombCount(bombCount);
        player.setBombNum(bombNum);
    }

    private void getSpecs(ResultSet result) throws SQLException {
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

    private void loadGameIdentification(Statement statement, int k) throws SQLException {
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
    }

}
