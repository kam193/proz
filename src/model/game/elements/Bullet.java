package model.game.elements;

import javafx.scene.shape.Circle;

public class Bullet extends GameElement {
    public Bullet(double startX, double startY){
        super(new Circle(2.5));
        getView().getStyleClass().add("bullet");
        getView().setCenterY(startY);
        getView().setCenterX(startX);
    }

    public void moveBullet(double maxY){
        changePosition(0, -15, Double.POSITIVE_INFINITY);
        checkIsObBoard(maxY);
    }
}
