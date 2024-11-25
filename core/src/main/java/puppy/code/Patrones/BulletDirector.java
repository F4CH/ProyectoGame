package puppy.code.Patrones;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Proyectiles.Bullet;

public class BulletDirector {
    private BulletBuilder builder;

    public void setBuilder(BulletBuilder builder) {
        this.builder = builder;
    }

    public Bullet construir(float x, float y, float xSpeed, float ySpeed, Texture texture) {
        builder.setTexture(texture);
        builder.setPosition(x, y);
        builder.setSpeed(xSpeed, ySpeed);
        builder.setBounds(x, y);
        return builder.build(); // Retorna el Producto construido
    }
}
