package model;

import javafx.scene.shape.Circle;

import javafx.scene.Node;

public class GameElement {
    private final Circle view;

    public GameElement(Circle viewObject){
        view = viewObject;
    }

    public Circle getView() {
        return view;
    }

    public void changePosition(double offsetX, double offsetY, double maxX){
        if (getView().getCenterX() + offsetX <= maxX && getView().getCenterX() + offsetX >= 0)
            getView().setCenterX(getView().getCenterX() + offsetX);
        //if (getView().getCenterY() + offsetY <= maxY && getView().getCenterY() + offsetY >= 0)
        getView().setCenterY(getView().getCenterY() + offsetY);

    }

}
