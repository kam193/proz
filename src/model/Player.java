package model;

import javafx.scene.shape.Circle;

public class Player extends GameElement{
    public Player(double startX, double startY){
        super(new Circle(20));
        getView().getStyleClass().add("watermelon");
        getView().setCenterX(startX);
        getView().setCenterY(startY);
    }
}
