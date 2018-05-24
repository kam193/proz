package model.game.elements;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {

    @Test
    void positionIsChangedAfterOneMove() {
        Bullet b = new Bullet(0, 50);

        b.moveBullet(100);

        assertEquals(35, b.getView().getCenterY());
    }

    @Test
    void positionIsSetAfterCreate(){
        Bullet b = new Bullet(40, 50);

        assertArrayEquals(new double[]{40, 50}, new double[]{b.getView().getCenterX(), b.getView().getCenterY()});
    }

    @Test
    void bulletIsToRemoveWhenMoveMore0(){
        Bullet b = new Bullet(50, 10);
        b.moveBullet(50);
        assertEquals(true, b.isToRemove());
    }
}