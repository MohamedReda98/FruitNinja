package fruitninja2;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author MOHAMMED
 */
public class Fruit {

    //public data field
    public static int score;

    double startX, startY, endX, endY, theValueRanadom;

    public void appearFruit(AnchorPane anchorPane, Label label) {
    }

    public void moveFruit() {
    }

    public void cutFruit(AnchorPane anchorPane) {
    }

    public void createSlashTrace(AnchorPane anchorPane) {
    }

    //this metod to calculate score
    public void calculateScore(Label label) {
        
        score += 1;

        label.setText("Score:" + score);
        if (score <= 0) {
            label.setText("Score:0");
        }

    }

}
