package model.game;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void playerIsSetOnBoardOnCreate(){
        Pane board = new Pane();
        board.setPrefSize(600, 600);

        GameState game = new GameState(board);

        assertEquals(1, board.getChildren().size());
    }

    @Test
    void gameIsReadyAfterCreate(){
        Pane board = new Pane();
        GameState game = new GameState(board);

        assertEquals(PlayState.READY, game.getPlayState());
    }

    @Test
    void playerPositionIsSetAfterCreate(){
        Pane board = new Pane();
        board.setPrefSize(600, 600);

        GameState game = new GameState(board);

        assertEquals(300, ((Circle)board.getChildren().get(0)).getCenterX());
        assertEquals(550, ((Circle)board.getChildren().get(0)).getCenterY());
    }

    @Test
    void playerIsMovedLeftAfterLeftMove(){
        Pane board = new Pane();
        board.setPrefSize(600, 600);

        GameState game = new GameState(board);
        game.movePlayerLeft();

        assertEquals(290, ((Circle)board.getChildren().get(0)).getCenterX());
    }

    @Test
    void playerIsMovedRightAfterRightMove(){
        Pane board = new Pane();
        board.setPrefSize(600, 600);

        GameState game = new GameState(board);
        game.movePlayerRight();

        assertEquals(310, ((Circle)board.getChildren().get(0)).getCenterX());
    }

}