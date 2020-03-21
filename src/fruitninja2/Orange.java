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
public class Orange extends Fruit {

    //public data field

    ImageView imgFullOrange = new ImageView("images/Orange.png");

    //costractor with parameter from Main class
    public Orange(AnchorPane anchorPane) {
        //set the the layout of image
        imgFullOrange.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9)); //this makes the fruit appear randomly
        imgFullOrange.setY(50);
        imgFullOrange.setFitWidth(50);
        imgFullOrange.setFitHeight(50);
        imgFullOrange.setVisible(false);

        anchorPane.getChildren().add(imgFullOrange);
    }

    //this method to appear fruit
    @Override
    public void appearFruit(AnchorPane anchorPane, Label label) {
        Timeline orangesAppear = new Timeline(new KeyFrame(Duration.millis(1900), e -> {

            imgFullOrange.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9));
            imgFullOrange.setY(50);

            imgFullOrange.setOnMouseEntered(e1 -> {
                // Get start x, y
                startX = e1.getX();
                startY = e1.getY();

            });

            imgFullOrange.setOnMouseExited(e1 -> {
                // Get ends of x, y to draw trace
                endX = e1.getX();
                endY = e1.getY();

                // Cut the orange
                cutFruit(anchorPane);
                calculateScore(label);

                // Make slash trace
                createSlashTrace(anchorPane);

            });
            moveFruit();

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible -> {
                imgFullOrange.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        orangesAppear.setCycleCount(Timeline.INDEFINITE);
        orangesAppear.play();
    }
//this methode to make animation transate to fruit 

    @Override
    public void moveFruit() {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.9));
        translateTransition.setNode(imgFullOrange);
        translateTransition.setFromY(400);
        translateTransition.setToY(50);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(2);
        translateTransition.play();
    }
//this method to cut fruit and apper the tow half image 

    @Override
    public void cutFruit(AnchorPane anchorPane) {

        ImageView imgHalfOrange2 = new ImageView("images/orangehalf.png");
        ImageView imgHalfOrange1 = new ImageView("images/orangehalf.png");

        imgHalfOrange1.setRotate(-167.5);
        imgHalfOrange2.setRotate(33.7);

        imgHalfOrange1.setX(imgFullOrange.getX() + 1);
        imgHalfOrange1.setY(imgFullOrange.getTranslateY() + 10);

        imgHalfOrange2.setX(imgFullOrange.getX() - 1);
        imgHalfOrange2.setY(imgFullOrange.getTranslateY() - 10);

        imgHalfOrange1.setFitWidth(50);
        imgHalfOrange1.setFitHeight(50);

        imgHalfOrange2.setFitWidth(50);
        imgHalfOrange2.setFitHeight(50);

        imgFullOrange.setVisible(false);
        imgHalfOrange1.setVisible(true);
        imgHalfOrange2.setVisible(true);
//this time line to make the tow half image move down 
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(3), e -> {
            imgHalfOrange1.setRotate(imgHalfOrange1.getRotate() + 1);
            imgHalfOrange2.setRotate(imgHalfOrange2.getRotate() + 1);

            imgHalfOrange1.setX(imgHalfOrange1.getX() + .4);
            imgHalfOrange1.setY(imgHalfOrange1.getY() + 1);

            imgHalfOrange2.setX(imgHalfOrange2.getX() - .4);
            imgHalfOrange2.setY(imgHalfOrange2.getY() + 1);
        }));

        animation.setCycleCount(-1);
        animation.play();
        anchorPane.getChildren().addAll(imgHalfOrange1, imgHalfOrange2);

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

        // Tie the trace with our full orange layout x, y
        trace.setX(imgFullOrange.getX());
        trace.setY(imgFullOrange.getY());

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
