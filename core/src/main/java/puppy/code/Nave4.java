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

	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoDisparo = 0;
    private int intervaloDisparo = 10;
    private int tiempoVulnerable = 0;
    private int tiempoVulnerableMax = 120;
    private float hitboxReduction = 30f;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);
    }
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x = spr.getX();
        float y = spr.getY();
        int velocidad = 2;

        // Actualizar la vulnerabilidad
        if (tiempoVulnerable > 0) {
            tiempoVulnerable--; // Reducir el contador de vulnerabilidad
            herido = tiempoVulnerable > 0; // Herido solo mientras esté vulnerable
        }

        // Movimiento de la nave
        xVel = 0;
        yVel = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) xVel -= velocidad;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xVel += velocidad;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) yVel -= velocidad;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) yVel += velocidad;

        float nuevoX = x + xVel;
        float nuevoY = y + yVel;
        nuevoX = MathUtils.clamp(nuevoX, 0, Gdx.graphics.getWidth() - spr.getWidth());
        nuevoY = MathUtils.clamp(nuevoY, 0, Gdx.graphics.getHeight() - spr.getHeight());
        spr.setPosition(nuevoX, nuevoY);
        spr.draw(batch);

        // Disparo
        if (!herido && Gdx.input.isKeyPressed(Input.Keys.Z)){
            if(tiempoDisparo <= 0) {
                Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
                juego.agregarBala(bala);
                soundBala.play();
                tiempoDisparo = intervaloDisparo;
            }
        }
        if(tiempoDisparo > 0) tiempoDisparo--;

        if(herido){
            spr.setColor(1,0,0,1);
        }else{
            spr.setColor(1,1,1,1);
        }
    }

    public boolean checkCollision(Ball2 b) {
        // Solo aplica daño si no está en estado de vulnerabilidad
        if (tiempoVulnerable <= 0 && b.getArea().overlaps(this.getHitbox()) || (vidas == 1 && b.getArea().overlaps(this.getHitbox()))){
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
