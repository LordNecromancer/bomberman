import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;


public class ActionListeners implements KeyListener, Serializable {
    CreateBoard createBoard;

    public ActionListeners(CreateBoard createBoard) {
        this.createBoard = createBoard;

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


        if (!createBoard.isOnline) {
            if ((e.getKeyCode() == KeyEvent.VK_S) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

                GameManager.saveFile(createBoard);
            }

            if ((e.getKeyCode() == KeyEvent.VK_O) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

                GameManager.load();
                createBoard.dispose();
            }
        }

        int playerx = createBoard.player.playerPositionX;
        int playery = createBoard.player.playerPositionY;
        if (createBoard.player.isAlive) {
            if (createBoard.isPassed()) {
                try {
                    createBoard.date = Date.from(Instant.now());

                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {


                        if (createBoard.isOnline) {
                            goDown(playerx, playery);
                        } else {
                            moveOffline(playerx + 1, playery);
                        }

                    }
                    if (e.getKeyCode() == KeyEvent.VK_UP) {


                        if (createBoard.isOnline) {
                            goUp(playerx, playery);
                        } else {
                            moveOffline(playerx - 1, playery);
                        }
                    }


                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (createBoard.isOnline) {
                            goRight(playerx, playery);
                        } else {
                            moveOffline(playerx, playery + 1);
                        }
                    }


                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                        if (createBoard.isOnline) {
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
                if (createBoard.isOnline) {
                    try {
                        GameClient.client.send("#bomb$");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    createBoard.plantBomb();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_N) {
                if (!createBoard.isOnline) {
                    if (createBoard.player.bombControl && createBoard.player.isAlive) {
                        try {
                            createBoard.bombCells.get(0).bombThread.explode();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
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
        if (createBoard.isOnline) {
            sendNewPosition(playerX + 1, playerY);
        }
    }

    private void goUp(int playerX, int playerY) throws IOException {
        if (createBoard.isOnline) {
            sendNewPosition(playerX - 1, playerY);
        }
    }

    private void goRight(int playerX, int playerY) throws IOException {

        if (createBoard.isOnline) {
            sendNewPosition(playerX, playerY + 1);
        }
    }

    private void sendNewPosition(int playerNewPositionX, int playerNewPositionY) throws IOException {

        if (createBoard.player.isAlive) {
//            if (!createBoard.gameComponents[playerx][playery].type.equals("bomb")) {
//                createBoard.gameComponents[playerx][playery] = new FieldCell();
//            }
            // createBoard.player.bombSet = false;

            //  createBoard.player.currentBomb = null;
            GameClient.client.send("#playerX$" + playerNewPositionX);
            GameClient.client.send("#playerY$" + playerNewPositionY);

            //  createBoard.gameComponents[createBoard.player.playerPositionX][createBoard.player.playerPositionY] = createBoard.player;
            //  createBoard.createFrame();
        }
    }

    private void goLeft(int playerX, int playerY) throws IOException {
        if (createBoard.isOnline) {
            sendNewPosition(playerX, playerY - 1);
        }
    }

    private void moveOffline(int playerX, int playerY) {
        GameComponent cell = createBoard.gameComponents[playerX][playerY];
//        currentLocation(playerX,playerY);
//        nextLocation(playerX,playerY);
        if (cell.passable) {

            updateContentPane(createBoard.player.playerPositionX, createBoard.player.playerPositionY);


            createBoard.gameComponents[playerX][playerY] = createBoard.player;
            createBoard.player.playerPositionY = playerY;
            createBoard.player.playerPositionX = playerX;
        }
        if (cell instanceof StatChanger) {
            updateContentPane(createBoard.player.playerPositionX, createBoard.player.playerPositionY);

            createBoard.checkIfIsStatChanger(cell);
            createBoard.labels[playerX][playerY].setText("");
            createBoard.gameComponents[playerX][playerY] = createBoard.player;
            createBoard.player.playerPositionY = playerY;
            createBoard.player.playerPositionX = playerX;
        }

        if (cell.type.equals("door")) {
            if (createBoard.checkIfPassable()) {
                createBoard.goToNextLevel();
            }
        }


        createBoard.createFrame();


    }

    private void updateContentPane(int playerx, int playery) {

        if (createBoard.player.isAlive) {
            if (!createBoard.gameComponents[playerx][playery].type.equals("bomb")) {
                createBoard.gameComponents[playerx][playery] = new FieldCell();
            }
            createBoard.player.bombSet = false;

            createBoard.player.currentBomb = null;
        }
    }

    private void currentLocation(int i, int j) {
        if (!createBoard.gameComponents[i][j].neverPassable) {

            createBoard.player.disappearedObject = (Cell) createBoard.gameComponents[i][j];
            createBoard.player.isGhosting = true;
        }
    }

    private void nextLocation(int i, int j) {
        if (createBoard.player.isGhosting) {
            createBoard.player.isGhosting = false;
            createBoard.player.disappearedObject.passable = false;

            createBoard.setGameComponents(i, j, createBoard.player.disappearedObject);
            createBoard.gameComponents[i][j].passable = false;
            createBoard.player.disappearedObject = null;

        } else {
            createBoard.setGameComponents(i, j, new FieldCell());
        }
    }


}
