package model.game;

import model.game.elements.Enemy;

import java.util.List;

public class GameStats {
    private int[] killedEnemies = new int[Enemy.EnemyType.values().length];
    private int points = 0;

    public int getPoints() {
        return points;
    }

    public void addKilledEnemy(Enemy.EnemyType type){
        points += type.health;
        killedEnemies[type.ordinal()] += 1;
    }

    public int getKilledEnemy(Enemy.EnemyType type){
        return killedEnemies[type.ordinal()];
    }
}
