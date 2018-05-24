package window;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.game.GameEndEvent;
import model.game.GameEndListener;
import model.game.GameState;
import model.game.PlayState;
import model.game.elements.Enemy;

public class Controller implements GameEndListener {

    public Label labelPoints;
    public GridPane gridStats;
    public Pane paneHealth;
    public GridPane gridForKilled;
    public Label labelLevel;
    public DialogPane welcomeDialog;
    public DialogPane gameOverDialog;
    private GameState gameState;

    public Pane paneGame;

    private Timeline gameTimeLine;
    private Timeline keysTimeLine;

    private boolean isShootKeyTyped = false;
    private boolean isMoveKeyTyped = false;
    private boolean isMoveToLeft = false;

    @FXML
    public void initialize() {
        gameState = new GameState(paneGame);

        gameState.addEndGameListener(this);
        paneGame.getParent().setOnKeyPressed(this::pressedKey);
        paneGame.getParent().setOnKeyReleased(this::releasedKey);

        labelPoints.textProperty().bind(gameState.getStatistics().getPointsProperty().asString());
        labelLevel.textProperty().bind(gameState.getLevelNameProperty());

        int columnIndex = 0;
        for (Enemy.EnemyType enemyType : Enemy.EnemyType.values()) {
            Circle enemyIcon = new Circle(15);
            enemyIcon.getStyleClass().add(enemyType.styleClassName);
            gridForKilled.addColumn(columnIndex++, enemyIcon);
            Label countKilled = new Label();
            countKilled.textProperty().bind(Bindings.valueAt(gameState.getStatistics().getKilledEnemies(), enemyType.ordinal()).asString());
            gridForKilled.addColumn(columnIndex++, countKilled);
        }

        paneHealth.prefWidthProperty().bind(gameState.getPlayerHealthProperty().multiply(32));

        gameTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> gameState.clockTick()));
        gameTimeLine.setCycleCount(Animation.INDEFINITE);

        keysTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> makeMoveAndShoot()));
        keysTimeLine.setCycleCount(Animation.INDEFINITE);
    }

    private void makeMoveAndShoot() {
        if (isShootKeyTyped) {
            gameState.shootPlayer();
        }

        if (isMoveKeyTyped && isMoveToLeft) {
            gameState.movePlayerLeft();
        }

        if (isMoveKeyTyped && !isMoveToLeft) {
            gameState.movePlayerRight();
        }
    }

    private void releasedKey(KeyEvent keyEvent) {
        if (gameState.getPlayState() == PlayState.PLAYING) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isShootKeyTyped = false;
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                isMoveKeyTyped = false;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isMoveKeyTyped = false;
            }
        }
    }

    public void pressedKey(KeyEvent keyEvent) {
        if (gameState.getPlayState() == PlayState.PLAYING) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isShootKeyTyped = true;
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                isMoveToLeft = true;
                isMoveKeyTyped = true;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isMoveToLeft = false;
                isMoveKeyTyped = true;
            }
        } else if (gameState.getPlayState() == PlayState.ENDGAME) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                gameOverDialog.setVisible(false);
                gameState.clearState();
                playGame();
            }
        } else if (gameState.getPlayState() == PlayState.READY) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                welcomeDialog.setVisible(false);
                playGame();
            }
        }
    }

    private void playGame() {
        gameState.startGame();
        gameTimeLine.play();
        keysTimeLine.play();
    }

    @Override
    public void endGameReceived(GameEndEvent event) {
        keysTimeLine.stop();
        gameTimeLine.stop();
        gameOverDialog.setVisible(true);
        gameOverDialog.toFront();

        isShootKeyTyped = false;
        isMoveKeyTyped = false;
    }
}
