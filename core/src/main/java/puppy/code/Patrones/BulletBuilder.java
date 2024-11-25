package puppy.code.Patrones;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Proyectiles.Bullet;

public interface BulletBuilder {
    void setTexture(Texture texture);

    void setPosition(float x, float y);

    void setSpeed(float xSpeed, float ySpeed);

    void setBounds(float x, float y);

    Bullet build(); // Construye y retorna el Producto final

    Bullet getProducto(); // Retorna la instancia en proceso de construcción
}
