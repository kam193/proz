package game;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.Player;

public class Controller {

    public Pane paneGame;
    private Player player;

    @FXML
    public void initialize(){
        player = new Player(300, 500);

        paneGame.getChildren().add(player.getView());
        paneGame.getParent().setOnKeyPressed(this::pressedKey);

    }

    public void pressedKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            player.changePosition(-10, 0, 600, 600);
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT) {
            player.changePosition(10, 0, 600, 600);
        }
    }
}
