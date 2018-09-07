import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sun on 09/07/2018.
 */
public class Music extends Thread {
    AudioInputStream audioInputStream;
    Clip clip;

    @Override
    public void run() {


        try {
            audioInputStream =
                    AudioSystem.getAudioInputStream(new File("music.wav").getAbsoluteFile());
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
