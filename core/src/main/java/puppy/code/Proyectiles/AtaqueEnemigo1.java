package puppy.code.Proyectiles;

import com.badlogic.gdx.graphics.Texture;

public class AtaqueEnemigo1 extends Proyectil {
    public AtaqueEnemigo1(float x, float y, int speed, float angle, Texture tx) {
        super(x, y, speed, angle, tx);
    }

    @Override
    public void update() {
        getSpr().setPosition(getSpr().getX() + getXSpeed(), getSpr().getY() + getYSpeed());
        checkBounds();
    }
}
