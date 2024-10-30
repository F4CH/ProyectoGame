package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.PowerUps.BalasExtra;
import puppy.code.PowerUps.VidasExtra;
import com.badlogic.gdx.Input;


public class PantallaJuego implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Music gameMusic;
    private int score;
    private int ronda;
    private boolean juegoPausado;
    private float posXNave;
    private float posYNave;


    private EnemigoBasico1 enemigoBasico1;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private ArrayList<Proyectil> proyectiles = new ArrayList<>();

    private Nave4 nave;

    private ArrayList<Bullet> balas = new ArrayList<>();
    private BalasExtra auxBalasExtra = new BalasExtra();
    private VidasExtra auxVidasExtra = new VidasExtra();

    public int getScore() {return score;}

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.juegoPausado = false;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        // Inicializar assets; música de fondo y efectos de sonido
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1,0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicGame.ogg"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        // Cargar imagen de la nave, 64x64
        nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
            Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
            new Texture(Gdx.files.internal("Rocket2.png")),
            Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas);

        //enemigoBasico1 = new EnemigoBasico1(Gdx.graphics.getWidth()/2-50, 700, new Texture(Gdx.files.internal("fairy_red.png")), new Texture(Gdx.files.internal("thickrice.png")));
        EnemigoBasico2 enemigoBasico2 = new EnemigoBasico2(Gdx.graphics.getWidth()/2-150, 700, new Texture(Gdx.files.internal("fairy_blue.png")), new Texture(Gdx.files.internal("wave.png")));
        //enemigos.add(enemigoBasico1);
        enemigos.add(enemigoBasico2);
    }

    public Nave4 getNave(){return nave;}

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {

        if(juegoPausado) return;

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new PantallaPausa(game, this));
            gameMusic.pause();
            juegoPausado = true;
            return;
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dibujaEncabezado();

        nave.draw(batch, this);


        for(Enemigo e : enemigos){
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
                    if(e.estaDestruida()) {
                        enemigos.remove(j);
                        j--;
                        score += 300;
                        if(getScore() % 50 == 0) auxBalasExtra.aplicarPowerUp(this);
                        if(getScore() % 300 == 0) auxVidasExtra.aplicarPowerUp(this);
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
        for(int i = 0; i < proyectiles.size(); i++){
            Proyectil p =  proyectiles.get(i);
            p.update();
            p.draw(batch);
        }

        // Dibujar nave y verificar colisiones solo si no está destruida
        nave.draw(batch, this);
        for (int i = 0; i < proyectiles.size(); i++) {
            Proyectil p = proyectiles.get(i);
            p.draw(batch);

            // Verificar colisión solo si la nave no está en estado de vulnerabilidad
            if (!nave.estaHerido() && nave.checkCollision(p)) {
                proyectiles.remove(i);
                i--;
            }
        }

        // Verificar si la nave está destruida
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        // Verificar si el nivel está completo
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

    public boolean agregarProyectil(Proyectil pp){
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
    public void resize(int width, int height) {}

    @Override
    public void pause() {
        juegoPausado = true;
        if(nave != null){
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
        if(nave != null){
            nave.setPosition(posXNave, posYNave);
            nave.detenerMovimiento();
        }
        gameMusic.play();
        game.setScreen(new PantallaJuego(game, ronda, nave.getVidas(), score));
    }
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        this.explosionSound.dispose();
        this.gameMusic.dispose();
    }
}
