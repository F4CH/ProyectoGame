package puppy.code.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Proyectiles.AtaqueEnemigo1;
import puppy.code.Proyectiles.Bullet;
import puppy.code.Proyectiles.Proyectil;

public class EnemigoBasico1 extends Enemigo {
    public EnemigoBasico1(int x, int y, float tiempoEspera){
        super(new Texture(Gdx.files.internal("thickrice.png")), tiempoEspera);
        this.vida = 5;
        this.hitbox_default = 30;
        this.speed_default = 2;
        this.timeSinceLastDirectionChange = 1.5f;
        this.intervaloCambioDireccion = 1.5f;
        this.intervaloDisparo = 20;

        spr = new Sprite(new Texture(Gdx.files.internal("fairy_red.png")));
        spr.setPosition(x, y);
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
        // Si es tiempo de disparar
        if (tiempoDisparo <= 0) {
            int numProyectiles = 6; // Número de proyectiles a disparar en un círculo
            int velocidadProyectil = 4; // Velocidad del proyectil

            // Genera los proyectiles en un patrón circular
            for (int i = 0; i < numProyectiles; i++) {
                float angulo = i * (360.0f / numProyectiles); // Ángulo espaciado uniformemente
                Proyectil ataque = new AtaqueEnemigo1(
                    spr.getX() + spr.getWidth() / 2, // Posición x inicial del proyectil
                    spr.getY() + spr.getHeight() / 2, // Posición y inicial del proyectil
                    velocidadProyectil, // Velocidad del proyectil
                    angulo, // Ángulo de disparo
                    txProyectil // Textura del proyectil
                );
                // Añade el proyectil a la pantalla de juego
                juego.agregarProyectil(ataque);
            }
            // Reinicia el tiempo para el próximo disparo
            tiempoDisparo = intervaloDisparo;
        }
        // Disminuye el tiempo para el próximo disparo
        if(tiempoDisparo > 0) tiempoDisparo--;
    }
}
