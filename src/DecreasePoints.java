import java.io.Serializable;

/**
 * Created by Sun on 06/24/2018.
 */
public class DecreasePoints extends Poison implements Serializable {
    final private String type = "decreasePoints";
    private static final long serialVersionUID = 1113799434508276969L;


    CreateBoard createBoard;

    public DecreasePoints() {
        super.type = type;
        createBoard = CreateBoard.createBoard;
    }

    public void doYourThing() {

        createBoard.addScore(-100);
    }
}



