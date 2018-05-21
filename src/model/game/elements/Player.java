package model.game.elements;

import javafx.scene.shape.Circle;

public class Player extends GameElement{
    public Player(double startX, double startY){
        super(new Circle(20));
        getView().getStyleClass().add("watermelon");
        getView().setCenterX(startX);
        getView().setCenterY(startY);
    }

    public Bullet shoot(){
        return new Bullet(getView().getCenterX(), getView().getCenterY()-20);
    }
}
