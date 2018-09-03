import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;


public class ActionListeners implements KeyListener, Serializable {
    GameBoardCreator gameBoardCreator;

    public ActionListeners(GameBoardCreator gameBoardCreator) {
        this.gameBoardCreator = gameBoardCreator;

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        /**
         * Code for key actions.
         * Ctrl + S ......-> Saves the game
         * Ctrl + O ......-> Loads the game
         * UP ; Down ; Left ; Right
         * B ......-> for planting the bomb
         */


        if (!gameBoardCreator.isOnline()) {
            if ((e.getKeyCode() == KeyEvent.VK_S) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

                GameManager.saveFile(gameBoardCreator);
            }

            if ((e.getKeyCode() == KeyEvent.VK_O) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

                GameManager.load();
                gameBoardCreator.dispose();
            }
        }

        int playerx = gameBoardCreator.player.getPlayerPositionX();
        int playery = gameBoardCreator.player.getPlayerPositionY();
        if (gameBoardCreator.player.isAlive()) {
            if (gameBoardCreator.isPassed()) {
                try {
                    gameBoardCreator.setDate(Date.from(Instant.now()));

                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {


                        if (gameBoardCreator.isOnline()) {
                            goDown(playerx, playery);
                        } else {
                            moveOffline(playerx + 1, playery);
                        }

                    }
                    if (e.getKeyCode() == KeyEvent.VK_UP) {


                        if (gameBoardCreator.isOnline()) {
                            goUp(playerx, playery);
                        } else {
                            moveOffline(playerx - 1, playery);
                        }
                    }


                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (gameBoardCreator.isOnline()) {
                            goRight(playerx, playery);
                        } else {
                            moveOffline(playerx, playery + 1);
                        }
                    }


                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                        if (gameBoardCreator.isOnline()) {
                            goLeft(playerx, playery);
                        } else {
                            moveOffline(playerx, playery - 1);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_B) {
                if (gameBoardCreator.isOnline()) {
                    try {
                        GameClient.client.send("#bomb$");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    GameBoardCreator.player.plantBomb();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_N) {
                if (!gameBoardCreator.isOnline()) {
                    if (gameBoardCreator.player.isBombControl() && gameBoardCreator.player.isAlive()) {
                        try {
                            gameBoardCreator.player.getBombCells().get(0).getBombThread().explode();
                        } catch (InterruptedException e1) {
                            System.out.println("No bomb to explode");
                        }
                    }
                } else {
                    try {
                        GameClient.client.send("#now$");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    private void goDown(int playerX, int playerY) throws IOException {
        if (gameBoardCreator.isOnline()) {
            sendNewPosition(playerX + 1, playerY);
        }
    }

    private void goUp(int playerX, int playerY) throws IOException {
        if (gameBoardCreator.isOnline()) {
            sendNewPosition(playerX - 1, playerY);
        }
    }

    private void goRight(int playerX, int playerY) throws IOException {

        if (gameBoardCreator.isOnline()) {
            sendNewPosition(playerX, playerY + 1);
        }
    }

    private void sendNewPosition(int playerNewPositionX, int playerNewPositionY) throws IOException {

        if (gameBoardCreator.player.isAlive()) {

            GameClient.client.send("#playerX$" + playerNewPositionX);
            GameClient.client.send("#playerY$" + playerNewPositionY);
        }
    }

    private void goLeft(int playerX, int playerY) throws IOException {
        if (gameBoardCreator.isOnline()) {
            sendNewPosition(playerX, playerY - 1);
        }
    }

    private void moveOffline(int playerX, int playerY) {
        GameComponent cell = gameBoardCreator.gameComponents[playerX][playerY];

        if (cell.getPassable()) {

            updateContentPane(gameBoardCreator.player.getPlayerPositionX(), gameBoardCreator.player.getPlayerPositionY());


            gameBoardCreator.gameComponents[playerX][playerY] = gameBoardCreator.player;
            gameBoardCreator.player.setPlayerPositionY(playerY);
            gameBoardCreator.player.setPlayerPositionX(playerX);
        }
        if (cell instanceof StatChanger) {
            updateContentPane(gameBoardCreator.player.getPlayerPositionX(), gameBoardCreator.player.getPlayerPositionY());

            gameBoardCreator.checkIfIsStatChanger(cell);
            gameBoardCreator.labels[playerX][playerY].setText("");
            gameBoardCreator.gameComponents[playerX][playerY] = gameBoardCreator.player;
            gameBoardCreator.player.setPlayerPositionY(playerY);
            gameBoardCreator.player.setPlayerPositionX(playerX);
        }

        if (cell.getType().equals("door")) {
            if (gameBoardCreator.checkIfICanGoToNextLevel()) {
                gameBoardCreator.goToNextLevel();
            }
        }


        gameBoardCreator.createFrame();


    }

    private void updateContentPane(int playerx, int playery) {

        if (gameBoardCreator.player.isAlive()) {
            if (!gameBoardCreator.gameComponents[playerx][playery].getType().equals("bomb")) {
                gameBoardCreator.gameComponents[playerx][playery] = new FieldCell();
            }
            gameBoardCreator.player.setBombSet(false);

            gameBoardCreator.player.setCurrentBomb(null);
        }
    }


}
