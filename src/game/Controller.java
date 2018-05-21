package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Bullet;
import model.Enemy;
import model.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    public Pane paneGame;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();

    private Timeline gameTimeLine;

    @FXML
    public void initialize(){
        player = new Player(300, 500);

        paneGame.getChildren().add(player.getView());

        enemies.add(new Enemy(250, Enemy.EnemyType.ICECREAM));
        enemies.add(new Enemy(300, Enemy.EnemyType.CAKE));
        enemies.add(new Enemy(350, Enemy.EnemyType.ICECREAM));

        paneGame.getChildren().addAll(enemies.stream().map(e -> e.getView()).collect(Collectors.toList()));
        paneGame.getParent().setOnKeyPressed(this::pressedKey);

        gameTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            enemies.forEach(e -> e.moveEnemy());
            bullets.forEach(e -> e.moveBullet());

            bullets.forEach(bullet -> enemies.forEach(enemy -> {
                if (bullet.isCollision(enemy)) {
                    bullet.setToRemove(true);
                    enemy.setToRemove(true);

                    paneGame.getChildren().removeAll(bullet.getView(), enemy.getView());
                }}));

            enemies.removeIf(e -> e.isToRemove());
            bullets.removeIf(e -> e.isToRemove());
        }));
        gameTimeLine.setCycleCount(Animation.INDEFINITE);
        gameTimeLine.play();
    }

    public void pressedKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            player.changePosition(-10, 0, 600);
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT) {
            player.changePosition(10, 0, 600);
        }
        else if (keyEvent.getCode() == KeyCode.SPACE){
            Bullet bull = player.shoot();
            bullets.add(bull);
            paneGame.getChildren().add(bull.getView());
        }
    }

    public void shutdown(){

    }
}
