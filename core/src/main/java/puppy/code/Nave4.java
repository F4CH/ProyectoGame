package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Nave4 {

    //private static final int damage_default = 20;
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
    private int tiempoDisparo;
    private int intervaloDisparo;
    private int tiempoVulnerable;
    private int tiempoVulnerableMax;
    private float hitboxReduction;
    private boolean estadoBalasExtra = false;
    private int disparosPowerUp = 0;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        this.vidas = 3;
        this.destruida = false;
        this.herido = false;
        this.tiempoDisparo = 0;
        this.intervaloDisparo = 10;
        this.tiempoVulnerable = 0;
        this.tiempoVulnerableMax = 120;
        this.hitboxReduction = hitbox_default;

    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;

    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
    }
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        manejarVulnerabilidad();
        manejarMovimiento();
        manejarRebote();
        spr.draw(batch);
        manejarDisparo(juego);
    }

    public void setPowerUpDisparo(boolean estado){this.estadoBalasExtra = estado;}

    public boolean getPowerUpDisparo(){return this.estadoBalasExtra;}

    public void setDisparosPowerUp(int powerUp){this.disparosPowerUp = powerUp;}

    public int getDisparosPowerUp(){return this.disparosPowerUp;}

    public void manejarVulnerabilidad() {
        if (tiempoVulnerable > 0) {
            tiempoVulnerable--;
            herido = tiempoVulnerable > 0;
        }
        spr.setColor(herido ? 1 : 1, herido ? 0 : 1, 1 , 1);
    }

    public void manejarMovimiento() {
        xVel = 0;
        yVel = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) xVel -= speed_default;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xVel += speed_default;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) yVel -= speed_default;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) yVel += speed_default;

        spr.setPosition(spr.getX() + xVel, spr.getY() + yVel);
    }

    private void manejarRebote() {
        float nuevoX = spr.getX() + xVel;
        float nuevoY = spr.getY() + yVel;
        nuevoX = MathUtils.clamp(nuevoX, 0, Gdx.graphics.getWidth() - spr.getWidth());
        nuevoY = MathUtils.clamp(nuevoY, 0, Gdx.graphics.getHeight() - spr.getHeight());
        spr.setPosition(nuevoX, nuevoY);
    }

    private void manejarDisparo(PantallaJuego juego) {

        if(!herido && Gdx.input.isKeyPressed(Input.Keys.Z) && getPowerUpDisparo()){
            if(tiempoDisparo <= 0) {
                Bullet balaIzq = new Bullet(spr.getX() + spr.getWidth() / 2 - 25, spr.getY() + spr.getHeight() - 5,3, txBala);
                juego.agregarBala(balaIzq);
                Bullet balaDer = new Bullet(spr.getX() + spr.getWidth() / 2 + 15, spr.getY() + spr.getHeight() - 5,3, txBala);
                juego.agregarBala(balaDer);
                Bullet balaCen = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 3, txBala);
                juego.agregarBala(balaCen);
                tiempoDisparo = intervaloDisparo;
                soundBala.play();
                setDisparosPowerUp(getDisparosPowerUp() - 1);
                if(disparosPowerUp == 0) setPowerUpDisparo(false);
            }
        }

        if(!herido && Gdx.input.isKeyPressed(Input.Keys.Z)){
            if(tiempoDisparo <= 0) {
                Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5,3, txBala);
                juego.agregarBala(bala);
                soundBala.play();
                tiempoDisparo = intervaloDisparo;
            }
        }
        if(tiempoDisparo > 0) tiempoDisparo--;
    }

    public boolean checkCollision(Proyectil p) {
        // Solo aplica daño si no está en estado de vulnerabilidad
        if (tiempoVulnerable <= 0 && p.getArea().overlaps(this.getHitbox()) || (vidas == 1 && p.getArea().overlaps(this.getHitbox()))){
            if(vidas == 1){
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

    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}

    public void detenerMovimiento(){
        this.xVel = 0;
        this.yVel = 0;
    }

    public void setPosition(float x , float y){
        spr.setPosition(x, y);
        detenerMovimiento();
    }

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
}
