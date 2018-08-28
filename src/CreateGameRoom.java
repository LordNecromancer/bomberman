import javax.swing.*;
import java.io.IOException;

/**
 * Created by Sun on 08/12/2018.
 */
public class CreateGameRoom {

    int width;
    int height;
    private String roomName;
    private GameClient client;

    public CreateGameRoom(GameClient client) throws IOException {
        this.client = client;
        width = Integer.valueOf(JOptionPane.showInputDialog("choose width"));
        height = Integer.valueOf(JOptionPane.showInputDialog("choose height"));
        roomName = JOptionPane.showInputDialog("Enter the room name");

        sendInitials();


    }

    private void sendInitials() throws IOException {
        client.send("#width$" + width);
        client.send("#height$" + height);
        client.send(("#roomName$" + roomName));
    }


}
