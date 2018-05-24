package model.game.elements;

import javafx.scene.shape.Circle;

public class GameElement {
    private final Circle view;
    private boolean toRemove = false;

    public GameElement(Circle viewObject) {
        view = viewObject;
    }

    public Circle getView() {
        return view;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    public void changePosition(double offsetX, double offsetY, double maxX) {
        if (getView().getCenterX() + offsetX <= maxX && getView().getCenterX() + offsetX >= 0)
            getView().setCenterX(getView().getCenterX() + offsetX);
        getView().setCenterY(getView().getCenterY() + offsetY);

    }

    public boolean isCollision(GameElement other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent()) && !isToRemove() && !other.isToRemove();
    }

    protected void checkIsObBoard(double maxY) {
        if (getView().getCenterY() > maxY || getView().getCenterY() < 0)
            setToRemove(true);
    }

}
