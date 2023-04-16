package dpp.dpp;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by fab on 4/16/2016
 */
public class Philosopher implements Runnable {
    /*static Semaphore room = new Semaphore(4);*/

    private Chopstick leftChopstick;
    private Chopstick rightChopstick;
    private int id;
    private String name;

    String state;

    private ImageView headView;
    private Image thinkingImg;
    private Image hungryImg;
    private Image eatingImg;

    private TextArea loggingConsole;

    /*private Timer timer;*/


    private boolean consecHungry = false;

    public Philosopher(Chopstick leftChopstick, Chopstick rightChopstick, int id, String name, ImageView headView,
                       Image thinkingImg, Image hungryImg, Image eatingImg, TextArea loggingConsole) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.id = id;
        this.name = name;
        this.headView = headView;
        this.thinkingImg = thinkingImg;
        this.hungryImg = hungryImg;
        this.eatingImg = eatingImg;
        this.loggingConsole = loggingConsole;

//        state = "think";



    }

    void Start(){
        state = "hungry";
        if(leftChopstick.Pickup()){
            if(rightChopstick.Pickup()){
                eat();

                {think();
                    rightChopstick.Putdown();
                    leftChopstick.Putdown();
                }
            }
            else{
                leftChopstick.Putdown();
            }
        }

    }

    @Override
    public void run() {
        String oldState = "state";
        think();
        while (Controller.RUNNING) {

            if( !oldState.equals(state) ){
                hungry();
                oldState = state;
            }
            Start();

        }
        Platform.runLater(() -> loggingConsole.appendText(name + " stopped \n"));
        System.out.println(name + " stopped");
    }

    private void think() {
        System.out.println(name + " is thinking...");
        Platform.runLater(() -> {
            loggingConsole.appendText(name + " is thinking... \n");
            headView.setImage(thinkingImg);
        });
        state = "hungry";
    }




    private void hungry() {


        try {
            Thread.sleep((long) Math.round((Math.random()+2) * 1000));
            System.out.println(name + " is hungry...");
            Platform.runLater(() -> {
                loggingConsole.appendText(name + " is hungry... \n");
                headView.setImage(hungryImg);
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }

        state="eat";
    }



    private void eat() {
        try {
            System.out.println(name + " is eating...");
            Platform.runLater(() -> {
                loggingConsole.appendText(name + " is eating... \n");
                headView.setImage(eatingImg);
            });
            Thread.sleep((long) Math.round((Math.random()+2) * 1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }

        state="think";
    }

    @Override
    public String toString() {
        return "Philosopher_" + id + "_" + name;
    }

}
