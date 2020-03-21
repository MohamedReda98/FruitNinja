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
public class Pineapple extends Fruit {

    //public data field

    ImageView imgFullPineapple = new ImageView("images/Pineapple.png");

    //costractor with parameter from Main class
    public Pineapple(AnchorPane anchorPane) {
        //set the the layout of image
        imgFullPineapple.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9)); //this makes the fruit appear randomly
        imgFullPineapple.setY(40);
        imgFullPineapple.setFitWidth(70);
        imgFullPineapple.setFitHeight(80);
        imgFullPineapple.setVisible(false);

        anchorPane.getChildren().add(imgFullPineapple);
    }

    //this method to appear fruit
    @Override
    public void appearFruit(AnchorPane anchorPane, Label label) {
        Timeline pineapplesAppear = new Timeline(new KeyFrame(Duration.millis(1800), e -> {

            imgFullPineapple.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9));
            imgFullPineapple.setY(40);

            imgFullPineapple.setOnMouseEntered(e1 -> {
                // Get start x, y
                startX = e1.getX();
                startY = e1.getY();

            });

            imgFullPineapple.setOnMouseExited(e1 -> {
                // Get ends of x, y to draw trace
                endX = e1.getX();
                endY = e1.getY();

                // Cut the pineapple
                cutFruit(anchorPane);
                calculateScore(label);

                // Make slash trace
                createSlashTrace(anchorPane);

            });
            moveFruit();

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible -> {
                imgFullPineapple.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        pineapplesAppear.setCycleCount(Timeline.INDEFINITE);
        pineapplesAppear.play();
    }
//this methode to make animation transate to fruit 

    @Override
    public void moveFruit() {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.9));
        translateTransition.setNode(imgFullPineapple);
        translateTransition.setFromY(400);
        translateTransition.setToY(40);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(2);
        translateTransition.play();
    }
//this method to cut fruit and apper the tow half image 

    @Override
    public void cutFruit(AnchorPane anchorPane) {

        ImageView imgHalfPineapple2 = new ImageView("images/PineappleHalf2.png");
        ImageView imgHalfPineapple1 = new ImageView("images/PineappleHalf1.png");

        imgHalfPineapple1.setRotate(-167.5);
        imgHalfPineapple2.setRotate(33.7);

        imgHalfPineapple1.setX(imgFullPineapple.getX() + 1);
        imgHalfPineapple1.setY(imgFullPineapple.getTranslateY() + 10);

        imgHalfPineapple2.setX(imgFullPineapple.getX() - 1);
        imgHalfPineapple2.setY(imgFullPineapple.getTranslateY() - 10);

        imgHalfPineapple1.setFitWidth(70);
        imgHalfPineapple1.setFitHeight(80);

        imgHalfPineapple2.setFitWidth(70);
        imgHalfPineapple2.setFitHeight(80);

        imgFullPineapple.setVisible(false);
        imgHalfPineapple1.setVisible(true);
        imgHalfPineapple2.setVisible(true);
//this time line to make the tow half image move down 
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(3), e -> {
            imgHalfPineapple1.setRotate(imgHalfPineapple1.getRotate() + 1);
            imgHalfPineapple2.setRotate(imgHalfPineapple2.getRotate() + 1);

            imgHalfPineapple1.setX(imgHalfPineapple1.getX() + .4);
            imgHalfPineapple1.setY(imgHalfPineapple1.getY() + 1);

            imgHalfPineapple2.setX(imgHalfPineapple2.getX() - .4);
            imgHalfPineapple2.setY(imgHalfPineapple2.getY() + 1);
        }));

        animation.setCycleCount(-1);
        animation.play();
        anchorPane.getChildren().addAll(imgHalfPineapple1, imgHalfPineapple2);

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

        // Tie the trace with our full pineapple layout x, y
        trace.setX(imgFullPineapple.getX());
        trace.setY(imgFullPineapple.getY());

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
