package dpp.dpp;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

    private int num;
    ReentrantLock fork = new ReentrantLock();


    private ImageView chopstickView;
    private TextArea loggingConsole;

    public Chopstick(int id, TextArea loggingConsole) {
        this.num = id;
        this.loggingConsole = loggingConsole;
    }

    public void setChopstickView(ImageView chopstickView) {
        this.chopstickView = chopstickView;
    }



    boolean Pickup() {
        if (fork.tryLock()) {

            Platform.runLater(()-> {
                chopstickView.setVisible(false);

            });
            return true;
        }
        return false;
    }
    void Putdown() {
        fork.unlock();
        Platform.runLater(() -> {
            chopstickView.setVisible(true);
        });
    }

}
