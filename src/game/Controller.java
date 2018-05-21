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
    private int countIteration = 0;

    @FXML
    public void initialize(){
        player = new Player(300, 500);

        paneGame.getChildren().add(player.getView());

        paneGame.getParent().setOnKeyPressed(this::pressedKey);

        List<Integer> positions = new ArrayList<>();
        Collections.addAll(positions, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575);

        gameTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            if (countIteration % 40 == 0){ // dodaj wrogow

                Collections.shuffle(positions);

                for (int i = 0; i < 5; i++) {
                    Enemy enemy = new Enemy(positions.get(i), Enemy.EnemyType.randomType());

                    enemies.add(enemy);
                    paneGame.getChildren().add(enemy.getView());
                }
            }

            if (countIteration % 5 == 0)
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

            countIteration += 1;
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
