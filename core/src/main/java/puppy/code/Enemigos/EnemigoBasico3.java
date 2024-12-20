package puppy.code.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Patrones.Enemigo;
import puppy.code.Proyectiles.AtaqueEnemigo1;
import puppy.code.Proyectiles.Proyectil;

public class EnemigoBasico3 extends Enemigo {
    public EnemigoBasico3(int x, int y, float tiempoEspera) {
        super(new Texture(Gdx.files.internal("youhoming.png")), tiempoEspera);
        setVida(8);
        setHitboxDefault(30);
        setSpeedDefault(4);
        setTimeSinceLastDirectionChange(1.9f);
        setIntervaloCambioDireccion(1.9f);
        setIntervaloDisparo(90);

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("superfairy.png")));
        sprite.setPosition(x, y);
        sprite.setBounds(x, y, 80, 80);
        setSpr(sprite);
        setHitboxReduction(getHitboxDefault());
    }

    @Override
    public void manejarMovimiento(float delta) {
        setTimeSinceLastDirectionChange(getTimeSinceLastDirectionChange() + delta);

        if (getTimeSinceLastDirectionChange() >= getIntervaloCambioDireccion()) {
            setXVel(MathUtils.random(-1, 1) * getSpeedDefault());
            setYVel(MathUtils.random(-1, 1) * getSpeedDefault());
            setTimeSinceLastDirectionChange(0);
        }
    }

    @Override
    public void manejarDisparo(PantallaJuego juego) {
        if (getTiempoDisparo() <= 0) {
            int numProyectiles = 28;
            int velocidadProyectil = 4;

            for (int i = 0; i < numProyectiles; i++) {
                float angulo = i * (360.0f / numProyectiles);
                Proyectil ataque = new AtaqueEnemigo1(
                    getSpr().getX() + getSpr().getWidth() / 2,
                    getSpr().getY() + getSpr().getHeight() / 2,
                    velocidadProyectil,
                    angulo,
                    getTxProyectil()
                );
                juego.agregarProyectil(ataque);
            }
            setTiempoDisparo(getIntervaloDisparo());
        }
        if (getTiempoDisparo() > 0) setTiempoDisparo(getTiempoDisparo() - 1);
    }
}
