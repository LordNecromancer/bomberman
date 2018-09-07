import javax.swing.*;
import java.sql.*;

class GameSaver {

    private int gameId;

    void saveFile(GameBoardCreator gameBoardCreator) {

        String url = "jdbc:mysql://localhost:3306/bomberman";
        String username = "root";
        String password = "";
        Statement statement;
        Connection connection;
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

    private void addToGameComponentsArray(GameBoardCreator gameBoardCreator, Connection connection) {
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

    private void insertIntoGameComponentTable(GameBoardCreator gameBoardCreator, Connection connection, String str2) throws SQLException {
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


    private void addToGameSpecs(GameBoardCreator gameBoardCreator, Connection connection) {
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

    private void insertIntoGameSpecsTable(GameBoardCreator gameBoardCreator, Connection connection) throws SQLException {
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

    private void addToGameTable(GameBoardCreator gameBoardCreator, Statement statement, Connection connection, String name) {
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

    private PreparedStatement insertIntoGameTable(GameBoardCreator gameBoardCreator, Connection connection, String name) throws SQLException {
        String str2 = "insert into bomberManTest (gameName,width,height) " + "values (?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(str2, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, gameBoardCreator.width);
        preparedStatement.setInt(3, gameBoardCreator.height);

        preparedStatement.execute();
        return preparedStatement;
    }
}