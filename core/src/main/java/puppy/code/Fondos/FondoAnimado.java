package puppy.code.Fondos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;

public class FondoAnimado {
    private Animation<TextureRegion> gifAnimation;
    private float elapsedTime = 0;

    public FondoAnimado(String gifPath) {
        // Cargar el archivo GIF desde la ruta y crear la animación
        FileHandle gifFile = Gdx.files.internal(gifPath);
        gifAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, gifFile.read());
    }

    public void update(float delta) {
        // Actualizar el tiempo transcurrido para avanzar en la animación
        elapsedTime += delta;
    }

    public void draw(SpriteBatch batch) {
        // Obtener el cuadro actual del GIF según el tiempo transcurrido
        TextureRegion currentFrame = gifAnimation.getKeyFrame(elapsedTime, true);
        // Dibujar el cuadro actual en la pantalla, ajustando al tamaño de la pantalla
        batch.draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void dispose() {
        // Crea un nuevo arreglo de TextureRegion basado en los elementos de getKeyFrames()
        Object[] frames = gifAnimation.getKeyFrames();

        for (Object obj : frames) {
            if (obj instanceof TextureRegion) {
                TextureRegion region = (TextureRegion) obj;
                if (region.getTexture() != null) {
                    region.getTexture().dispose();
                }
            }
        }
    }
}
