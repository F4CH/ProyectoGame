
package puppy.code.Pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.Fondos.FondoEstatico;
import puppy.code.SpaceNavigation;

public class PantallaPausa implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private PantallaJuego pantallaJuego;
    private String[] opciones = {"Reanudar" , "Reiniciar Nivel" , "Volver al Menu Principal"};
    private int opcionSeleccionada = 0;
    private FondoEstatico fondoEstatico;

    public PantallaPausa(SpaceNavigation game, PantallaJuego pantallaJuego) {
        this.game = game;
        this.pantallaJuego = pantallaJuego;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

        fondoEstatico = new FondoEstatico("FondoPausa.png");
    }

    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        fondoEstatico.draw(game.getBatch());
        game.getFont().draw(game.getBatch(), "Juego en Pausa", 140 , 500);

        for(int i = 0 ; i < opciones.length ; i++){
            if(i == opcionSeleccionada){
                game.getFont().draw(game.getBatch(), "> " + opciones[i], 140 , 400 - i * 50);
            }else{
                game.getFont().draw(game.getBatch(),opciones[i], 160 , 400 - i * 50);
            }
        }
        game.getBatch().end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            opcionSeleccionada = (opcionSeleccionada > 0) ? opcionSeleccionada - 1: opciones.length - 1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            opcionSeleccionada = (opcionSeleccionada < opciones.length - 1) ? opcionSeleccionada + 1: 0;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(opcionSeleccionada == 0){
                game.setScreen(pantallaJuego);
                pantallaJuego.reanudarMusica();
            }else if(opcionSeleccionada == 1){
                game.setScreen(new PantallaJuego(game, 1, 3 ,0));
                pantallaJuego.dispose();
            }else if(opcionSeleccionada == 2){
                game.setScreen(new PantallaMenu(game));
                pantallaJuego.dispose();
            }
        }
    }

    public void show() {}

    public void resize(int width, int height) {}

    public void pause(){}

    public void resume(){}

    public void hide(){}

    public void dispose(){}
}
