package model.game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Pane;
import model.game.elements.Bullet;
import model.game.elements.Enemy;
import model.game.elements.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class GameState {

    public static final int PLAYER_START_POSITION_X = 50;
    public static final int PLAYER_HEALTH = 5;
    public static final int PLAYER_MOVE_OFFSET_X = 10;
    private List endGameListeners = new ArrayList();

    private Pane gameBoard;

    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private GameStats statistics = new GameStats();

    private GameLevel level = GameLevel.LEVEL1;
    private SimpleStringProperty levelNameProperty = new SimpleStringProperty(level.toString());

    private PlayState playState;

    private int countIteration = 0;
    private List<Integer> positions = new ArrayList<>(Arrays.asList(25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575));

    /**
     * Generate new game state, ready to play.
     * Make player.
     * @param gameBoard Pane to draw game
     */
    public GameState(Pane gameBoard) {
        this.gameBoard = gameBoard;
        player = new Player(gameBoard.getPrefWidth() / 2, gameBoard.getPrefHeight() - PLAYER_START_POSITION_X, PLAYER_HEALTH);
        gameBoard.getChildren().add(player.getView());
        playState = PlayState.READY;
    }

    /**
     * Start game. Set state to Playing
     */
    public void startGame() {
        playState = PlayState.PLAYING;
    }

    /**
     * Rune one clock tick. Move enemies, bullets.
     * Check collisions, remove elements is necessary.
     * Level up. Check, is game over
     */
    public void clockTick() {
        if (countIteration % level.createEnemiesEveryNTicks == 0) {
            createNewEnemies();
        }

        if (countIteration % level.moveEnemiesEveryNTicks == 0)
            enemies.forEach(e -> e.moveEnemy(gameBoard.getHeight(), level.enemiesChangeX));

        bullets.forEach(e -> e.moveBullet(gameBoard.getHeight()));

        bullets.forEach(checkCollisionsWithEnemies());
        enemies.forEach(checkCollisionWithPlayer());

        removeBulletsAndEnemiesIfNeccecary();

        if (statistics.getPointsProperty().get() >= level.nextLevelOnPoints) {
            changeLevel(level.next());
        }

        countIteration += 1;
    }

    private void removeBulletsAndEnemiesIfNeccecary() {
        enemies.forEach(e -> {
            if (e.isToRemove()) gameBoard.getChildren().remove(e.getView());
        });
        bullets.forEach(b -> {
            if (b.isToRemove()) gameBoard.getChildren().remove(b.getView());
        });

        enemies.removeIf(e -> e.isToRemove());
        bullets.removeIf(e -> e.isToRemove());
    }

    private Consumer<Enemy> checkCollisionWithPlayer() {
        return enemy -> {
            if (player.isCollision(enemy)) {
                player.setHealthProperty(player.getHealthProperty().get() - 1);
                enemy.setToRemove(true);

                if (player.getHealthProperty().get() <= 0) {
                    playState = PlayState.ENDGAME;
                    fireEndGameEvent();
                }
            }
        };
    }

    private Consumer<Bullet> checkCollisionsWithEnemies() {
        return bullet -> enemies.forEach(enemy -> {
            if (bullet.isCollision(enemy)) {
                bullet.setToRemove(true);
                if (enemy.hitAndIsKilled()) {
                    statistics.addKilledEnemy(enemy.getType());
                }
            }
        });
    }

    /**
     * @param next New level
     */
    private void changeLevel(GameLevel next) {
        level = next;
        levelNameProperty.set(level.toString());
    }

    /**
     * Create new enemies on random X positions
     */
    private void createNewEnemies() {
        Collections.shuffle(positions);

        for (int i = 0; i < level.numberOfEnemiesToCreate; i++) {
            Enemy enemy = new Enemy(positions.get(i), Enemy.EnemyType.randomType());

            enemies.add(enemy);
            gameBoard.getChildren().add(enemy.getView());
        }
    }

    /**
     * Move player to left
     */
    public void movePlayerLeft() {
        player.changePosition(-PLAYER_MOVE_OFFSET_X, 0, gameBoard.getPrefWidth());
    }

    /**
     * Move player to right
     */
    public void movePlayerRight() {
        player.changePosition(PLAYER_MOVE_OFFSET_X, 0, gameBoard.getPrefWidth());
    }

    /**
     * Make shoot and add to game board
     */
    public void shootPlayer() {
        Bullet bull = player.shoot();
        bullets.add(bull);
        gameBoard.getChildren().add(bull.getView());
    }

    /**
     * Clear game, ready to restart.
     * Remove enemies, clear statistics.
     */
    public void clearState() {
        enemies.forEach(e -> gameBoard.getChildren().remove(e.getView()));
        bullets.forEach(b -> gameBoard.getChildren().remove(b.getView()));

        enemies.clear();
        bullets.clear();

        changeLevel(GameLevel.LEVEL1);
        countIteration = 0;
        statistics.clear();

        player.setHealthProperty(5);
        player.getView().setCenterX(gameBoard.getPrefWidth() / 2);
        player.getView().setCenterY(gameBoard.getPrefHeight() - PLAYER_START_POSITION_X);

        playState = PlayState.READY;
    }

    /**
     * @return GameStats object with statistics
     */
    public GameStats getStatistics() {
        return statistics;
    }

    public SimpleIntegerProperty getPlayerHealthProperty() {
        return player.getHealthProperty();
    }

    public GameLevel getLevel() {
        return level;
    }

    public SimpleStringProperty getLevelNameProperty() {
        return levelNameProperty;
    }

    public PlayState getPlayState() {
        return playState;
    }

    public synchronized void addEndGameListener(GameEndListener listener) {
        endGameListeners.add(listener);
    }

    public synchronized void removeEndGameListener(GameEndListener listener) {
        endGameListeners.remove(listener);
    }

    /**
     * Send event of GameEnd
     */
    private synchronized void fireEndGameEvent() {
        GameEndEvent event = new GameEndEvent(this);
        endGameListeners.forEach(e -> ((GameEndListener) e).endGameReceived(event));
    }
}
