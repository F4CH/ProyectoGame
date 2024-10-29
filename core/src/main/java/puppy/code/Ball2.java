package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 {

    private static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    private int xSpeed;
    private int ySpeed;
    private boolean destroyed;
    private Sprite spr;

    // Constructor para inicializar la posición y velocidad del asteroide
    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        this.spr = new Sprite(tx);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.destroyed = false;

        // Validar que el borde de la esfera no quede fuera de la pantalla
        if (x - size < 0) x += size;
        if (x + size > SCREEN_WIDTH) x -= size;
        if (y - size < 0) y += size;
        if (y + size > SCREEN_HEIGHT) y -= size;

        spr.setPosition(x, y);
    }

    // Actualiza la posición del asteroide y verifica los límites de la pantalla
    public void update() {
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
        checkBounds();
    }

    // Dibuja el asteroide en pantalla
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    // Verifica colisión y rebote con otro asteroide
    public void checkCollision(Ball2 other) {
        if (this.getArea().overlaps(other.getArea())) {
            handleBounce(other);
        }
    }

    // Devuelve el rectángulo de colisión del asteroide
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    // Getter para verificar si está destruido
    public boolean isDestroyed() {
        return destroyed;
    }

    // Métodos privados para encapsular lógicas específicas

    // Verifica si el asteroide sale de los límites y ajusta la dirección de velocidad
    private void checkBounds() {
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > SCREEN_WIDTH) {
            xSpeed = -xSpeed;
        }
        if (spr.getY() < 0 || spr.getY() + spr.getHeight() > SCREEN_HEIGHT) {
            ySpeed = -ySpeed;
        }
    }

    // Lógica de destrucción del asteroide
    private void destroy() {
        destroyed = true;
    }

    // Maneja el rebote del asteroide al colisionar con otro asteroide
    private void handleBounce(Ball2 other) {
        int tempXSpeed = this.xSpeed;
        int tempYSpeed = this.ySpeed;

        this.xSpeed = -other.xSpeed;
        this.ySpeed = -other.ySpeed;

        other.xSpeed = -tempXSpeed;
        other.ySpeed = -tempYSpeed;
    }
}
