package model.game.elements;

import javafx.scene.shape.Circle;

public class Bullet extends GameElement {

    private static final double BULLET_RADIUS = 2.5;
    private static final int MOVE_OFFSET_Y = 15;

    /**
     * Create bullet
     * @param startX Start X point
     * @param startY Start Y point
     */
    public Bullet(double startX, double startY) {
        super(new Circle(BULLET_RADIUS));
        getView().getStyleClass().add("bullet");
        getView().setCenterY(startY);
        getView().setCenterX(startX);
    }

    /**
     * Move bullet to up and check is on board
     * @param maxY Max value of Y
     */
    public void moveBullet(double maxY) {
        changePosition(0, -MOVE_OFFSET_Y, Double.POSITIVE_INFINITY);
        checkIsObBoard(maxY);
    }
}
