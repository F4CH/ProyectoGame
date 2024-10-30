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
import puppy.code.PowerUps.BalasDiagonales;
import puppy.code.PowerUps.BalasExtra;
import puppy.code.PowerUps.PowerUp;
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
    private ShapeRenderer shapeRenderer;


    private EnemigoBasico1 enemigoBasico1;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private ArrayList<Proyectil> proyectiles = new ArrayList<>();

    private Nave4 nave;

    private ArrayList<Bullet> balas = new ArrayList<>();
    /*private BalasExtra auxBalasExtra = new BalasExtra();
    private VidasExtra auxVidasExtra = new VidasExtra();*/
    PowerUp powerup;
    public int getScore() {return score;}

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        shapeRenderer = new ShapeRenderer();
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

        enemigoBasico1 = new EnemigoBasico1(Gdx.graphics.getWidth()/2-50, 700, new Texture(Gdx.files.internal("fairy_red.png")));
        EnemigoBasico1 enemigoBasico2 = new EnemigoBasico1(Gdx.graphics.getWidth()/2-150, 700, new Texture(Gdx.files.internal("fairy_blue.png")));
        enemigos.add(enemigoBasico1);
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            game.setScreen(new PantallaPausa(game, this));
            gameMusic.pause();
            return;
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dibujaEncabezado();

        nave.draw(batch, this);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        Rectangle hitboxNave = nave.getHitbox();
        shapeRenderer.rect(hitboxNave.x, hitboxNave.y, hitboxNave.width, hitboxNave.height);

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
                        if(getScore() % 50 == 0){
                            powerup = new BalasExtra();
                            powerup.aplicarPowerUp(this);
                        }
                        if(getScore() % 100 == 0){
                            powerup = new BalasDiagonales();
                            powerup.aplicarPowerUp(this);
                        }
                        if(getScore() % 300 == 0) {
                            powerup = new VidasExtra();
                            powerup.aplicarPowerUp(this);
                        }
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
            AtaqueEnemigo1 p = (AtaqueEnemigo1) proyectiles.get(i);
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
        shapeRenderer.end();
    }

    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }

    public boolean agregarProyectil(Proyectil pp){
        return proyectiles.add(pp);
    }


    public void reanudarMusica() {
        gameMusic.play();
    }
    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        this.explosionSound.dispose();
        this.gameMusic.dispose();
    }


}
