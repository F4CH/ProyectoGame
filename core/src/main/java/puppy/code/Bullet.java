package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {

    private static final int DEFAULT_Y_SPEED = 3;

    private int xSpeed;
    private int ySpeed;
    private boolean destroyed;
    private Sprite spr;

    // Constructor
    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
        this.spr = new Sprite(tx);
        this.spr.setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed + DEFAULT_Y_SPEED;
        this.destroyed = false;
    }

    // Actualiza la posición de la bala y verifica si está fuera de los límites
    public void update() {
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
        checkBounds();
    }

    // Dibuja la bala en el lote de sprites
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    // Verifica si ha colisionado con un asteroide y destruye la bala
    public boolean checkCollision(Ball2 b2) {
        if (spr.getBoundingRectangle().overlaps(b2.getArea())) {
            this.destroyed = true;
            return true;
        }
        return false;
    }

    // Verifica si la bala está fuera de los límites de la pantalla
    private void checkBounds() {
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }

    // Getter para verificar si la bala está destruida
    public boolean isDestroyed() {
        return destroyed;
    }
}
