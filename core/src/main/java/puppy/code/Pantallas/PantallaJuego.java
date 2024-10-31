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

    private Oleadas oleadas;

    private float tiempoEspera = 2.0f; // Tiempo de espera en segundos
    private float contadorTiempo = 0; // Contador para el tiempo transcurrido
    private boolean esperando = false; // Estado de espera

    private ArrayList<Enemigo> enemigos;
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
        fondoAnimado = game.getFondoAnimado();
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

        oleadas = new Oleadas();
        enemigos = new ArrayList<>(oleadas.generarOleada());

        // Inicializar el mapa de PowerUps
        powerUpMap = new HashMap<>();
        powerUpMap.put(50, new BalasExtra());
        powerUpMap.put(100, new BalasDiagonales());
        powerUpMap.put(300, new VidasExtra());
    }

    public Nave4 getNave() {
        return nave;
    }

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 200, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), (float) Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        comprobarPausa();
        iniciarComponentes(delta);
        dibujarEnemigos(delta);
        actualizarBalas();
        actualizarProyectiles();
        // BORRAR ESTO AL ACABAR EL TEST
        nave.draw(batch, this);
        //verificarAtaquesANave();
        verificarNaveDestruida();
        verificarFinRonda(delta);
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
        //fondoAnimado.dispose();
    }

    public void comprobarPausa(){
        if (juegoPausado) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaPausa(game, this));
            gameMusic.pause();
            juegoPausado = true;
            return;
        }
    }

    public void iniciarComponentes(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fondoAnimado.update(delta);
        batch.begin();
        fondoAnimado.draw(batch);
        dibujaEncabezado();
        nave.draw(batch, this);
    }

    public void dibujarEnemigos(float delta){
        for (Enemigo e : enemigos) {
            e.draw(batch, this, delta);
        }
    }

    public void actualizarBalas(){
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
    }

    public void actualizarProyectiles(){
        for (int i = 0; i < proyectiles.size(); i++) {
            Proyectil p = proyectiles.get(i);
            p.update();
            p.draw(batch);

            if (p.isDestroyed()) {
                proyectiles.remove(i);
                i--;
            }
        }
    }

    public void verificarAtaquesANave(){
        nave.draw(batch, this);
        for (int i = 0; i < proyectiles.size(); i++) {
            Proyectil p = proyectiles.get(i);
            p.draw(batch);

            if (!nave.estaHerido() && nave.checkCollision(p)) {
                proyectiles.remove(i);
                i--;
            }
        }
    }

    public void verificarNaveDestruida(){
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }

    public void verificarFinRonda(float delta){
        if (enemigos.isEmpty() && !esperando) {
            ronda++;
            oleadas.incrementarNivel(); // Incrementa el nivel de oleada en Oleadas
            esperando = true; // Inicia la espera
        }

        if (esperando) {
            contadorTiempo += delta; // Incrementa el contador con el tiempo transcurrido

            if (contadorTiempo >= tiempoEspera) {
                esperando = false; // Termina la espera
                contadorTiempo = 0; // Reinicia el contador
                enemigos = new ArrayList<>(oleadas.generarOleada()); // Genera una nueva oleada
            }
        }
    }

    public void aplicarPowerUpSiCorresponde() {
        if(score % 50 == 0){
            PowerUp balasExtra = powerUpMap.get(50);
            if(balasExtra != null){
                balasExtra.aplicarPowerUp(this);
            }
        }
        if(score % 100 == 0){
            PowerUp balasDiagonales = powerUpMap.get(100);
            if(balasDiagonales != null){
                balasDiagonales.aplicarPowerUp(this);
            }
        }
        if(score % 300 == 0){
            PowerUp vidasExtra = powerUpMap.get(300);
            if(vidasExtra != null){
                vidasExtra.aplicarPowerUp(this);
            }
        }
    }
}
