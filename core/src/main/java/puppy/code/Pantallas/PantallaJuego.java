package puppy.code.Pantallas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.*;
import puppy.code.Enemigos.Enemigo;
import puppy.code.Enemigos.EnemigoBasico1;
import puppy.code.Enemigos.EnemigoBasico2;
import puppy.code.Enemigos.EnemigoBasico3;
import puppy.code.Fondos.FondoAnimado;
import puppy.code.PowerUps.PowerUp;
import puppy.code.PowerUps.BalasExtra;
import puppy.code.PowerUps.BalasDiagonales;
import puppy.code.PowerUps.VidasExtra;
import com.badlogic.gdx.Input;
import puppy.code.Proyectiles.Bullet;
import puppy.code.Proyectiles.Proyectil;

public class PantallaJuego implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Music gameMusic;
    private int score;
    private int ronda;
    private boolean juegoPausado;
    private float posXNave;
    private float posYNave;
    private FondoAnimado fondoAnimado;

    private EnemigoBasico1 enemigoBasico1;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private ArrayList<Proyectil> proyectiles = new ArrayList<>();
    private Nave4 nave;
    private ArrayList<Bullet> balas = new ArrayList<>();

    // Mapa para aplicar PowerUps dinámicamente basado en el puntaje
    private final Map<Integer, PowerUp> powerUpMap;

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.juegoPausado = false;

        batch = game.getBatch();
        fondoAnimado = new FondoAnimado("fondo.gif");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicGame.ogg"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30, new Texture(Gdx.files.internal("MainShip3.png")),
            Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
            new Texture(Gdx.files.internal("Rocket2.png")),
            Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas);

        enemigoBasico1 = new EnemigoBasico1(Gdx.graphics.getWidth() / 2 - 50, 700);
        EnemigoBasico2 enemigoBasico2 = new EnemigoBasico2(Gdx.graphics.getWidth() / 2 - 150, 700);
        EnemigoBasico3 enemigoBasico3 = new EnemigoBasico3(Gdx.graphics.getWidth() / 2 - 20, 700);
        enemigos.add(enemigoBasico1);
        enemigos.add(enemigoBasico2);
        enemigos.add(enemigoBasico3);

        // Inicializar el mapa de PowerUps
        powerUpMap = new HashMap<>();
        powerUpMap.put(50, new BalasExtra());
        powerUpMap.put(100, new BalasDiagonales());
        powerUpMap.put(300, new VidasExtra());
    }

    public Nave4 getNave() {
        return nave;
    }

    private void aplicarPowerUpSiCorresponde() {
        PowerUp powerUp = powerUpMap.get(score);
        if (powerUp != null) {
            powerUp.aplicarPowerUp(this);
        }
    }

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        if (juegoPausado) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaPausa(game, this));
            gameMusic.pause();
            juegoPausado = true;
            return;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fondoAnimado.update(delta);
        batch.begin();
        fondoAnimado.draw(batch);
        dibujaEncabezado();

        nave.draw(batch, this);

        for (Enemigo e : enemigos) {
            e.draw(batch, this, delta);
        }

        // Actualizar y dibujar balas
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            b.draw(batch);
            for (int j = 0; j < enemigos.size(); j++) {
                Enemigo e = enemigos.get(j);
                if (e.checkCollision(b)) {
                    b.setDestroyed();
                    if (e.estaDestruida()) {
                        enemigos.remove(j);
                        j--;
                        score += 50;
                        aplicarPowerUpSiCorresponde();
                        break;
                    }
                }
            }
            if (b.isDestroyed()) {
                balas.remove(i);
                i--;
            }
        }

        // Actualizar proyectiles enemigos
        for (int i = 0; i < proyectiles.size(); i++) {
            Proyectil p = proyectiles.get(i);
            p.update();
            p.draw(batch);

            if (p.isDestroyed()) {
                proyectiles.remove(i);
                i--;
            }
        }

        nave.draw(batch, this);
        for (int i = 0; i < proyectiles.size(); i++) {
            Proyectil p = proyectiles.get(i);
            p.draw(batch);

            if (!nave.estaHerido() && nave.checkCollision(p)) {
                proyectiles.remove(i);
                i--;
            }
        }

        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        if (enemigos.isEmpty()) {
            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        batch.end();
    }

    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }

    public boolean agregarProyectil(Proyectil pp) {
        return proyectiles.add(pp);
    }

    public void reanudarMusica() {
        juegoPausado = false;
        gameMusic.play();
    }

    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        juegoPausado = true;
        if (nave != null) {
            posXNave = nave.getX();
            posYNave = nave.getY();
            nave.detenerMovimiento();
        }
        gameMusic.pause();
        game.setScreen(new PantallaPausa(game, this));
    }

    @Override
    public void resume() {
        juegoPausado = false;
        if (nave != null) {
            nave.setPosition(posXNave, posYNave);
            nave.detenerMovimiento();
        }
        gameMusic.play();
        game.setScreen(new PantallaJuego(game, ronda, nave.getVidas(), score));
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        this.gameMusic.dispose();
    }
}
