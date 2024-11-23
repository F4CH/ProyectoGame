package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.Fondos.FondoAnimado;
import puppy.code.Pantallas.PantallaFactory;
import puppy.code.Pantallas.PantallaMenu;
import puppy.code.Patrones.SpaceNavegationPantallaFactory;


public class SpaceNavigation extends Game {
	private String nombreJuego = "Battle Of The Space";
	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;
    private FondoAnimado fondoAnimado;
    private PantallaFactory pantallaFactory;


	public void create() {
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(); // usa Arial font x defecto
		font.getData().setScale(2f);
        fondoAnimado = new FondoAnimado("fondo.gif");
        pantallaFactory = new SpaceNavegationPantallaFactory(this);
		this.setScreen(pantallaFactory.crearPantallaMenu());
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

    public PantallaFactory getPantallaFactory() {
        return pantallaFactory;
    }
}
