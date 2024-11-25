package puppy.code.Patrones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import puppy.code.Proyectiles.Bullet;

public class ConcreteBulletBuilder implements BulletBuilder{
    private Bullet bullet;

    public ConcreteBulletBuilder() {}

    @Override
    public void setTexture(Texture texture) {
        this.bullet = new Bullet(); // Inicializamos un nuevo Producto vacío
        Sprite sprite = new Sprite(texture);
        bullet.setSpr(sprite);
    }

    @Override
    public void setPosition(float x, float y) {
        bullet.getSpr().setPosition(x, y);
    }

    @Override
    public void setSpeed(float xSpeed, float ySpeed) {
        bullet.setXSpeed(xSpeed);
        bullet.setYSpeed(ySpeed);
    }

    @Override
    public void setBounds(float x, float y){
        bullet.getSpr().setBounds(x, y, 20, 30);
    }

    @Override
    public Bullet build() {
        return bullet; // Retorna el Producto final construido
    }

    @Override
    public Bullet getProducto() {
        return bullet;
    }
}
