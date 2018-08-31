import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;


public class ActionListeners implements KeyListener, Serializable {
    CreatingGameBoard creatingGameBoard;

    public ActionListeners(CreatingGameBoard creatingGameBoard) {
        this.creatingGameBoard = creatingGameBoard;

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


        if (!creatingGameBoard.isOnline) {
            if ((e.getKeyCode() == KeyEvent.VK_S) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

                GameManager.saveFile(creatingGameBoard);
            }

            if ((e.getKeyCode() == KeyEvent.VK_O) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

                GameManager.load();
                creatingGameBoard.dispose();
            }
        }

        int playerx = creatingGameBoard.player.playerPositionX;
        int playery = creatingGameBoard.player.playerPositionY;
        if (creatingGameBoard.player.isAlive) {
            if (creatingGameBoard.isPassed()) {
                try {
                    creatingGameBoard.date = Date.from(Instant.now());

                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {


                        if (creatingGameBoard.isOnline) {
                            goDown(playerx, playery);
                        } else {
                            moveOffline(playerx + 1, playery);
                        }

                    }
                    if (e.getKeyCode() == KeyEvent.VK_UP) {


                        if (creatingGameBoard.isOnline) {
                            goUp(playerx, playery);
                        } else {
                            moveOffline(playerx - 1, playery);
                        }
                    }


                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (creatingGameBoard.isOnline) {
                            goRight(playerx, playery);
                        } else {
                            moveOffline(playerx, playery + 1);
                        }
                    }


                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                        if (creatingGameBoard.isOnline) {
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
                if (creatingGameBoard.isOnline) {
                    try {
                        GameClient.client.send("#bomb$");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    creatingGameBoard.plantBomb();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_N) {
                if (!creatingGameBoard.isOnline) {
                    if (creatingGameBoard.player.bombControl && creatingGameBoard.player.isAlive) {
                        try {
                            creatingGameBoard.player.bombCells.get(0).bombThread.explode();
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
        if (creatingGameBoard.isOnline) {
            sendNewPosition(playerX + 1, playerY);
        }
    }

    private void goUp(int playerX, int playerY) throws IOException {
        if (creatingGameBoard.isOnline) {
            sendNewPosition(playerX - 1, playerY);
        }
    }

    private void goRight(int playerX, int playerY) throws IOException {

        if (creatingGameBoard.isOnline) {
            sendNewPosition(playerX, playerY + 1);
        }
    }

    private void sendNewPosition(int playerNewPositionX, int playerNewPositionY) throws IOException {

        if (creatingGameBoard.player.isAlive) {

            GameClient.client.send("#playerX$" + playerNewPositionX);
            GameClient.client.send("#playerY$" + playerNewPositionY);
        }
    }

    private void goLeft(int playerX, int playerY) throws IOException {
        if (creatingGameBoard.isOnline) {
            sendNewPosition(playerX, playerY - 1);
        }
    }

    private void moveOffline(int playerX, int playerY) {
        GameComponent cell = creatingGameBoard.gameComponents[playerX][playerY];

        if (cell.passable) {

            updateContentPane(creatingGameBoard.player.playerPositionX, creatingGameBoard.player.playerPositionY);


            creatingGameBoard.gameComponents[playerX][playerY] = creatingGameBoard.player;
            creatingGameBoard.player.playerPositionY = playerY;
            creatingGameBoard.player.playerPositionX = playerX;
        }
        if (cell instanceof StatChanger) {
            updateContentPane(creatingGameBoard.player.playerPositionX, creatingGameBoard.player.playerPositionY);

            creatingGameBoard.checkIfIsStatChanger(cell);
            creatingGameBoard.labels[playerX][playerY].setText("");
            creatingGameBoard.gameComponents[playerX][playerY] = creatingGameBoard.player;
            creatingGameBoard.player.playerPositionY = playerY;
            creatingGameBoard.player.playerPositionX = playerX;
        }

        if (cell.type.equals("door")) {
            if (creatingGameBoard.checkIfICanGoToNextLevel()) {
                creatingGameBoard.goToNextLevel();
            }
        }


        creatingGameBoard.createFrame();


    }

    private void updateContentPane(int playerx, int playery) {

        if (creatingGameBoard.player.isAlive) {
            if (!creatingGameBoard.gameComponents[playerx][playery].type.equals("bomb")) {
                creatingGameBoard.gameComponents[playerx][playery] = new FieldCell();
            }
            creatingGameBoard.player.bombSet = false;

            creatingGameBoard.player.currentBomb = null;
        }
    }


}
