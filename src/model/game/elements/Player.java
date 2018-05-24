package model.game.elements;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;

public class Player extends GameElement {
    private SimpleIntegerProperty healthProperty;

    public Player(double startX, double startY, int playerHealth) {
        super(new Circle(20));
        getView().getStyleClass().add("watermelon");
        getView().setCenterX(startX);
        getView().setCenterY(startY);
        healthProperty = new SimpleIntegerProperty(playerHealth);
    }

    public Bullet shoot() {
        return new Bullet(getView().getCenterX(), getView().getCenterY() - 20);
    }

    public SimpleIntegerProperty getHealyhProperty() {
        return healthProperty;
    }

    public void setHealthProperty(int health) {
        healthProperty.set(health);
    }
}
