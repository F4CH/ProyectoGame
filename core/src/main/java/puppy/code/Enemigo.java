package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemigo {
    protected int hitbox_default;
    protected int speed_default;
    protected boolean destruida;
    protected int vida;
    protected float xVel;
    protected float yVel;
    protected Sprite spr;
    protected Texture txProyectil;
    protected int tiempoDisparo;
    protected int intervaloDisparo;
    protected float hitboxReduction;
    protected float timeSinceLastDirectionChange;
    protected float intervaloCambioDireccion;

    public Enemigo(int x, int y, Texture tx){
        this.destruida = false;
        this.tiempoDisparo = 0;
        this.intervaloDisparo = 0;
        this.timeSinceLastDirectionChange = 0;

        this.txProyectil = new Texture(Gdx.files.internal("thickrice.png"));

        spr = new Sprite(tx);
        spr.setPosition(x, y);
    }

    public void draw(SpriteBatch batch, PantallaJuego juego, float delta){
        manejarMovimiento(delta);
        manejarRebote();
        spr.draw(batch);
        manejarDisparo(juego);
    }

    public abstract void manejarMovimiento(float delta);

    public abstract void manejarRebote();

    public abstract void manejarDisparo(PantallaJuego juego);

    public abstract boolean checkCollision(Bullet b);

    public boolean estaDestruida(){
        return destruida;
    }

    public int getVida() {return vida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
    public void setVida(int vida2) {vida = vida2;}

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
