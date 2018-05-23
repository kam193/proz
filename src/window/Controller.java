package window;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
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
import model.game.elements.Enemy;

public class Controller implements GameEndListener {

    public Label labelPoints;
    public GridPane gridStats;
    public Pane paneHealth;
    public GridPane gridForKilled;
    public Label labelLevel;
    private GameState gameState;

    public Pane paneGame;

    private Timeline gameTimeLine;

    @FXML
    public void initialize() {
        gameState = new GameState(paneGame);

        gameState.addEndGameListener(this);
        paneGame.getParent().setOnKeyPressed(this::pressedKey);

        gameState.startGame();

        labelPoints.textProperty().bind(gameState.getStatistics().getPointsProperty().asString());
        labelLevel.textProperty().bind(gameState.getLevelNameProperty());

        int coumnIndex = 0;
        for (Enemy.EnemyType enemyType : Enemy.EnemyType.values()){
            Circle enemyIcon = new Circle(15);
            enemyIcon.getStyleClass().add(enemyType.styleClassName);
            gridForKilled.addColumn(coumnIndex++, enemyIcon);
            Label countKilled = new Label();
            countKilled.textProperty().bind(Bindings.valueAt(gameState.getStatistics().getKilledEnemies(), enemyType.ordinal()).asString());
            gridForKilled.addColumn(coumnIndex++, countKilled);
        }

        paneHealth.prefWidthProperty().bind(gameState.getPlayerHealthProperty().multiply(32));

        gameTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> gameState.clockTick()));
        gameTimeLine.setCycleCount(Animation.INDEFINITE);
        gameTimeLine.play();
    }

    public void pressedKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            gameState.movePlayerLeft();
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            gameState.movePlayerRight();
        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            gameState.shootPlayer();
        } else if (keyEvent.getCode() == KeyCode.P) {
            System.out.println(gameState.getStatistics().getPointsProperty().get());
            for (Enemy.EnemyType enemyType : Enemy.EnemyType.values())
                System.out.println(String.format("%s: %d", enemyType.toString(), gameState.getStatistics().getKilledEnemy(enemyType)));
            System.out.println(String.format("Player healt: %d", gameState.getPlayerHealthProperty()));
            System.out.println(gameState.getLevel().toString());

        } else if (keyEvent.getCode() == KeyCode.R){
            gameState.clearState();
        } else if (keyEvent.getCode() == KeyCode.S){
            gameState.startGame();
            gameTimeLine.play();
        }
    }

    @Override
    public void endGameReceived(GameEndEvent event) {
        System.out.println("KONIEC GRY");
        gameTimeLine.stop();
    }
}
