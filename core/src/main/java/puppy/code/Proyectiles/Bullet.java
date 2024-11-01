package puppy.code.Proyectiles;

import com.badlogic.gdx.graphics.Texture;

public class Bullet extends Proyectil {

    // Constructor
    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, ySpeed + 3, tx);
        this.xSpeed = xSpeed;
        spr.setBounds(x, y, 20, 30);
    }

    // Actualiza la posición de la bala y verifica si está fuera de los límites
    @Override
    public void update() {
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
        checkBounds();
    }

    public void setDestroyed(){
        this.destroyed = true;
    }
}
