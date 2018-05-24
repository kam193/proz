package model.game;

public enum GameLevel {
    LEVEL1(20, 4, 61, 2, 5, "Level 1"),
    LEVEL2(50, 3, 50, 2, 6, "Level 2"),
    LEVEL3(90, 2, 41, 3, 7, "Level 3"),
    LEVEL4(150, 2, 30, 4, 8, "Level 4"),
    LEVEL5(Integer.MAX_VALUE, 2, 31, 4, 9, "Level 5");

    public final int nextLevelOnPoints;
    public final int moveEnemiesEveryNTicks;
    public final int createEnemiesEveryNTicks;
    public final int numberOfEnemiesToCreate;
    public final double enemiesChangeX;
    public final String levelName;

    private GameLevel(int nextLevel, int ticksToMoveEnemies, int ticksToCreateEnemies, int numberOfNewEnemies, double changeX, String name){
        nextLevelOnPoints = nextLevel;
        moveEnemiesEveryNTicks = ticksToMoveEnemies;
        createEnemiesEveryNTicks = ticksToCreateEnemies;
        numberOfEnemiesToCreate = numberOfNewEnemies;
        enemiesChangeX = changeX;
        levelName = name;
    }

    public GameLevel next(){
        return (this.ordinal() + 1) % values().length == 0 ? this : values()[(this.ordinal() + 1) % values().length];
    }

    @Override
    public String toString() {
        return levelName;
    }
}
