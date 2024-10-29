package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Proyectil {
    private boolean destroyed;
    private Sprite spr;
    private float xSpeed;
    private float ySpeed;

    // Constructor que toma un ángulo de dirección
    public Proyectil(float x, float y, int speed, float angle, Texture tx) {
        this.spr = new Sprite(tx);
        this.spr.setPosition(x, y);
        this.destroyed = false;

        // Calcula las velocidades en X y Y basadas en el ángulo dado
        float radians = (float) Math.toRadians(angle);
        xSpeed = speed * (float) Math.cos(radians);
        ySpeed = speed * (float) Math.sin(radians);

        // Configura tamaño de proyectil
        spr.setBounds(x, y, 30, 30);

        // Configura la velocidad resultante en el sprite
        spr.setX(spr.getX() + xSpeed);
        spr.setY(spr.getY() + ySpeed);
    }

    // Actualiza la posición del proyectil y verifica los límites
    public void update() {
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
        checkBounds();
    }

    // Dibuja el proyectil en el lote de sprites
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    // Verifica si el proyectil está fuera de los límites de la pantalla
    private void checkBounds() {
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }

    // Getter para verificar si el proyectil está destruido
    public boolean isDestroyed() {
        return destroyed;
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
}
