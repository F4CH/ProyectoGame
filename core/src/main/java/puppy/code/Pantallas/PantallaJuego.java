package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.Enemigos.Enemigo;
import puppy.code.Fondos.FondoAnimado;
import puppy.code.Nave4;
import puppy.code.Oleadas;
import puppy.code.PowerUps.BalasDiagonales;
import puppy.code.PowerUps.BalasExtra;
import puppy.code.PowerUps.PowerUp;
import puppy.code.PowerUps.VidasExtra;
import puppy.code.Proyectiles.Bullet;
import puppy.code.Proyectiles.Proyectil;
import puppy.code.SpaceNavigation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PantallaJuego implements Screen {
    private SpaceNavigation game; // Juego
    private OrthographicCamera camera;  // Camara
    private SpriteBatch batch;  // Batch
    private Music gameMusic; // Música del juego
    private int score; // Score del juego
    private int ronda; // Ronda del juego
    private boolean juegoPausado; // Boolean para verificar pausa
    private float posXNave; // Posición de la nave en el eje x
    private float posYNave; // Posición de la nave en el eje y
    private FondoAnimado fondoAnimado; // Fonde de la partida
    private Oleadas oleadas; // Clase oleada para controlar la generación de enemigos
    private float tiempoEspera = 2.0f; // Tiempo de espera en segundos
    private float contadorTiempo = 0; // Contador para el tiempo transcurrido
    private boolean esperando = false; // Boolean para verificar el estado de espera
    private ArrayList<Enemigo> enemigos; // Array de enemigos
    private ArrayList<Proyectil> proyectiles = new ArrayList<>(); // Array de ataques enemigos
    private Nave4 nave; // Nave del jugador
    private ArrayList<Bullet> balas = new ArrayList<>(); // Array de balas disparadas por el jugador
    private final Map<Integer, PowerUp> powerUpMap; // Mapa para guardar los PowerUps

    public PantallaJuego(SpaceNavigation game) {
        this.game = game; // Se asigna el juego recibido
        this.ronda = 1; // Se inicia en la ronda 1
        this.score = 0; // Se inicia el score en 0
        this.juegoPausado = false; // Se asigna la pausa en false
        batch = game.getBatch(); // Se asigna el batch
        fondoAnimado = game.getFondoAnimado(); // Se asigna el fondo
        camera = new OrthographicCamera(); // Se crea la camara
        camera.setToOrtho(false, 800, 640); // Se entregan parametros a la camara
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicGame.ogg")); // Se asigna la música del juego
        gameMusic.setLooping(true); // Se coloca la musica en loop
        gameMusic.setVolume(0.5f); // Se asigna el volumen
        gameMusic.play(); // Se inicia la música
        nave = new Nave4(); // Se inicializa la nave dle jugador
        oleadas = new Oleadas(); // Se inicializan las oleadas de enemigos
        enemigos = new ArrayList<>(oleadas.generarOleada()); // Se crea el array con los enemigos de la ronda
        // Inicializar el mapa de PowerUps junto con los Power Ups correspondientes
        powerUpMap = new HashMap<>();
        powerUpMap.put(50, new BalasExtra()); // PowerUp de balas extra
        powerUpMap.put(100, new BalasDiagonales()); // PowerUp de balas diagonales
        powerUpMap.put(300, new VidasExtra()); // PowerUp de vidas extras
    }

    @Override
    public void render(float delta) {
        comprobarPausa();
        iniciarComponentes(delta);
        dibujarEnemigos(delta);
        actualizarBalas();
        actualizarProyectiles();
        verificarAtaquesANave();
        verificarNaveDestruida();
        verificarFinRonda(delta);
        batch.end();
    }

    // Metodo que devuelve la nave del jugador
    public Nave4 getNave() {
        return nave;
    }

    // Metodo que dibuja el encabezado del juego
    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 200, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), (float) Gdx.graphics.getWidth() / 2 - 100, 30);
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
        game.setScreen(new PantallaJuego(game));
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        this.gameMusic.dispose();
    }

    public void comprobarPausa(){
        if (juegoPausado) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaPausa(game, this));
            gameMusic.pause();
            juegoPausado = true;
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
