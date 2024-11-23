package puppy.code.Patrones;
//THEMPLATE METHOD
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Proyectiles.Bullet;

public abstract class Enemigo {
    private int hitbox_default;
    private int speed_default;
    private boolean destruida;
    private int vida;
    private float xVel;
    private float yVel;
    private Sprite spr;
    private Texture txProyectil;
    private int tiempoDisparo;
    private int intervaloDisparo;
    private float hitboxReduction;
    private float timeSinceLastDirectionChange;
    private float intervaloCambioDireccion;

    private float tiempoEspera;
    private float contadorEspera;
    private boolean apareciendo;

    private float alturaInicial;
    private float velocidadEntrada;
    private boolean enMovimiento;

    public Enemigo(Texture txAtaque, float tiempoEspera) {
        this.destruida = false;
        this.tiempoDisparo = 0;
        this.intervaloDisparo = 0;

        this.txProyectil = txAtaque;

        this.tiempoEspera = tiempoEspera;
        this.contadorEspera = 0;
        this.apareciendo = true;

        this.alturaInicial = Gdx.graphics.getHeight();
        this.velocidadEntrada = 550;
    }

    // Método plantilla
    public final void draw(SpriteBatch batch, PantallaJuego juego, float delta) {
        if (isApareciendo()) {
            manejarAparicion(delta);
            if (isApareciendo()) return;
        }

        if (isEnMovimiento()) {
            manejarEntrada(delta, batch);
        } else {
            manejarMovimiento(delta);
            manejarRebote();
            getSpr().draw(batch);
            manejarDisparo(juego);
        }
    }

    // Métodos concretos comunes
    private void manejarAparicion(float delta) {
        setContadorEspera(getContadorEspera() + delta);

        if (getContadorEspera() >= getTiempoEspera()) {
            setApareciendo(false);
            setContadorEspera(0);
            setEnMovimiento(true);
        }
    }

    private void manejarEntrada(float delta, SpriteBatch batch) {
        float posicionYFinal = 600;
        getSpr().setY(getSpr().getY() - getVelocidadEntrada() * delta);
        getSpr().draw(batch);

        if (getSpr().getY() <= posicionYFinal) {
            getSpr().setY(posicionYFinal);
            setEnMovimiento(false);
        }
    }

    protected void manejarRebote() {
        float nuevoX = getSpr().getX() + getXVel();
        float nuevoY = getSpr().getY() + getYVel();

        float alturaLimiteSuperior = Gdx.graphics.getHeight() * 0.75f;

        if (nuevoX < 0) nuevoX = 0;
        if (nuevoX > Gdx.graphics.getWidth() - getSpr().getWidth())
            nuevoX = Gdx.graphics.getWidth() - getSpr().getWidth();

        if (nuevoY < alturaLimiteSuperior) nuevoY = alturaLimiteSuperior;
        if (nuevoY > Gdx.graphics.getHeight() - getSpr().getHeight())
            nuevoY = Gdx.graphics.getHeight() - getSpr().getHeight();

        getSpr().setPosition(nuevoX, nuevoY);
    }

    public boolean checkCollision(Bullet b) {
        if (isEnMovimiento()) return false;
        if (b.getArea().overlaps(this.getHitbox())) {
            setVida(getVida() - 1);
            if (getVida() <= 0) setDestruida(true);
            return true;
        }
        return false;
    }

    public Rectangle getHitbox() {
        float centerX = getSpr().getX() + getSpr().getWidth() / 2;
        float centerY = getSpr().getY() + getSpr().getHeight() / 2;

        float reducedWidth = getSpr().getWidth() - getHitboxReduction();
        float reducedHeight = getSpr().getHeight() - getHitboxReduction();

        float hitboxX = centerX - reducedWidth / 2;
        float hitboxY = centerY - reducedHeight / 2;

        return new Rectangle(hitboxX, hitboxY, reducedWidth, reducedHeight);
    }

    // Métodos abstractos que definen el comportamiento específico de cada enemigo
    protected abstract void manejarMovimiento(float delta);

    protected abstract void manejarDisparo(PantallaJuego juego);

    // Getters y Setters (sin modificaciones)
    public int getHitboxDefault() { return hitbox_default; }
    public void setHitboxDefault(int hitbox_default) { this.hitbox_default = hitbox_default; }
    public int getSpeedDefault() { return speed_default; }
    public void setSpeedDefault(int speed_default) { this.speed_default = speed_default; }
    public boolean isDestruida() { return destruida; }
    public void setDestruida(boolean destruida) { this.destruida = destruida; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
    public float getXVel() { return xVel; }
    public void setXVel(float xVel) { this.xVel = xVel; }
    public float getYVel() { return yVel; }
    public void setYVel(float yVel) { this.yVel = yVel; }
    public Sprite getSpr() { return spr; }
    public void setSpr(Sprite spr) { this.spr = spr; }
    public Texture getTxProyectil() { return txProyectil; }
    public void setTxProyectil(Texture txProyectil) { this.txProyectil = txProyectil; }
    public int getTiempoDisparo() { return tiempoDisparo; }
    public void setTiempoDisparo(int tiempoDisparo) { this.tiempoDisparo = tiempoDisparo; }
    public int getIntervaloDisparo() { return intervaloDisparo; }
    public void setIntervaloDisparo(int intervaloDisparo) { this.intervaloDisparo = intervaloDisparo; }
    public float getHitboxReduction() { return hitboxReduction; }
    public void setHitboxReduction(float hitboxReduction) { this.hitboxReduction = hitboxReduction; }
    public float getTimeSinceLastDirectionChange() { return timeSinceLastDirectionChange; }
    public void setTimeSinceLastDirectionChange(float timeSinceLastDirectionChange) { this.timeSinceLastDirectionChange = timeSinceLastDirectionChange; }
    public float getIntervaloCambioDireccion() { return intervaloCambioDireccion; }
    public void setIntervaloCambioDireccion(float intervaloCambioDireccion) { this.intervaloCambioDireccion = intervaloCambioDireccion; }
    public float getTiempoEspera() { return tiempoEspera; }
    public void setTiempoEspera(float tiempoEspera) { this.tiempoEspera = tiempoEspera; }
    public float getContadorEspera() { return contadorEspera; }
    public void setContadorEspera(float contadorEspera) { this.contadorEspera = contadorEspera; }
    public boolean isApareciendo() { return apareciendo; }
    public void setApareciendo(boolean apareciendo) { this.apareciendo = apareciendo; }
    public float getAlturaInicial() { return alturaInicial; }
    public void setAlturaInicial(float alturaInicial) { this.alturaInicial = alturaInicial; }
    public float getVelocidadEntrada() { return velocidadEntrada; }
    public void setVelocidadEntrada(float velocidadEntrada) { this.velocidadEntrada = velocidadEntrada; }
    public boolean isEnMovimiento() { return enMovimiento; }
    public void setEnMovimiento(boolean enMovimiento) { this.enMovimiento = enMovimiento; }
}
