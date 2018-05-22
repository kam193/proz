package model.game.elements;

import javafx.scene.shape.Circle;

public class Player extends GameElement{
    private int health;

    public Player(double startX, double startY, int playerHealth){
        super(new Circle(20));
        getView().getStyleClass().add("watermelon");
        getView().setCenterX(startX);
        getView().setCenterY(startY);
        health = playerHealth;
    }

    public Bullet shoot(){
        return new Bullet(getView().getCenterX(), getView().getCenterY()-20);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
