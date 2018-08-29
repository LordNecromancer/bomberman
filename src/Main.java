import com.sun.security.ntlm.NTLMException;

import javax.swing.*;
import java.io.IOException;


public abstract class Main {
    public static void main(String[] args) throws IOException, NTLMException, ClassNotFoundException {


        int onlineOrNot = JOptionPane.showConfirmDialog(null, "Do you wish to play online?", "How do you wish to play", JOptionPane.YES_NO_OPTION);
        if (onlineOrNot == JOptionPane.YES_OPTION) {

            GameClient client = new GameClient();
        } else {
            StartFrame sf = new StartFrame();
            sf.run();
        }


    }
}
