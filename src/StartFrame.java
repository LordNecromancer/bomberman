import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StartFrame extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;
    private int w = 400;
    private int h = 350;
    private int startingPointWidth = width / 2 - w / 2;
    private int startingPointHeight = height / 2 - h / 2;
    private JLabel icon;
    //boolean isOnline = false;

    public void run() {


        ImageIcon i = new ImageIcon("icon.png");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(startingPointWidth, startingPointHeight, w, h);
        JPanel contentPane = createContentPane(this);

        JTextField getHeight = new JTextField(5);
        JTextField getWidth = new JTextField(5);
        createMiddle(i, contentPane, getHeight, getWidth);


        JButton submit = new JButton("Let's Go !!!");
        submit.setSize(100, 50);
        contentPane.add(submit, BorderLayout.SOUTH);
        JButton load = new JButton("Load Game");
        contentPane.add(load, BorderLayout.NORTH);
        this.setVisible(true);


        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submit) {
                    // try {
                    int widthEntered = Integer.valueOf(getWidth.getText());
                    int heightEntered = Integer.valueOf(getHeight.getText());

/**
 * Important point :   TO make a game an instance of game manager is created and it
 * handles everything. You could specify all aspects of the game when you are creating the gameManager.
 */

                    GameManager gameManager = new GameManager(widthEntered, heightEntered, null, false);
                    gameManager.createBoard();
                    gameManager.init();
                }


            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == load) {

                    GameManager.load();
                }
            }
        });
    }

    private JPanel createContentPane(JFrame start) {
        JPanel contentPane = (JPanel) start.getContentPane();
        contentPane.setBounds(startingPointWidth, startingPointHeight, w, h);
        contentPane.setBackground(Color.CYAN);
        contentPane.setLayout(new BorderLayout());
        return contentPane;
    }

    private void createMiddle(ImageIcon i, JPanel contentPane, JTextField heightEntered, JTextField widthEntered) {
        heightEntered.setText("Width");
        widthEntered.setText("Height");
        widthEntered.setSize(100, 20);
        heightEntered.setSize(100, 20);
        contentPane.add(heightEntered, BorderLayout.EAST);
        contentPane.add(widthEntered, BorderLayout.WEST);
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        contentPane.add(center, BorderLayout.CENTER);
        center.setBackground(Color.WHITE);
        icon = new JLabel(i);
        center.add(icon);
    }
}
