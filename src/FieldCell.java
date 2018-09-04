import java.io.Serializable;

/**
 * Created by Sun on 03/31/2018.
 */
public class FieldCell extends GameComponent implements Serializable {
    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {

    }

    private static final long serialVersionUID = 1119342734515296969L;


    private final String type = "field";

    public FieldCell() {
        super.setType(type);
        super.setPassable(true);
        super.setPassable(true);


    }
}