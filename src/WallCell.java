import java.io.Serializable;


public class WallCell extends GameComponent implements Serializable {


    private final String type = "wall";
    private static final long serialVersionUID = 1119742734515296969L;


    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {

    }

    public WallCell() {

        super.setType(type);
        super.setPassable(false);
        super.setExplosive(false);


    }
}
