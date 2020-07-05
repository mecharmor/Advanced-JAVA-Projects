package GameObject;
import Helpers.GameManager;

public interface Observable {

    void update();
    int screenX();
    int screenY();
    void publish();
    boolean GameOver();
    //void checkBorder();

    //void addObservableToGameManager();
}
