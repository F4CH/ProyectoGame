package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.Fondos.FondoEstatico;
import puppy.code.SpaceNavigation;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
    private String[] opciones = {"Iniciar Juego","Instrucciones","Salir"};
    private int opcionSeleccionada = 0;
    private FondoEstatico fondoEstatico;

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);

        fondoEstatico = new FondoEstatico("FondoMenu.png");
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);


		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
        fondoEstatico.draw(game.getBatch());
		game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation !", 140, 500);
        for(int i = 0; i < opciones.length; i++){
            if(i == opcionSeleccionada){
                game.getFont().draw(game.getBatch(), "> " + opciones[i], 140, 400 - i * 50);
            }else{
                game.getFont().draw(game.getBatch(), "  " + opciones[i], 160, 400 - i * 50);
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
                game.setScreen(new PantallaJuego(game, 1, 3, 0));
            }else if (opcionSeleccionada == 1) {
                game.setScreen(new PantallaInstrucciones(game));
                dispose();
            }else if(opcionSeleccionada == 2){
                Gdx.app.exit();
            }
        }
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}