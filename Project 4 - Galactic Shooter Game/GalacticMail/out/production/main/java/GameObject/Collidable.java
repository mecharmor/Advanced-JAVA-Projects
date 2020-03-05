package GameObject;

import java.awt.*;

public interface Collidable {

    //void addCollidableToGameManager(GameManager);
    void handleCollision(Collidable obj);
    boolean isCollidable();
    //boolean intersects(Rectangle rect);
    Rectangle getBounds();

    //boolean isDamaging();


}
