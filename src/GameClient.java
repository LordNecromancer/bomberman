import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Time;


public class GameClient {
    private String ip;
    private int port;
    //  private JTextArea text;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Socket socket;
    private String name;
    private boolean isStarted = false;
    GameBoardCreator gameBoardCreator;
    static GameClient client;
    Player player;
    int width;
    int height;
    private String roomName;
    private JoiningOrCreatingGameRoomInterface createOrJoinRoom;
    private long time;

    GameClient() throws IOException, ClassNotFoundException {
        this.name = JOptionPane.showInputDialog("Choose Your Name  :");
        player = new Player(name);
        this.client = this;


        connectToServer();
    }

    private void connectToServer() throws IOException, ClassNotFoundException {

        this.port = getPort();
        this.ip = getIp();
        socket = new Socket(ip, port);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


        run();

        createOrJoinRoom = new JoiningOrCreatingGameRoomInterface(this);

        // text = createOrJoinRoom.textShown;

        createOrJoinRoom.run();


    }


    private String getIp() {
        return JOptionPane.showInputDialog("Enter IP address", "127.0.0.1");
    }

    private int getPort() {
        return Integer.valueOf(JOptionPane.showInputDialog("Enter port number", "8080"));
    }

    //@Override
    public void run() throws ClassNotFoundException, IOException {


        try {

            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No server found");

        }


        final String[] string = {""};

        Socket finalSocket = socket;
        final InputStream[] inputStream = {null};

        final Object[] object = {null};

        Thread receive = new Thread() {
            @Override
            public void run() {

                while (true) {


                    try {

                        object[0] = objectInputStream.readObject();

                    } catch (ClassNotFoundException e) {
                        // e.printStackTrace();
                    } catch (SocketException e) {
                        try {
                            socket.close();
                        } catch (IOException e1) {

                        }
                    } catch (IOException e) {


                        e.printStackTrace();
                        break;
                    }

                    if (object[0] instanceof Player) {
                        player = (Player) object[0];
                        if (gameBoardCreator != null)
                            gameBoardCreator.player = player;
                    } else if (object[0] instanceof GameComponent[][]) {

                        GameComponent[][] array = null;


                        array = (GameComponent[][]) object[0];


                        if (gameBoardCreator != null) {
                            gameBoardCreator.gameComponents = array;
                            gameBoardCreator.createFrame();
                        }
                    } else if (object[0] instanceof String) {


                        string[0] = (String) object[0];


                        System.out.println("received    " + string[0]);

                        if (string[0].equals("JoinedRoom$")) {

                            gameBoardCreator.setGameTime(new Time(client.time));
                            gameBoardCreator.setTimer();
                        }


                        if (string[0].equals("NoSuchRoom$")) {


                            createOrJoinRoom.getTextShown().setText("No such room exists." + "\r\n");

                        } else if (string[0].startsWith("#score$")) {
                            gameBoardCreator.refreshScore(string[0].substring(7));
                        } else if (string[0].startsWith("#width$")) {
                            width = Integer.valueOf(string[0].substring(7));
                        } else if (string[0].startsWith("#height$")) {
                            height = Integer.valueOf(string[0].substring(8));

                        } else if (string[0].startsWith("#time$")) {
                            time = Integer.valueOf(string[0].substring(6));
                            if (gameBoardCreator != null) {
                                gameBoardCreator.setGameTime(new Time(time));
                            }

                        } else if (string[0].startsWith("#playerX$")) {
                            player.setPlayerPositionX(Integer.valueOf(string[0].substring(9)));

                        } else if (string[0].startsWith("#playerY$")) {
                            player.setPlayerPositionY(Integer.valueOf(string[0].substring(9)));

                        } else {
                            if (string[0].startsWith("#roomName$")) {
                                roomName = string[0].substring(10);
                                GameManager gameManager = new GameManager(width, height, null, true);
                                gameManager.createBoard();
                                gameManager.init();
                                client.gameBoardCreator = gameManager.gameBoardCreator;
                                gameBoardCreator.player = player;

                                gameBoardCreator.setGameTime(new Time(time));
                                gameBoardCreator.setTimer();


                            } else if (string[0].equals("#lost$")) {
                                JOptionPane.showMessageDialog(null, "Game Over!!!", "Game Over!", 1);

                            } else if (string[0].startsWith("@")) {
                                createOrJoinRoom.getTextShown().setText(createOrJoinRoom.getTextShown().getText() + string[0] + "\r\n");
                            } else if (string[0].length() > name.length() + 5) {
                                createOrJoinRoom.getTextShown().setText(createOrJoinRoom.getTextShown().getText() + string[0] + "\r\n");
                            }
                        }
                    }
                }
            }
        };

        receive.start();
    }


    void send(String msg) throws IOException {

        try {


            System.out.println(msg);
            if (!isStarted) {
                objectOutputStream.writeObject("#name$" + name);
                isStarted = true;
            }
            objectOutputStream.writeObject(msg);
            objectOutputStream.flush();


        } catch (IOException e) {
            createOrJoinRoom.getTextShown().append("\r\n" + "Sorry the server is unavailable right now :(");

        }
    }


    void getRoomsList() throws IOException {

        this.send("#getRoomsList$");
    }
}
