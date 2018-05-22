package model.game;

public enum GameLevel {
    LEVEL1(20, 10, 61, 1),
    LEVEL2(50, 10, 50, 2),
    LEVEL3(90, 10, 41, 3),
    LEVEL4(150, 8, 30, 4),
    LEVEL5(Integer.MAX_VALUE, 8, 31, 5);

    public final int nextLevelOnPoints;
    public final int moveEnemiesEveryNTicks;
    public final int createEnemiesEveryNTicks;
    public final int numberOfEnemiesToCreate;

    private GameLevel(int nextLevel, int ticksToMoveEnemies, int ticksToCreateEnemies, int numberOfNewEnemies){
        nextLevelOnPoints = nextLevel;
        moveEnemiesEveryNTicks = ticksToMoveEnemies;
        createEnemiesEveryNTicks = ticksToCreateEnemies;
        numberOfEnemiesToCreate = numberOfNewEnemies;
    }

    public GameLevel next(){
        return (this.ordinal() + 1) % values().length == 0 ? this : values()[(this.ordinal() + 1) % values().length];
    }
}
