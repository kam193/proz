package model.game.elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    @Test
    void enemyIsToRemoveWhenMoveBeyondBoard() {
        Enemy enemy = new Enemy(50, Enemy.EnemyType.ICECREAM);

        enemy.moveEnemy(26, 0);

        assertEquals(true, enemy.isToRemove());
    }

    @Test
    void enemyIsMovingInOneDirectionOnXByTwoMove(){
        Enemy enemy = new Enemy(50, Enemy.EnemyType.ICECREAM);

        enemy.moveEnemy(100, 5);
        enemy.moveEnemy(100, 5);

        assertEquals(60, enemy.getView().getCenterX());
    }

    @Test
    void positionIsSetOnCreate(){
        Enemy enemy = new Enemy(50, Enemy.EnemyType.ICECREAM);

        assertEquals(25, enemy.getView().getCenterY());
        assertEquals(50, enemy.getView().getCenterX());
    }

    @Test
    void enemyIsDeadAfterCorrectHit(){
        Enemy enemyIceCream = new Enemy(50, Enemy.EnemyType.ICECREAM);

        for (int i = 0; i < Enemy.EnemyType.ICECREAM.health - 1; i++) {
            enemyIceCream.hitAndIsKilled();
        }

        assertEquals(true, enemyIceCream.hitAndIsKilled());
    }

    @Test
    void enemyIsNotDeadAfterToFewHit(){
        Enemy enemyIceCream = new Enemy(50, Enemy.EnemyType.ICECREAM);

        for (int i = 0; i < Enemy.EnemyType.ICECREAM.health - 2; i++) {
            enemyIceCream.hitAndIsKilled();
        }

        assertEquals(false, enemyIceCream.hitAndIsKilled());
    }

    @Test
    void enemyIsToRemoveAfterKilled(){
        Enemy enemyIceCream = new Enemy(50, Enemy.EnemyType.ICECREAM);

        for (int i = 0; i < Enemy.EnemyType.ICECREAM.health; i++) {
            enemyIceCream.hitAndIsKilled();
        }

        assertEquals(true, enemyIceCream.isToRemove());
    }

    @Test
    void enemyIsNotToRemoveAfterTooFewHits(){
        Enemy enemyIceCream = new Enemy(50, Enemy.EnemyType.ICECREAM);

        enemyIceCream.hitAndIsKilled();

        assertEquals(false, enemyIceCream.isToRemove());
    }
}