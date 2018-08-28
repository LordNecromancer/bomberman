import java.io.Serializable;


public class Door extends Cell implements Serializable {
    final private String type = "door";

    public Door() {
        super.type = type;
        super.isExplosive = false;
        super.neverPassable = true;


    }


}
