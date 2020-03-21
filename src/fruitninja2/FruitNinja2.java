/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruitninja2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author MOHAMMED
 */
public class FruitNinja2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();

        Label labelScore = new Label("Score:");

        labelScore.setTextFill(Color.GREEN);
        labelScore.setPrefSize(160, 32);
        labelScore.setAlignment(Pos.BASELINE_CENTER);
        labelScore.setFont(new Font("Cambria", 32));
        labelScore.setTranslateX(450);

        ImageView backGround = new ImageView("images/background.jpg"); //back ground image
        ImageView logo = new ImageView("images/logo.png");
        logo.setFitHeight(100);
        logo.setFitWidth(200);
        logo.setTranslateX(50);
        backGround.setFitHeight(410);
        backGround.setFitWidth(610);
        anchorPane.getChildren().addAll(backGround, logo, labelScore);

        Banana banana = new Banana(anchorPane); //constract object banana and pass anchorpane
        Orange orange = new Orange(anchorPane);
        Apple apple = new Apple(anchorPane);
        Pineapple pineapple = new Pineapple(anchorPane);
        Watermelon watermelon = new Watermelon(anchorPane);
        Bomb bomb = new Bomb(anchorPane);

        banana.appearFruit(anchorPane, labelScore);
        watermelon.appearFruit(anchorPane, labelScore);
        orange.appearFruit(anchorPane, labelScore);
        pineapple.appearFruit(anchorPane, labelScore);
        apple.appearFruit(anchorPane, labelScore);
        bomb.appearFruit(anchorPane, labelScore);

        Scene scene = new Scene(anchorPane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/images/stage_icon.png")); //icon for the stage
        primaryStage.setTitle("Fruit Ninja!!");
        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }

}
