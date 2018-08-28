import java.io.Serializable;

/**
 * Created by Sun on 03/31/2018.
 */
public class FieldCell extends Cell implements Serializable {

    private final String type = "field";

    public FieldCell() {
        super.type = type;
        super.passable = true;


    }
}