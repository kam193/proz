package model.game.elements;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameElementTest {

    @Test
    void positionOnYIsChanged(){
        GameElement element = new GameElement(new Circle(50, 50, 50));

        element.changePosition(0, 10, 100);

        assertEquals(60, element.getView().getCenterY());
    }

    @Test
    void positionOnXIsChanged(){
        GameElement element = new GameElement(new Circle(50, 50, 50));

        element.changePosition(20, 0, 1000);

        assertEquals(70, element.getView().getCenterX());
    }

    @Test
    void positionOnXIsNotChangedWhenLessThan0(){
        GameElement element = new GameElement(new Circle(50, 50, 50));

        element.changePosition(-60, 0, 1000);

        assertEquals(50, element.getView().getCenterX());
    }

    @Test
    void positionOnXIsNotChangedWhenMoreThanMax(){
        GameElement element = new GameElement(new Circle(50, 50, 50));

        element.changePosition(100, 0, 100);

        assertEquals(50, element.getView().getCenterX());
    }

    @Test
    void collisionIsDetectedWhenShould(){
        Pane board = new Pane();

        GameElement element1 = new GameElement(new Circle(50, 50, 10));
        GameElement element2 = new GameElement(new Circle(50, 55, 10));

        board.getChildren().addAll(element1.getView(), element2.getView());

        assertEquals(true, element1.isCollision(element2));
    }

    @Test
    void collisionIsNotDetectedWhenNotShould(){
        Pane board = new Pane();

        GameElement element1 = new GameElement(new Circle(50, 50, 10));
        GameElement element2 = new GameElement(new Circle(100, 100, 10));

        board.getChildren().addAll(element1.getView(), element2.getView());

        assertEquals(false, element1.isCollision(element2));
    }
}