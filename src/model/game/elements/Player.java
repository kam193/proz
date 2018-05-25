package model.game.elements;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;

public class Player extends GameElement {
    private static final int PLAYER_RADIUS = 20;
    private SimpleIntegerProperty healthProperty;

    /**
     * Create player
     * @param startX Start X point
     * @param startY Start Y point
     * @param playerHealth Initial player health
     */
    public Player(double startX, double startY, int playerHealth) {
        super(new Circle(PLAYER_RADIUS));
        getView().getStyleClass().add("watermelon");
        getView().setCenterX(startX);
        getView().setCenterY(startY);
        healthProperty = new SimpleIntegerProperty(playerHealth);
    }

    /**
     * Generate shoot.
     * @return New bullet
     */
    public Bullet shoot() {
        return new Bullet(getView().getCenterX(), getView().getCenterY() - 20);
    }

    public SimpleIntegerProperty getHealthProperty() {
        return healthProperty;
    }

    public void setHealthProperty(int health) {
        healthProperty.set(health);
    }
}
