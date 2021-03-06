package model.game.elements;

import javafx.scene.shape.Circle;

import java.util.Random;

public class Enemy extends GameElement {

    public static final int MAX_MOVE_IN_ONE_DIRECTION = 5;
    public static final int MOVE_OFFSET_Y = 3;
    public static final int START_POSITION_Y = 25;
    private static final int ENEMY_RADIUS = 20;
    private EnemyType type;
    private int lastDirectionLeft = 0;
    private int health;

    /**
     * Create enemy on Y = 25 and X = param with type from param.
     * @param startX Start X point
     * @param typeEnemy Type of enemy
     */
    public Enemy(double startX, EnemyType typeEnemy) {
        super(new Circle(ENEMY_RADIUS));
        type = typeEnemy;
        getView().getStyleClass().add(type.styleClassName);
        getView().setCenterX(startX);
        getView().setCenterY(START_POSITION_Y);
        health = type.health;
    }

    /**
     * Move enemy in Y to down, and in X five times to left,
     * next 5 times to right. Repeat. And check is on board,
     * optionaly set to remove.
     * @param maxY Max Y value
     * @param changeX X offset
     */
    public void moveEnemy(double maxY, double changeX) {
        if (lastDirectionLeft >= MAX_MOVE_IN_ONE_DIRECTION)
            changeX *= -1;
        lastDirectionLeft = (lastDirectionLeft + 1) % (MAX_MOVE_IN_ONE_DIRECTION * 2);
        changePosition(changeX, MOVE_OFFSET_Y, Double.POSITIVE_INFINITY);
        checkIsObBoard(maxY);
    }

    /**
     * Hit this enemy, decrease health and check is still alive.
     * If killed in this hit, set to remove and return true.
     * @return True, if kill in this hit
     */
    public boolean hitAndIsKilled() {
        if (--health == 0) {
            setToRemove(true);
            return true;
        }
        return false;
    }

    public EnemyType getType() {
        return type;
    }

    public enum EnemyType {
        ICECREAM("icecream", 2),
        CAKE("cake", 4),
        CANDY("candy", 1),
        BIRTHDAYCAKE("birthdaycake", 5),
        CHOCOLATE("chocolate", 3);


        private static final Random random = new Random();
        public final String styleClassName;
        public final int health;

        private EnemyType(String className, int enemyHealth) {
            styleClassName = className;
            health = enemyHealth;
        }

        public static EnemyType randomType() {
            return values()[random.nextInt(values().length)];
        }
    }
}
