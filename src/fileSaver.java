import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Sun on 04/14/2018.
 */
public class fileSaver extends JFrame {
    JPanel contentPane = (JPanel) getContentPane();


    public fileSaver(GameManager mf) throws IOException, ClassNotFoundException {


        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        init(mf);

    }

    public void init(GameManager mf) throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        JButton save = new JButton("Save");
        int retval = fileChooser.showSaveDialog(save);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                return;
            }
            String path = file.getPath();
            FileOutputStream fos = new FileOutputStream(fileChooser.getName());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mf);


        }
    }
}
