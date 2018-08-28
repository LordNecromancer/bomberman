import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Sun on 04/14/2018.
 */
public class fileLoader extends JFrame {
    JPanel contentPane = (JPanel) getContentPane();


    public fileLoader() throws IOException, ClassNotFoundException {


        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
        init();

    }

    public void init() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        contentPane.add(fileChooser);
        File file = fileChooser.getSelectedFile();
        String path = file.getPath();
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        GameManager result = (GameManager) objectInputStream.readObject();


    }
}
