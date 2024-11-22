package puppy.code.Proyectiles;

import com.badlogic.gdx.graphics.Texture;

public class Bullet extends Proyectil {

    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, ySpeed + 3, tx);
        setXSpeed(xSpeed);
        getSpr().setBounds(x, y, 20, 30);
    }

    @Override
    public void update() {
        getSpr().setPosition(getSpr().getX() + getXSpeed(), getSpr().getY() + getYSpeed());
        checkBounds();
    }

    public void setDestroyed() {
        super.setDestroyed(true);
    }
}
