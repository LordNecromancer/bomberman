import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Created by Sun on 07/31/2018.
 */
public class MainFrameGraphics implements Serializable {
    CreateBoard createBoard;

    public MainFrameGraphics(CreateBoard createBoard) {
        this.createBoard = createBoard;
    }

    private ImageIcon hurdle = new ImageIcon("obstacle.png");
    private ImageIcon wall = new ImageIcon("brick.png");
    private ImageIcon bombIcon = new ImageIcon("bomb.png");
    private ImageIcon field = new ImageIcon("grass.jpg");
    private ImageIcon man = new ImageIcon("man.png");
    private ImageIcon manBomb = new ImageIcon("manbomb.png");
    private ImageIcon enemyLvL1 = new ImageIcon("enemyLvL1.png");
    private ImageIcon enemyLvL2 = new ImageIcon("enemyLvL2.png");
    private ImageIcon enemyLvL3 = new ImageIcon("enemyLvL3.png");
    private ImageIcon enemyLvL4 = new ImageIcon("enemyLvL4.png");
    private ImageIcon door = new ImageIcon("door.png");

    private JPanel contentPane;
    JPanel center;
    public JLabel score;
    public JLabel time;
    private JPanel top;
    private ActionListeners listeners;


    void crateGameFrame(int w, int h, int dw, int dh) {
        createBoard.setTitle("BomberMan");
        createBoard.setSize(dh, dw + dw / 8);
        createBoard.setVisible(true);
        createBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = (JPanel) createBoard.getContentPane();
        contentPane.setBackground(Color.black);
        createTopPanel(dw, dh);
        createCenterPanel(w, h, dw, dh);
        contentPane.add(top, BorderLayout.NORTH);
        contentPane.add(center, BorderLayout.CENTER);
    }

    private void createTopPanel(int dw, int dh) {
        top = new JPanel();
        top.setPreferredSize(new Dimension(dh, dw / 8));
        top.setLayout(new BorderLayout());
        createTimeLabel();

        createScoreLabel();
        top.add(score, BorderLayout.EAST);
        top.add(time, BorderLayout.WEST);
        top.setBackground(Color.darkGray);
    }

    private void createScoreLabel() {
        score = new JLabel(Integer.toString(createBoard.points));
        score.setForeground(Color.CYAN);
        score.setSize(top.getWidth() / 2, top.getHeight());
    }

    private void createTimeLabel() {
        time = new JLabel();
        time.setForeground(Color.CYAN);
        time.setBackground(Color.orange);
        time.setAlignmentX(Component.CENTER_ALIGNMENT);
        time.setVerticalAlignment(SwingConstants.CENTER);
        time.setHorizontalTextPosition(SwingConstants.CENTER);
        time.setSize(top.getWidth() / 2 + 100, top.getHeight() + 100);
    }

    private void createCenterPanel(int w, int h, int dw, int dh) {
        center = new JPanel();
        center.setLayout(new GridLayout(2 + w, 2 + h));
        center.setBackground(Color.black);
        center.setPreferredSize(new Dimension(dh / 2, dw / 2));
        listeners = new ActionListeners(createBoard);
        createBoard.addKeyListener(listeners);
        for (int i = 0; i < w + 2; i++) {
            for (int j = 0; j < h + 2; j++) {
                createBoard.labels[i][j] = new JLabel();

            }
        }
    }

    public JLabel showTheObject(int i, int j) {

        JLabel label = createBoard.labels[i][j];


        switch (createBoard.gameComponents[i][j].type) {
            case "player":
                label = new JLabel(man);
                break;
            case "field":
                label = new JLabel(field);
                break;
            case "bomb":
                label = new JLabel(getBombIcon(i, j));
                break;
            case "obstacle":
                label = new JLabel(hurdle);
                break;
            case "wall":
                label = new JLabel(wall);
                break;
            case "enemyLvL1":
                label = new JLabel(enemyLvL1);
                break;
            case "enemyLvL2":
                label = new JLabel(enemyLvL2);
                break;
            case "enemyLvL3":
                label = new JLabel(enemyLvL3);
                break;
            case "enemyLvL4":
                label = new JLabel(enemyLvL4);
                break;
            case "door":
                label = new JLabel(door);
                break;
            case "decreaseBombs":
                label = new JLabel("DBombs");
                break;
            case "decreasePoints":
                label = new JLabel("DPoints");
                break;
            case "decreaseRadius":
                label = new JLabel("DRadius");
                break;
            case "decreaseSpeed":
                label = new JLabel("DSpeed");
                break;
            case "loseBombControl":
                label = new JLabel("LBC");
                break;
            case "bombControl":
                label = new JLabel("BC");
                break;
            case "increaseSpeed":
                label = new JLabel("ISpeed");
                break;
            case "increaseRadius":
                label = new JLabel("IRadius");
                break;
            case "increasePoints":
                label = new JLabel("IPoints");
                break;
            case "increaseBombs":
                label = new JLabel("IBombs");
                break;
            case "ghostAbility":
                label = new JLabel("Ghost");
                break;
        }

        return label;
    }
//    private void addKeyListenerToFrame() {
//
//
//        graphics.center.addKeyListener(listeners);
//
//
//    }

    public ImageIcon getBombIcon(int i, int j) {
        if (createBoard.player.playerPositionX == i && createBoard.player.playerPositionY == j) {
            return manBomb;
        } else {
            return bombIcon;
        }
    }
}
