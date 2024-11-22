package puppy.code.Proyectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Proyectil {
    private boolean destroyed;
    private Sprite spr;
    private float xSpeed;
    private float ySpeed;

    public Proyectil(float x, float y, int speed, float angle, Texture tx) {
        this.spr = new Sprite(tx);
        this.spr.setPosition(x, y);
        this.destroyed = false;

        // Calcula las velocidades en X y Y basadas en el ángulo dado
        float radians = (float) Math.toRadians(angle);
        this.xSpeed = speed * (float) Math.cos(radians);
        this.ySpeed = speed * (float) Math.sin(radians);

        // Configura tamaño de proyectil
        spr.setBounds(x, y, 30, 30);
    }

    public Proyectil(float x, float y, int ySpeed, Texture tx) {
        this.spr = new Sprite(tx);
        this.spr.setPosition(x, y);
        this.destroyed = false;

        this.xSpeed = 0;
        this.ySpeed = ySpeed + 1;

        spr.setBounds(x, y, 30, 30);
    }

    public abstract void update();

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    protected void checkBounds() {
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Sprite getSpr() {
        return spr;
    }

    public void setSpr(Sprite spr) {
        this.spr = spr;
    }

    public float getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
}
