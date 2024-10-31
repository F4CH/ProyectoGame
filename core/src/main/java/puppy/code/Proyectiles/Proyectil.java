package puppy.code.Proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Proyectil {
    protected boolean destroyed;
    protected Sprite spr;
    protected float xSpeed;
    protected float ySpeed;

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

    public Proyectil(float x, float y, int ySpeed, Texture tx){
        this.spr = new Sprite(tx);
        this.spr.setPosition(x, y);
        this.destroyed = false;

        this.xSpeed = 0;
        this.ySpeed = ySpeed + 1;

    }

    // Actualiza la posición del proyectil y verifica los límites
    public abstract void update();

    // Dibuja el proyectil en el lote de sprites
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    // Verifica si el proyectil está fuera de los límites de la pantalla
    protected void checkBounds() {
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
