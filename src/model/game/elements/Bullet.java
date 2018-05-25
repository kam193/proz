package model.game.elements;

import javafx.scene.shape.Circle;

public class Bullet extends GameElement {
    /**
     * Create bullet
     * @param startX Start X point
     * @param startY Start Y point
     */
    public Bullet(double startX, double startY) {
        super(new Circle(2.5));
        getView().getStyleClass().add("bullet");
        getView().setCenterY(startY);
        getView().setCenterX(startX);
    }

    /**
     * Move bullet to up and check is on board
     * @param maxY Max value of Y
     */
    public void moveBullet(double maxY) {
        changePosition(0, -15, Double.POSITIVE_INFINITY);
        checkIsObBoard(maxY);
    }
}
