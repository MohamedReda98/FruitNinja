/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruitninja2;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.FadeTransition;
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
public class Bomb extends Fruit {

    ImageView imgFullBomb = new ImageView("images/Bomb.gif");

    //costractor with parameter from Main class
    public Bomb(AnchorPane anchorPane) {
        //set the the layout of image
        imgFullBomb.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9)); //this makes the fruit appear randomly
        imgFullBomb.setY(50);
        imgFullBomb.setFitWidth(80);
        imgFullBomb.setFitHeight(80);
        imgFullBomb.setVisible(false);

        anchorPane.getChildren().add(imgFullBomb);
    }

    //this method to appear fruit
    @Override
    public void appearFruit(AnchorPane anchorPane, Label label) {
        Timeline bombsAppear = new Timeline(new KeyFrame(Duration.millis(1850), e -> {

            imgFullBomb.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9));
            imgFullBomb.setY(50);

            imgFullBomb.setOnMouseEntered(e1 -> {
                // Get start x, y
                startX = e1.getX();
                startY = e1.getY();

            });

            imgFullBomb.setOnMouseExited(e1 -> {
                // Get ends of x, y to draw trace
                endX = e1.getX();
                endY = e1.getY();

                createFlashe(anchorPane);
                calculateScore(label);

            });
            moveFruit();

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible -> {
                imgFullBomb.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        bombsAppear.setCycleCount(Timeline.INDEFINITE);
        bombsAppear.play();
    }

    //this methode to make animation transate to fruit 
    @Override
    public void moveFruit() {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.9));
        translateTransition.setNode(imgFullBomb);
        translateTransition.setFromY(400);
        translateTransition.setToY(50);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(2);
        translateTransition.play();
    }

    public void createFlashe(AnchorPane anchorPane) {

        //Special effect in case of explosion
        FadeTransition ft = new FadeTransition(Duration.millis(300), anchorPane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();

    }

    //this metod to calculate score
    @Override
    public void calculateScore(Label label) {

        score -= 1;

        label.setText("Score:" + score);
        if (score <= 0) {
            label.setText("Score:0");
        }

    }

}
