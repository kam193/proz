package window;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.game.GameState;
import model.game.elements.Bullet;
import model.game.elements.Enemy;
import model.game.elements.Player;

import java.util.*;

public class Controller {

    private GameState gameState;

    public Pane paneGame;

    private Timeline gameTimeLine;

    @FXML
    public void initialize(){
        gameState = new GameState(paneGame);

        paneGame.getParent().setOnKeyPressed(this::pressedKey);

        gameState.startGame();

        gameTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> gameState.clockTick()));
        gameTimeLine.setCycleCount(Animation.INDEFINITE);
        gameTimeLine.play();
    }

    public void pressedKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            gameState.movePlayerLeft();
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT) {
            gameState.movePlayerRight();
        }
        else if (keyEvent.getCode() == KeyCode.SPACE){
            gameState.shootPlayer();
        }
    }
}
