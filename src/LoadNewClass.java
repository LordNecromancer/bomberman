import com.sun.org.apache.bcel.internal.util.ClassLoader;

import javax.swing.*;

/**
 * Created by Sun on 09/05/2018.
 */
class LoadNewClass {
    Class cls;
    ClassLoader classLoader;
    GameBoardCreator gameBoardCreator;

    LoadNewClass(Class cls, GameBoardCreator gameBoardCreator) {
        this.cls = cls;
        this.gameBoardCreator = gameBoardCreator;
        classLoader = new ClassLoader();
        try {
            classLoader.loadClass(cls.getName());
            extractFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void extractFields() {

        try {
            String type = (String) cls.getDeclaredField("type").get(cls.newInstance());
            ImageIcon imageIcon = (ImageIcon) cls.getDeclaredField("image").get(cls.newInstance());
            gameBoardCreator.getMainFrameGraphics().getDisplayMap().put(type, imageIcon);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
