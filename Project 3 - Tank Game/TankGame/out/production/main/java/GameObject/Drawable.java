package GameObject;
import java.awt.*;
import Helpers.GameManager;

public interface Drawable {

    void drawImage(Graphics g);
    boolean isDrawable();

    //void addDrawableToGameManager();
}
