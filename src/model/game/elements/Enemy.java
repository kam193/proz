package model.game.elements;

import javafx.scene.shape.Circle;

import java.util.Random;

public class Enemy extends GameElement {

    private EnemyType type;
    private int lastDirectionLeft = 0;
    private int health;

    public Enemy(double startX, EnemyType typeEnemy) {
        super(new Circle(20));
        type = typeEnemy;
        getView().getStyleClass().add(type.styleClassName);
        getView().setCenterX(startX);
        getView().setCenterY(25);
        health = type.health;
    }

    public void moveEnemy(double maxY, double changeX) {
        if (lastDirectionLeft >= 5)
            changeX *= -1;
        lastDirectionLeft = (lastDirectionLeft + 1) % 10;
        changePosition(changeX, 3, Double.POSITIVE_INFINITY);
        checkIsObBoard(maxY);
    }

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
