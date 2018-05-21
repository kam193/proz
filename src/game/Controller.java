package game;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.Enemy;
import model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    public Pane paneGame;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();

    @FXML
    public void initialize(){
        player = new Player(300, 500);

        paneGame.getChildren().add(player.getView());

        enemies.add(new Enemy(250, Enemy.EnemyType.ICECREAM));
        enemies.add(new Enemy(300, Enemy.EnemyType.CAKE));
        enemies.add(new Enemy(350, Enemy.EnemyType.ICECREAM));

        paneGame.getChildren().addAll(enemies.stream().map(e -> e.getView()).collect(Collectors.toList()));
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
