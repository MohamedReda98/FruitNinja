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
public class Apple extends Fruit {

    //public data field
    ImageView imgFullApple = new ImageView("images/Red_Apple.png");

    //costractor with parameter from Main class
    public Apple(AnchorPane anchorPane) {
        //set the the layout of image
        imgFullApple.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9)); //this makes the fruit appear randomly
        imgFullApple.setY(50);
        imgFullApple.setFitWidth(50);
        imgFullApple.setFitHeight(50);
        imgFullApple.setVisible(false);

        anchorPane.getChildren().add(imgFullApple);
    }

    //this method to appear fruit
    @Override
    public void appearFruit(AnchorPane anchorPane, Label label) {
        Timeline applesAppear = new Timeline(new KeyFrame(Duration.millis(2000), e -> {

            imgFullApple.setX(600 * ThreadLocalRandom.current().nextDouble(0.09, 0.9));
            imgFullApple.setY(50);

            imgFullApple.setOnMouseEntered(e1 -> {
                // Get start x, y
                startX = e1.getX();
                startY = e1.getY();

            });

            imgFullApple.setOnMouseExited(e1 -> {
                // Get ends of x, y to draw trace
                endX = e1.getX();
                endY = e1.getY();

                // Cut the apple
                cutFruit(anchorPane);
                calculateScore(label);

                // Make slash trace
                createSlashTrace(anchorPane);

            });
            moveFruit();

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(5), delayVisible -> {
                imgFullApple.setVisible(true);
            }));

            delay.setCycleCount(1);
            delay.play();
        }));

        applesAppear.setCycleCount(Timeline.INDEFINITE);
        applesAppear.play();
    }
//this methode to make animation transate to fruit 

    @Override
    public void moveFruit() {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.9));
        translateTransition.setNode(imgFullApple);
        translateTransition.setFromY(400);
        translateTransition.setToY(50);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(2);
        translateTransition.play();
    }
//this method to cut fruit and apper the tow half image 

    @Override
    public void cutFruit(AnchorPane anchorPane) {

        ImageView imgHalfApple2 = new ImageView("images/apple_half1.png");
        ImageView imgHalfApple1 = new ImageView("images/apple_half2.png");

        imgHalfApple1.setRotate(-167.5);
        imgHalfApple2.setRotate(33.7);

        imgHalfApple1.setX(imgFullApple.getX() + 1);
        imgHalfApple1.setY(imgFullApple.getTranslateY() + 10);

        imgHalfApple2.setX(imgFullApple.getX() - 1);
        imgHalfApple2.setY(imgFullApple.getTranslateY() - 10);

        imgHalfApple1.setFitWidth(50);
        imgHalfApple1.setFitHeight(70);

        imgHalfApple2.setFitWidth(50);
        imgHalfApple2.setFitHeight(70);

        imgFullApple.setVisible(false);
        imgHalfApple1.setVisible(true);
        imgHalfApple2.setVisible(true);
//this time line to make the tow half image move down 
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(3), e -> {
            imgHalfApple1.setRotate(imgHalfApple1.getRotate() + 1);
            imgHalfApple2.setRotate(imgHalfApple2.getRotate() + 1);

            imgHalfApple1.setX(imgHalfApple1.getX() + .4);
            imgHalfApple1.setY(imgHalfApple1.getY() + 1);

            imgHalfApple2.setX(imgHalfApple2.getX() - .4);
            imgHalfApple2.setY(imgHalfApple2.getY() + 1);
        }));

        animation.setCycleCount(-1);
        animation.play();
        anchorPane.getChildren().addAll(imgHalfApple1, imgHalfApple2);

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

        // Tie the trace with our full apple layout x, y
        trace.setX(imgFullApple.getX());
        trace.setY(imgFullApple.getY());

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
