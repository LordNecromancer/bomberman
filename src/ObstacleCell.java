import java.io.Serializable;

/**
 * Created by Sun on 05/31/2018.
 */
public class ObstacleCell extends GameComponent implements Serializable {
    private static final long serialVersionUID = 1119442734515296969L;


    private final String type = "obstacle";
    boolean hasDoor = false;
    PowerUps powerUps = null;
    Poison poison = null;

    @Override
    public void destroy(GameBoardCreator gameBoardCreator, int i, int j) {

        ObstacleCell obstacleCell = (ObstacleCell) gameBoardCreator.gameComponents[i][j];
        gameBoardCreator.addScore(GameBoardCreator.getObstacleScore());
        if (obstacleCell.hasDoor) {
            gameBoardCreator.gameComponents[i][j] = new Door();


        } else if (obstacleCell.powerUps != null) {
            gameBoardCreator.gameComponents[i][j] = obstacleCell.powerUps;


        } else if (obstacleCell.poison != null) {
            gameBoardCreator.gameComponents[i][j] = obstacleCell.poison;


        } else {
            gameBoardCreator.gameComponents[i][j] = new FieldCell();
        }

    }

    public ObstacleCell() {
        super.setType(type);


    }
}
