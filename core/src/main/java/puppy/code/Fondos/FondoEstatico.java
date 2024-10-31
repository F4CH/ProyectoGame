package puppy.code.Fondos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class FondoEstatico {
    private Texture fondo;

    public FondoEstatico(String rutaImangem){
        fondo = new Texture(Gdx.files.internal(rutaImangem));
    }

    public void draw(SpriteBatch batch){

        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void dispose(){
        fondo.dispose();
    }
}
