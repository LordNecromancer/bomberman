import java.io.Serializable;


public class WallCell extends Cell implements Serializable {

    private final String type = "wall";

    public WallCell() {

        super.type = type;
        super.passable = false;
        super.isExplosive = false;


    }
}
