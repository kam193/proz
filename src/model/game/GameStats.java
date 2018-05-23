package model.game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.game.elements.Enemy;

import java.util.ArrayList;
import java.util.List;

public class GameStats {
    private ObservableList<Integer> killedEnemies = FXCollections.observableArrayList(new ArrayList<>());
    private SimpleIntegerProperty pointsProperty = new SimpleIntegerProperty();

    public GameStats() {
        for (int i = 0; i < Enemy.EnemyType.values().length; i++) {
            killedEnemies.add(0);
        }
    }

    public void addKilledEnemy(Enemy.EnemyType type) {
        pointsProperty.set(pointsProperty.getValue() + type.health);
        killedEnemies.set(type.ordinal(), killedEnemies.get(type.ordinal()) + 1);
    }

    public int getKilledEnemy(Enemy.EnemyType type) {
        return killedEnemies.get(type.ordinal());
    }

    public SimpleIntegerProperty getPointsProperty() {
        return pointsProperty;
    }

    public ObservableList<Integer> getKilledEnemies() {
        return killedEnemies;
    }

    public void clear() {
        pointsProperty.set(0);
        for (int i = 0; i < Enemy.EnemyType.values().length; i++) {
            killedEnemies.set(i, 0);
        }
    }
}
