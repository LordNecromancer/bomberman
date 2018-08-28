import javax.swing.*;
import java.awt.*;

/**
 * Created by Sun on 08/11/2018.
 */
public class SetUpGraphics {
    private int w = 400;
    private int h = 350;
    private int width;
    private int height;
    private int startingPointWidth = width / 2 - w / 2;
    private int startingPointHeight = height / 2 - h / 2;

    public SetUpGraphics(int width, int height) {
        this.width = width;
        this.height = height;
    }

    JPanel createContentPane(JFrame start) {
        JPanel contentPane = (JPanel) start.getContentPane();
        contentPane.setBounds(startingPointWidth, startingPointHeight, w, h);
        contentPane.setBackground(Color.CYAN);
        contentPane.setLayout(new BorderLayout());
        return contentPane;
    }

    JPanel createMiddle(JPanel contentPane) {


        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        contentPane.add(center, BorderLayout.CENTER);
        center.setBackground(Color.WHITE);
        return center;


    }
}
