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

    private boolean isShootKeyTyped = false;
    private boolean isMoveToLeft = false;
    private boolean isMoveToRight = false;
    private boolean isLastPressedLeft = false;

    /**
     * Set up view's bindings and key events. Prepare timers.
     */
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

        gameTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            gameState.clockTick();
            makeMoveAndShoot();
        }));
        gameTimeLine.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * On every timer tick, check is time to move or shoot
     */
    private void makeMoveAndShoot() {
        if (isShootKeyTyped) {
            gameState.shootPlayer();
        }

        if (isMoveToLeft && isLastPressedLeft) {
            gameState.movePlayerLeft();
        }

        if (isMoveToRight && !isLastPressedLeft) {
            gameState.movePlayerRight();
        }
    }

    /**
     * On release key, disable moving or shooting
     *
     * @param keyEvent
     */
    private void releasedKey(KeyEvent keyEvent) {
        if (gameState.getPlayState() == PlayState.PLAYING) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isShootKeyTyped = false;
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                isMoveToLeft = false;
                isLastPressedLeft = false;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isMoveToRight = false;
                isLastPressedLeft = true;
            }
        }
    }

    /**
     * On press key:
     * when game is play - activate moving or shooting,
     * when app is start - play game,
     * when game is over - restart game.
     *
     * @param keyEvent event object
     */
    public void pressedKey(KeyEvent keyEvent) {
        if (gameState.getPlayState() == PlayState.PLAYING) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isShootKeyTyped = true;
            } else if (keyEvent.getCode() == KeyCode.LEFT) {
                isMoveToLeft = true;
                isLastPressedLeft = true;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isMoveToRight = true;
                isLastPressedLeft = false;
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

    /**
     * Start game, start game and keys timers.
     */
    private void playGame() {
        gameState.startGame();
        gameTimeLine.play();
    }

    /**
     * On game end, stop timers and show dialog.
     *
     * @param event event object
     */
    @Override
    public void endGameReceived(GameEndEvent event) {
        gameTimeLine.stop();
        gameOverDialog.setVisible(true);
        gameOverDialog.toFront();

        isShootKeyTyped = false;
        isMoveToRight = false;
        isMoveToLeft = false;
    }
}
