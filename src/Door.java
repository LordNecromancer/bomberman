import java.io.Serializable;


public class Door extends GameComponent implements Serializable {
    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {

    }

    private static final long serialVersionUID = 1114742734505296969L;

    final private String type = "door";

    public Door() {
        super.setType(type);
        super.setExplosive(false);
        super.setNeverPassable(true);


    }


}
