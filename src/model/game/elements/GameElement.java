package model.game.elements;

import javafx.scene.shape.Circle;

/**
 * Base class for any element on game's board
 */
public class GameElement {
    /**
     * An Circle to render this element
     */
    private final Circle view;
    private boolean toRemove = false;

    /**
     * Create new game element
     * @param viewObject Circle to render this object
     */
    public GameElement(Circle viewObject) {
        view = viewObject;
    }

    /**
     * @return Circle object to render this element
     */
    public Circle getView() {
        return view;
    }

    /**
     * @return True, when this object is marked to remove from game board
     */
    public boolean isToRemove() {
        return toRemove;
    }

    /**
     * Change flag, is this object should be remove from game board
     * @param toRemove New value
     */
    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    /**
     * Change position of this object.
     * It changes center X, Y like new_X = old_X + offsetX.
     * X is not changed, if will be less than 0 or more than maxX
     * @param offsetX Offset to X
     * @param offsetY Offset to Y
     * @param maxX Max value of X
     */
    public void changePosition(double offsetX, double offsetY, double maxX) {
        if (getView().getCenterX() + offsetX <= maxX && getView().getCenterX() + offsetX >= 0)
            getView().setCenterX(getView().getCenterX() + offsetX);
        getView().setCenterY(getView().getCenterY() + offsetY);

    }

    /**
     * Check collision with another element
     * @param other Other element
     * @return true, is collision detected
     */
    public boolean isCollision(GameElement other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent()) && !isToRemove() && !other.isToRemove();
    }

    /**
     * Check is this element on board, chceks only Y.
     * If not, element is set to remove.
     * @param maxY max value of Y to be on board
     */
    protected void checkIsObBoard(double maxY) {
        if (getView().getCenterY() > maxY || getView().getCenterY() < 0)
            setToRemove(true);
    }

}
