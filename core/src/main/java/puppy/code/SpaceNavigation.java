package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.Fondos.FondoAnimado;
import puppy.code.Pantallas.*;


public class SpaceNavigation extends Game {
	private String nombreJuego = "Battle Of The Space";
	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;
    private FondoAnimado fondoAnimado;


	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(); // usa Arial font x defecto
		font.getData().setScale(2f);
        fondoAnimado = new FondoAnimado("fondo.gif");
        Screen ss = new PantallaMenu(this);
        this.setScreen(ss);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
        fondoAnimado.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

    public FondoAnimado getFondoAnimado() {
        return fondoAnimado;
    }

}
