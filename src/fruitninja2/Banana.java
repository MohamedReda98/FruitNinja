package fruitninja2;

import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Banana extends Fruit {

    ImageView imgFullBanana = new ImageView("images/Banana.png");

    //costractor with parameter from Main class
    public Banana(AnchorPane anchorPane) {
        //set the the layout of image
        imgFullBanana.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9)); //this makes the fruit appear randomly
        imgFullBanana.setY(50);
        imgFullBanana.setFitWidth(50);
        imgFullBanana.setFitHeight(80);
        imgFullBanana.setVisible(false);

        anchorPane.getChildren().add(imgFullBanana);
    }

    //this method to appear fruit
    @Override
    public void appearFruit(AnchorPane anchorPane, Label label) {
        Timeline bananasAppear = new Timeline(new KeyFrame(Duration.millis(1800), e -> {

            imgFullBanana.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9));
            imgFullBanana.setY(50);

            imgFullBanana.setOnMouseEntered(e1 -> {
                // Get start x, y
                startX = e1.getX();
                startY = e1.getY();

            });

            imgFullBanana.setOnMouseExited(e1 -> {
                // Get ends of x, y to draw trace
                endX = e1.getX();
                endY = e1.getY();

                // Cut the banana
                cutFruit(anchorPane);
                calculateScore(label);

                // Make slash trace
                createSlashTrace(anchorPane);

            });
            moveFruit();

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible -> {
                imgFullBanana.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        bananasAppear.setCycleCount(Timeline.INDEFINITE);
        bananasAppear.play();
    }

     //this methode to make animation transate to fruit 
    @Override
    public void moveFruit() {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.9));
        translateTransition.setNode(imgFullBanana);
        translateTransition.setFromY(400);
        translateTransition.setToY(50);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(2);
        translateTransition.play();
    }

//this method to cut fruit and apper the tow half image 
    @Override
    public void cutFruit(AnchorPane anchorPane) {

        ImageView imgHalfBanana2 = new ImageView("images/halfbanana.png");
        ImageView imgHalfBanana1 = new ImageView("images/halfbanana.png");

        imgHalfBanana1.setRotate(-167.5);
        imgHalfBanana2.setRotate(33.7);

        imgHalfBanana1.setX(imgFullBanana.getX() + 1);
        imgHalfBanana1.setY(imgFullBanana.getTranslateY() + 10);

        imgHalfBanana2.setX(imgFullBanana.getX() - 1);
        imgHalfBanana2.setY(imgFullBanana.getTranslateY() - 10);

        imgHalfBanana1.setFitWidth(50);
        imgHalfBanana1.setFitHeight(70);

        imgHalfBanana2.setFitWidth(50);
        imgHalfBanana2.setFitHeight(70);

        imgFullBanana.setVisible(false);
        imgHalfBanana1.setVisible(true);
        imgHalfBanana2.setVisible(true);
//this time line to make the tow half image move down 
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(3), e -> {
            imgHalfBanana1.setRotate(imgHalfBanana1.getRotate() + 1);
            imgHalfBanana2.setRotate(imgHalfBanana2.getRotate() + 1);

            imgHalfBanana1.setX(imgHalfBanana1.getX() + .4);
            imgHalfBanana1.setY(imgHalfBanana1.getY() + 1);

            imgHalfBanana2.setX(imgHalfBanana2.getX() - .4);
            imgHalfBanana2.setY(imgHalfBanana2.getY() + 1);
        }));

        animation.setCycleCount(-1);
        animation.play();
        anchorPane.getChildren().addAll(imgHalfBanana1, imgHalfBanana2);

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

        // Tie the trace with our full banana layout x, y
        trace.setX(imgFullBanana.getX());
        trace.setY(imgFullBanana.getY());

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
