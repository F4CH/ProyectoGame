package puppy.code.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Proyectiles.AtaqueEnemigo2;
import puppy.code.Proyectiles.Bullet;
import puppy.code.Proyectiles.Proyectil;

public class EnemigoBasico2 extends Enemigo {
    public EnemigoBasico2(int x, int y, float tiempoEspera){
        super(new Texture(Gdx.files.internal("fairy_circle_red.png")), tiempoEspera);
        this.vida = 4;
        this.hitbox_default = 30;
        this.speed_default = 2;
        this.timeSinceLastDirectionChange = 1.5f;
        this.intervaloCambioDireccion = 1.5f;
        this.intervaloDisparo = 20;

        spr = new Sprite(new Texture(Gdx.files.internal("fairy_blue.png")));
        spr.setPosition(x, y);
        spr.setY(Gdx.graphics.getHeight());
        spr.setBounds(x, y, 80, 80);
        this.hitboxReduction = hitbox_default;
    }

    @Override
    public void manejarMovimiento(float delta){
        // Incrementa el tiempo acumulado desde el último cambio de dirección
        timeSinceLastDirectionChange += delta;

        // Verifica si el intervalo de cambio de dirección ha sido alcanzado
        if (timeSinceLastDirectionChange >= intervaloCambioDireccion) {
            // Genera nuevas velocidades aleatorias para X y Y dentro de -speed_default a speed_default
            xVel = MathUtils.random(-1, 1) * speed_default;
            yVel = MathUtils.random(-1, 1) * speed_default;

            // Reinicia el contador de tiempo acumulado
            timeSinceLastDirectionChange = 0;
        }
    }

    @Override
    public void manejarDisparo(PantallaJuego juego){
        if (tiempoDisparo <= 0) {
            int numProyectiles = 6; // Número de proyectiles
            int velocidadProyectil = 6; // Velocidad del proyectil
            float deltaAngle = 0.5f; // Cambia según la velocidad del giro en espiral

            for (int i = 0; i < numProyectiles; i++) {
                float angulo = i * (360.0f / numProyectiles);
                Proyectil proyectil = new AtaqueEnemigo2(
                    spr.getX() + spr.getWidth() / 2, // Posición x
                    spr.getY() + spr.getHeight() / 2, // Posición y
                    velocidadProyectil,
                    angulo,
                    deltaAngle, // Incremento de ángulo para el espiral
                    txProyectil
                );
                juego.agregarProyectil(proyectil);
            }
            tiempoDisparo = intervaloDisparo;
        }
        if (tiempoDisparo > 0) tiempoDisparo--;
    }
}
