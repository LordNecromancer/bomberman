import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Sun on 08/12/2018.
 */
public class JoiningOrCreatingGameRoomInterface extends JFrame {


    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = screenSize.width;
    private int height = screenSize.height;
    private int w = 400;
    private int h = 350;
    private int startingPointWidth = width / 2 - w / 2;
    private int startingPointHeight = height / 2 - h / 2;
    private JTextArea textShown;
    private JButton joinRoom;
    private JButton createRoom;
    private JTextField textField;
    private int widthEntered;
    private int heightEntered;
    private GameClient client;
    private boolean isStarted = false;
    private SettingUpGraphics graphics = new SettingUpGraphics(width, height);

    public JoiningOrCreatingGameRoomInterface(GameClient client) throws IOException {
        this.client = client;
    }


    public void run() throws IOException {


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(startingPointWidth, startingPointHeight, w, h);
        JPanel contentPane = graphics.createContentPane(this);
        joinRoom = new JButton("Join");
        joinRoom.setSize(100, 50);
        // contentPane.add(joinRoom, BorderLayout.SOUTH);
        createRoom = new JButton("Create");
        createRoom.setSize(100, 50);
        contentPane.add(joinRoom, BorderLayout.EAST);
        contentPane.add(createRoom, BorderLayout.WEST);

        textField = new JTextField("Enter the room name here to join...");
        contentPane.add(textField, BorderLayout.NORTH);
        JPanel center = graphics.createMiddle(contentPane);

        setUpTextArea(center);

        addActionListeners();
        this.setVisible(true);


    }


    private void setUpTextArea(JPanel center) throws IOException {
        textShown = new JTextArea();
        textShown.setEditable(false);
        textShown.setVisible(true);
        textShown.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textShown);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret) textShown.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        center.add(scrollPane);
        client.getRoomsList();
    }

    private void addActionListeners() {
        joinRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    client.send(textField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                textField.setText("");
            }

        });

        createRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GameRoomCreator gameRoomCreator = new GameRoomCreator(client);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    public JTextArea getTextShown() {
        return textShown;
    }

    public void setTextShown(JTextArea textShown) {
        this.textShown = textShown;
    }
}
