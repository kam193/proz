package model;

import javafx.scene.shape.Circle;

import java.util.Random;

public class Enemy extends GameElement{

    private EnemyType type;
    private boolean lastDirectionLeft = false;

    public Enemy(double startX, EnemyType typeEnemy){
        super(new Circle(20));
        type = typeEnemy;
        getView().getStyleClass().add(type.styleClassName);
        getView().setCenterX(startX);
        getView().setCenterY(25);

    }

    public void moveEnemy(){
        double changeX = -15;
        if (lastDirectionLeft)
            changeX *= -1;
        lastDirectionLeft = !lastDirectionLeft;
        changePosition(changeX, 10, Double.POSITIVE_INFINITY);
    }

    public EnemyType getType() {
        return type;
    }

    public enum EnemyType{
        ICECREAM("icecream"),
        CAKE("cake");

        String styleClassName;

        private static final Random random = new Random();

        private EnemyType(String className){
            styleClassName = className;
        }

        public static EnemyType randomType(){
            return values()[random.nextInt(values().length)];
        }
    }
}
