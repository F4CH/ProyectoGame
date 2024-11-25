package puppy.code.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Patrones.Enemigo;
import puppy.code.Proyectiles.AtaqueEnemigo2;
import puppy.code.Proyectiles.Proyectil;

public class EnemigoBasico2 extends Enemigo {
    public EnemigoBasico2(int x, int y, float tiempoEspera) {
        super(new Texture(Gdx.files.internal("fairy_circle_red.png")), tiempoEspera);
        setVida(5);
        setHitboxDefault(30);
        setSpeedDefault(3);
        setTimeSinceLastDirectionChange(1.6f);
        setIntervaloCambioDireccion(1.6f);
        setIntervaloDisparo(30);

        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("fairy_blue.png")));
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
            int numProyectiles = 6;
            int velocidadProyectil = 6;
            float deltaAngle = 0.5f;

            for (int i = 0; i < numProyectiles; i++) {
                float angulo = i * (360.0f / numProyectiles);
                Proyectil proyectil = new AtaqueEnemigo2(
                    getSpr().getX() + getSpr().getWidth() / 2,
                    getSpr().getY() + getSpr().getHeight() / 2,
                    velocidadProyectil,
                    angulo,
                    deltaAngle,
                    getTxProyectil()
                );
                juego.agregarProyectil(proyectil);
            }
            setTiempoDisparo(getIntervaloDisparo());
        }
        if (getTiempoDisparo() > 0) setTiempoDisparo(getTiempoDisparo() - 1);
    }
}
