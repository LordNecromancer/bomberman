
import javax.swing.*;
import java.io.*;
import java.sql.*;

/**
 * Creating the Main class. Almost everything happens here.
 * It implements Serializable so that we can load and save objects.
 */
public class GameManager implements Serializable {


    private int dimension = 50;
    final private Time gameTime = new Time(300);
    private int obstacleCount;
    private int boardWidth;
    private int boardHeight;
    private int bombLimit = 1;
    private int level = 1;
    private int points = 0;
    boolean isOnline;
    Player player=null;
    CreateBoard createBoard;
    GameComponent[][] gameComponents=null;


    /**
     * Constructing the frame.
     * If there exists a board-meaning we want to load a saved game-we would call the method init() ; passing the
     * existing board. If no board exist a new board is made.
     */

    public GameManager(int w, int h,GameComponent[][]gameComponents, Boolean isOnline) {


        this.boardWidth = w;
        this.boardHeight = h;
        this.isOnline = isOnline;
        this.gameComponents=gameComponents;
        obstacleCount = (w + h) / 2;
        // player = new Player(playerx, playery);
       // createBoard();

    }

    public void createBoard() {

        createBoard = new CreateBoard(this, boardWidth, boardHeight, gameComponents, player, 0, isOnline);
        createBoard.init();
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

    public void goToNextLevel(CreateBoard createBoard) {
        level++;

        this.createBoard = new CreateBoard(this, createBoard.width, createBoard.height, null, createBoard.player, CreateBoard.bombNum, isOnline);
        this.createBoard.init();
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


    static void saveFile(CreateBoard createBoard) {

        String url = "jdbc:mysql://localhost:3306/bomberman";
        String username = "root";
        String password = "";
        Statement statement;
        Connection connection;

//
        String name=JOptionPane.showInputDialog("Enter your file name  ");
        System.out.println("Connecting database...");

        try { connection = DriverManager.getConnection(url, username, password);
            System.out.println(connection);
             statement=connection.createStatement();
            System.out.println(statement);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        try{
            StringBuilder string=new StringBuilder("CREATE TABLE "+name);
            string.append("(type VARCHAR(50)  ,");
            string.append("XComponent INT(64) ,");
            string.append("YComponent INT(64) ,");
            string.append("width INT(64) ,");
            string.append("height INT(64) )");



            statement.executeUpdate(string.toString());

          //  String str="insert into "+name+" (type,XComponent,YComponent) "+"values (?,?,?)";
            String str2="insert into "+name+" (type,XComponent,YComponent,width,height) "+"values (?,?,?,?,?)";

           //PreparedStatement preparedStatement=connection.prepareStatement(str);
            PreparedStatement preparedStatement=connection.prepareStatement(str2);
            for (int i = 0; i <createBoard.width+2 ; i++) {
                for (int j = 0; j <createBoard.height+2 ; j++) {
                    preparedStatement.setString(1,createBoard.gameComponents[i][j].getClass().getName());
                    preparedStatement.setInt(2,i);
                    preparedStatement.setInt(3,j);
                    preparedStatement.setInt(4,createBoard.width);
                    preparedStatement.setInt(5,createBoard.height);


                    preparedStatement.execute();
                }

            }
            preparedStatement.close();
            statement.close();
//            ResultSet result=statement.executeQuery("select * from bomberman");
//            while (result.next()){
//                System.out.println(result.getString("type"));
//            }
            System.out.println("Database connected!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Not Good syntax", e);

        }
        try{
            statement=connection.createStatement();

            StringBuilder string=new StringBuilder("CREATE TABLE #"+name);
            string.append("(bombSet BOOL,");
            string.append("bombControl BOOL,");
            string.append("playerSpeed INT(64),");
            string.append("isAlive BOOL)");




            statement.executeUpdate(string.toString());

            //  String str="insert into "+name+" (type,XComponent,YComponent) "+"values (?,?,?)";
            String str2="insert into #player$"+name+" (type,XComponent,YComponent,width,height) "+"values (?,?,?,?,?)";

            //PreparedStatement preparedStatement=connection.prepareStatement(str);
            PreparedStatement preparedStatement=connection.prepareStatement(str2);

                    preparedStatement.setBoolean(1,createBoard.player.bombSet);
                    preparedStatement.setBoolean(2,createBoard.player.bombControl);
                    preparedStatement.setInt(3,(int) createBoard.player.playerSpeed);
                    preparedStatement.setBoolean(4,createBoard.player.isAlive);


                    preparedStatement.execute();



            connection.close();
//            ResultSet result=statement.executeQuery("select * from bomberman");
//            while (result.next()){
//                System.out.println(result.getString("type"));
//            }
            System.out.println("Database connected!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Not Good syntax", e);

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
//                oos.writeObject(createBoard);
//                oos.close();
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
    }

     static void load()

    {

        String url = "jdbc:mysql://localhost:3306/bomberman";
        String username = "root";
        String password = "";
        String name=JOptionPane.showInputDialog("Enter your file name  ");
        Player player=null;
        Boolean bombSet=false;
        Boolean bombControl=false;
        boolean isAlive=true;
        int playerSpeed=0;

//
        System.out.println("Connecting database...");

        try {Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement=connection.createStatement();
            ResultSet result=statement.executeQuery("select * from "+name);
            int k=0;
            GameComponent[][] gameComponents =null;
            int width=0;
            int height=0;
            while (result.next()){
                if (k==0){
                   width=result.getInt("width");
                     height=result.getInt("height");
                    gameComponents=new GameComponent[width+2][height+2];
                    k++;

                }

                    String type=(result.getString("type"));
                int XComponent=result.getInt("XComponent");
                int YComponent=result.getInt("YComponent");
                try {
                    if(!type.equals("Player")) {
                        gameComponents[XComponent][YComponent] = (GameComponent) Class.forName(type).newInstance();
                    }
                    else {
                        player= new Player("");
                        gameComponents[XComponent][YComponent]=player;
                        player.playerPositionX=XComponent;
                        player.playerPositionY=YComponent;

                    }

                    ResultSet result2=statement.executeQuery("select * from #"+name);

                    while (result.next()){

                            bombSet=result2.getBoolean("bombSet");
                        bombControl=result2.getBoolean("bombControl");
                        playerSpeed=result2.getInt("playerSpeed");
                        isAlive=result2.getBoolean("isAlive");
                        player.bombSet=bombSet;
                        player.isAlive=isAlive;
                        player.bombControl=bombControl;
                        player.playerSpeed=playerSpeed;


                    }
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
         GameManager gameManager=new GameManager(width,height,gameComponents,false);
            gameManager.player=player;
            gameManager.createBoard();

            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
//        {
//            File file = getFile(0);
//
//            try {
//
//                FileInputStream fis = new FileInputStream(file.getPath());
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                CreateBoard result = (CreateBoard) ois.readObject();
//
//                CreateBoard newCreateBoard = new CreateBoard(result.gameManager, result.width, result.height, result.gameComponents, result.player, result.bombCount, result.isOnline);
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
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
