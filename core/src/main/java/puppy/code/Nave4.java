package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Patrones.EstrategiaDisparo;
import puppy.code.Proyectiles.Proyectil;

import java.util.List;


public class Nave4 {

    private static final int hitbox_default = 30;
    private static final int speed_default = 1;

    private boolean destruida;
    private int vidas;
    private float xVel;
    private float yVel;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido;
    private int tiempoVulnerable;
    private int tiempoVulnerableMax;
    private float hitboxReduction;
    private List<EstrategiaDisparo> powerUpsActivos;

    private DisparoBasico disparoBasico;
    private ControladorDisparo controladorDisparo;


    // Constructor
    public Nave4() {
        this.vidas = 3;
        this.destruida = false;
        this.herido = false;
        this.tiempoVulnerable = 0;
        this.tiempoVulnerableMax = 120;
        this.hitboxReduction = hitbox_default;
        this.disparoBasico = new DisparoBasico();
        this.controladorDisparo = new ControladorDisparo(disparoBasico);

        sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        this.soundBala = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        this.txBala = new Texture(Gdx.files.internal("Rocket2.png"));

        spr = new Sprite(new Texture(Gdx.files.internal("MainShip3.png")));
        spr.setPosition(Gdx.graphics.getWidth() / 2 - 50, 30);
        spr.setBounds(Gdx.graphics.getWidth() / 2 - 50, 30, 45, 45);
    }

    // Metodo que dibuja la nave
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        manejarVulnerabilidad();
        manejarMovimiento();
        manejarRebote();
        spr.draw(batch);
        manejarDisparo(juego);
    }
    // Metodo que maneja la vulnerabilidad de la nave
    public void manejarVulnerabilidad() {
        if (tiempoVulnerable > 0) {
            tiempoVulnerable--;
            herido = tiempoVulnerable > 0;
        }
        spr.setColor(herido ? 1 : 1, herido ? 0 : 1, 1, 1);
    }

    // Metodo que maneja el movimiento de la nave
    public void manejarMovimiento() {
        xVel = 0;
        yVel = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) xVel -= speed_default;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xVel += speed_default;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) yVel -= speed_default;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) yVel += speed_default;

        spr.setPosition(spr.getX() + xVel, spr.getY() + yVel);
    }

    // Metodo que maneja los rebotes con los extremos de la ventana
    private void manejarRebote() {
        float nuevoX = spr.getX() + xVel;
        float nuevoY = spr.getY() + yVel;
        nuevoX = MathUtils.clamp(nuevoX, 0, Gdx.graphics.getWidth() - spr.getWidth());
        nuevoY = MathUtils.clamp(nuevoY, 0, Gdx.graphics.getHeight() - spr.getHeight());
        spr.setPosition(nuevoX, nuevoY);
    }

    private void manejarDisparo(PantallaJuego juego) {
        controladorDisparo.disparar(juego, this);
    }


    // Metodo para comprobar las colisiones de la nave al ser impactada
    public boolean checkCollision(Proyectil p) {
        // Solo aplica daño si no está en estado de vulnerabilidad
        if (tiempoVulnerable <= 0 && p.getArea().overlaps(this.getHitbox()) || (vidas == 1 && p.getArea().overlaps(this.getHitbox()))) {
            if (vidas == 1) {
                vidas = 0;
                destruida = true;
                sonidoHerido.play();
                return true;
            }

            vidas--;
            herido = true;
            tiempoVulnerable = tiempoVulnerableMax; // Iniciar el contador de vulnerabilidad
            sonidoHerido.play();
            if (vidas <= 0) destruida = true;
            return true;
        }
        return false;
    }

    public boolean estaDestruido() {
        return !herido && destruida;
    }

    public boolean estaHerido() {
        return herido;
    }

    public int getVidas() {
        return vidas;
    }

    public int getX() {
        return (int) spr.getX();
    }

    public int getY() {
        return (int) spr.getY();
    }
    /*
    public void setVidas(int vidas2) {
        vidas = vidas2;
    }
     */

    public void incrementarVidas(int cantidad){
        vidas += cantidad;
    }

    // Metodo para detener el movimiento de la nave
    public void detenerMovimiento() {
        this.xVel = 0;
        this.yVel = 0;
    }

    // Metodo para settear la posicion de la nave
    public void setPosition(float x, float y) {
        spr.setPosition(x, y);
        detenerMovimiento();
    }

    // Metodo que devuelve la hitbox de la nave
    public Rectangle getHitbox() {
        // Obtener el centro del Sprite
        float centerX = spr.getX() + spr.getWidth() / 2;
        float centerY = spr.getY() + spr.getHeight() / 2;

        // Calcular las dimensiones reducidas de la hitbox
        float reducedWidth = spr.getWidth() - hitboxReduction;
        float reducedHeight = spr.getHeight() - hitboxReduction;

        // Calcular la esquina inferior izquierda de la hitbox centrada
        float hitboxX = centerX - reducedWidth / 2;
        float hitboxY = centerY - reducedHeight / 2;

        // Crear y devolver la nueva hitbox centrada y reducida
        return new Rectangle(hitboxX, hitboxY, reducedWidth, reducedHeight);
    }
    public void agregarPowerUp(EstrategiaDisparo powerUp) {
        powerUpsActivos.add(powerUp);
    }

    public Sprite getSprite() {
        return spr;
    }

    public Texture getTexture() {
        return txBala;
    }

    public void cambiarEstrategiaDisparo(EstrategiaDisparo nuevaEstrategia) {
        controladorDisparo.setEstrategia(nuevaEstrategia);
    }

    public void sonidoDisparo(){
        soundBala.play();
    }

    public void reestablecerDisparoBasico(){
        cambiarEstrategiaDisparo(disparoBasico);
    }

    public EstrategiaDisparo getEstrategia(){
        return controladorDisparo.getEstrategia();
    }
}

