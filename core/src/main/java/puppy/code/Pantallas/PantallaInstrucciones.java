package puppy.code.Pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.Fondos.FondoEstatico;
import puppy.code.SpaceNavigation;

public class PantallaInstrucciones implements Screen{

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private FondoEstatico fondoEstatico;

    public PantallaInstrucciones(SpaceNavigation game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

        fondoEstatico = new FondoEstatico("FondoInstrucciones.png");
    }

    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        fondoEstatico.draw(game.getBatch());
        game.getFont().draw(game.getBatch(), "Instrucciones", 100 , 750);
        game.getFont().draw(game.getBatch(), "Controles y Power UPS", 100, 650);
        game.getFont().draw(game.getBatch(), "Mover la nave: Flechas de Direccion", 120, 600 );
        game.getFont().draw(game.getBatch(), "Disparar : Tecla Z", 120, 550);
        game.getFont().draw(game.getBatch(), "Pausar: Tecla ESC", 120 , 500);
        game.getFont().draw(game.getBatch(), "Disparo Extra : 50 puntos", 120 , 450);
        game.getFont().draw(game.getBatch(), "Disparo Diagonal : 100 puntos", 120 , 400);
        game.getFont().draw(game.getBatch(), "Vida Extra : 300 Puntos", 120, 350);
        game.getFont().draw(game.getBatch(), "Objetivo:", 100, 250);
        game.getFont().draw(game.getBatch(), "Eliminar a los enemigos y evitar que te golpeen para obtener la mayor puntuacion", 120, 200);
        game.getFont().draw(game.getBatch(), "Presiona ESC para volver al menu principal.", 120, 100);
        game.getBatch().end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(game.getPantallaFactory().crearPantallaMenu());
            dispose();
        }
    }

    public void show(){}

    public void resize(int width, int height){}

    public void pause(){}

    public void resume(){}

    public void hide(){}

    public void dispose(){
        fondoEstatico.dispose();
    }
}
