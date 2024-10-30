package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class AtaqueEnemigo1 extends Proyectil{
    // Constructor que toma un ángulo de dirección
    public AtaqueEnemigo1(float x, float y, int speed, float angle, Texture tx) {
        super(x, y, speed, angle, tx);
    }

    // Actualiza la posición del proyectil y verifica los límites
    @Override
    public void update() {
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
        checkBounds();
    }
}
