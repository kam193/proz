package model.game.elements;

import javafx.scene.shape.Circle;

import java.util.Random;

public class Enemy extends GameElement{

    private EnemyType type;
    private boolean lastDirectionLeft = false;
    private int health;

    public Enemy(double startX, EnemyType typeEnemy){
        super(new Circle(20));
        type = typeEnemy;
        getView().getStyleClass().add(type.styleClassName);
        getView().setCenterX(startX);
        getView().setCenterY(25);
        health = type.health;
    }

    public void moveEnemy(double maxY){
        double changeX = -15;
        if (lastDirectionLeft)
            changeX *= -1;
        lastDirectionLeft = !lastDirectionLeft;
        changePosition(changeX, 10, Double.POSITIVE_INFINITY);
        checkIsObBoard(maxY);
    }

    public void hit(){
        if(--health <= 0)
            setToRemove(true);
    }

    public EnemyType getType() {
        return type;
    }

    public enum EnemyType{
        ICECREAM("icecream", 2),
        CAKE("cake", 4),
        CANDY("candy", 1),
        BIRTHDAYCAKE("birthdaycake", 5),
        CHOCOLATE("chocolate", 3);


        String styleClassName;
        int health;

        private static final Random random = new Random();

        private EnemyType(String className, int enemyHealth){
            styleClassName = className;
            health = enemyHealth;
        }

        public static EnemyType randomType(){
            return values()[random.nextInt(values().length)];
        }
    }
}
