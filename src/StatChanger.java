import java.io.Serializable;

/**
 * Created by Sun on 06/22/2018.
 */
public abstract class StatChanger extends GameComponent implements Serializable {
    private static final long serialVersionUID = 1113799434508676096L;


    StatChanger() {
        super.setExplosive(false);
        super.setNeverPassable(true);
    }

    public abstract void doYourThing();

    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {
    }

}
