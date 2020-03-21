/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruitninja2;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author MOHAMMED
 */
public class Watermelon extends Fruit {

    //public data field
    ImageView imgFullWatermelon = new ImageView("images/Watermelon.png");

    //costractor with parameter from Main class
    public Watermelon(AnchorPane anchorPane) {
        //set the the layout of image
        imgFullWatermelon.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9)); //this makes the fruit appear randomly
        imgFullWatermelon.setY(30);
        imgFullWatermelon.setFitWidth(80);
        imgFullWatermelon.setFitHeight(80);
        imgFullWatermelon.setVisible(false);

        anchorPane.getChildren().add(imgFullWatermelon);
    }

    //this method to appear fruit
    @Override
    public void appearFruit(AnchorPane anchorPane, Label label) {
        Timeline watermelonsAppear = new Timeline(new KeyFrame(Duration.millis(1900), e -> {

            imgFullWatermelon.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9));
            imgFullWatermelon.setY(30);

            imgFullWatermelon.setOnMouseEntered(e1 -> {
                // Get start x, y
                startX = e1.getX();
                startY = e1.getY();

            });

            imgFullWatermelon.setOnMouseExited(e1 -> {
                // Get ends of x, y to draw trace
                endX = e1.getX();
                endY = e1.getY();

                // Cut the watermelon
                cutFruit(anchorPane);
                calculateScore(label);

                // Make slash trace
                createSlashTrace(anchorPane);

            });
            moveFruit();

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible -> {
                imgFullWatermelon.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        watermelonsAppear.setCycleCount(Timeline.INDEFINITE);
        watermelonsAppear.play();
    }
//this methode to make animation transate to fruit 

    @Override
    public void moveFruit() {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.9));
        translateTransition.setNode(imgFullWatermelon);
        translateTransition.setFromY(400);
        translateTransition.setToY(30);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(2);
        translateTransition.play();
    }
//this method to cut fruit and apper the tow half image 

    @Override
    public void cutFruit(AnchorPane anchorPane) {

        ImageView imgHalfWatermelon2 = new ImageView("images/halfwatermelon.png");
        ImageView imgHalfWatermelon1 = new ImageView("images/halfwatermelon.png");

        imgHalfWatermelon1.setRotate(-167.5);
        imgHalfWatermelon2.setRotate(33.7);

        imgHalfWatermelon1.setX(imgFullWatermelon.getX() + 1);
        imgHalfWatermelon1.setY(imgFullWatermelon.getTranslateY() + 10);

        imgHalfWatermelon2.setX(imgFullWatermelon.getX() - 1);
        imgHalfWatermelon2.setY(imgFullWatermelon.getTranslateY() - 10);

        imgHalfWatermelon1.setFitWidth(80);
        imgHalfWatermelon1.setFitHeight(80);

        imgHalfWatermelon2.setFitWidth(80);
        imgHalfWatermelon2.setFitHeight(80);

        imgFullWatermelon.setVisible(false);
        imgHalfWatermelon1.setVisible(true);
        imgHalfWatermelon2.setVisible(true);
//this time line to make the tow half image move down 
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(3), e -> {
            imgHalfWatermelon1.setRotate(imgHalfWatermelon1.getRotate() + 1);
            imgHalfWatermelon2.setRotate(imgHalfWatermelon2.getRotate() + 1);

            imgHalfWatermelon1.setX(imgHalfWatermelon1.getX() + .4);
            imgHalfWatermelon1.setY(imgHalfWatermelon1.getY() + 1);

            imgHalfWatermelon2.setX(imgHalfWatermelon2.getX() - .4);
            imgHalfWatermelon2.setY(imgHalfWatermelon2.getY() + 1);
        }));

        animation.setCycleCount(-1);
        animation.play();
        anchorPane.getChildren().addAll(imgHalfWatermelon1, imgHalfWatermelon2);

    }
//this mehod to appear trace 

    @Override
    public void createSlashTrace(AnchorPane anchorPane) {
        // Get slash slope
        double slope = (endY - startY) / (endX - startX);

        // Get angle of incline with +ve X axis
        double angle = Math.toDegrees(Math.atan(slope));

        // Define the slash image and create the object for it
        ImageView trace = new ImageView("images/slash_trace.gif");

        // Set rotate angle we calculated, 45 hard-coded value
        trace.setRotate(45 - angle);

        // Firstly make the slash invisible
        trace.setVisible(false);

        // Tie the trace with our full watermelon layout x, y
        trace.setX(imgFullWatermelon.getX());
        trace.setY(imgFullWatermelon.getY());

        // Add the trace to the pane
        anchorPane.getChildren().add(trace);

        // Make animation to show the trace just for 300 millisecond
        Timeline showSlash = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    trace.setVisible(true);
                }
                ),
                new KeyFrame(Duration.millis(300), e -> {
                    trace.setVisible(false);
                }
                )
        );

        showSlash.setCycleCount(1);
        showSlash.play();

    }

}
