package model.game;

import javafx.scene.layout.Pane;
import model.game.elements.Bullet;
import model.game.elements.Enemy;
import model.game.elements.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameState {

    private Pane gameBoard;

    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private GameStats statistics = new GameStats();

    private GameLevel level = GameLevel.LEVEL1;

    private int countIteration = 0;
    private List<Integer> positions = new ArrayList<>(Arrays.asList(25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575));

    public GameState(Pane gameBoard){
        this.gameBoard = gameBoard;
    }

    public void startGame(){
        player = new Player(300, 500, 5);
        gameBoard.getChildren().add(player.getView());
    }

    public void clockTick(){
        if (countIteration % level.createEnemiesEveryNTicks == 0){ // dodaj wrogow
            Collections.shuffle(positions);

            for (int i = 0; i < level.numberOfEnemiesToCreate; i++) {
                Enemy enemy = new Enemy(positions.get(i), Enemy.EnemyType.randomType());

                enemies.add(enemy);
                gameBoard.getChildren().add(enemy.getView());
            }
        }

        if (countIteration % level.moveEnemiesEveryNTicks == 0)
            enemies.forEach(e -> e.moveEnemy(gameBoard.getHeight()));

        bullets.forEach(e -> e.moveBullet(gameBoard.getHeight()));

        bullets.forEach(bullet -> enemies.forEach(enemy -> {
            if (bullet.isCollision(enemy)) {
                bullet.setToRemove(true);
                if (enemy.hitAndIsKilled()){
                    statistics.addKilledEnemy(enemy.getType());
                }
            }}));

        enemies.forEach(enemy -> {
            if (player.isCollision(enemy)){
                player.setHealth(player.getHealth()-1);
                enemy.setToRemove(true);
            }
        });

        enemies.forEach(e -> {if (e.isToRemove()) gameBoard.getChildren().remove(e.getView());});
        bullets.forEach(b -> {if (b.isToRemove()) gameBoard.getChildren().remove(b.getView());});

        enemies.removeIf(e -> e.isToRemove());
        bullets.removeIf(e -> e.isToRemove());

        if (statistics.getPointsProperty().get() >= level.nextLevelOnPoints)
            level = level.next();

        countIteration += 1;
    }

    public void movePlayerLeft(){
        player.changePosition(-10, 0, 600);
    }

    public void movePlayerRight(){
        player.changePosition(10, 0, 600);
    }

    public void shootPlayer(){
        Bullet bull = player.shoot();
        bullets.add(bull);
        gameBoard.getChildren().add(bull.getView());
    }

    public GameStats getStatistics(){
        return statistics;
    }

    public int getPlayerHealth(){
        return player.getHealth();
    }

    public GameLevel getLevel(){
        return level;
    }
}
