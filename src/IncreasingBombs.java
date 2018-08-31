import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public class IncreasingBombs extends PowerUps implements Serializable {

    final private String type = "increaseBombs";
    private static final long serialVersionUID = 1113799434508306969L;


    public IncreasingBombs() {
        super.type = type;
    }

    public void doYourThing() {
        CreatingGameBoard.player.bombNum = CreatingGameBoard.player.bombNum + 1;
    }

}
