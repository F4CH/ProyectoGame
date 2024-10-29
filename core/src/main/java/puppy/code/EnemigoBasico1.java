package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class EnemigoBasico1 extends Enemigo{
    public EnemigoBasico1(int x, int y, Texture tx){
        super(x, y, tx);
        this.hitbox_default = 30;
        this.speed_default = 2;
        this.intervaloCambioDireccion = 1.5f;
        this.intervaloDisparo = 20;

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

        // Calcula las nuevas posiciones basadas en la velocidad actual
        float nuevoX = spr.getX() + xVel;
        float nuevoY = spr.getY() + yVel;

        // Limita la posición a la mitad superior de la pantalla
        float alturaLimiteSuperior = Gdx.graphics.getHeight() * 0.75f;

        // Asegura que el enemigo se mantenga dentro de los límites de la pantalla en el eje X
        if (nuevoX < 0) nuevoX = 0;
        if (nuevoX > Gdx.graphics.getWidth() - spr.getWidth()) nuevoX = Gdx.graphics.getWidth() - spr.getWidth();

        // Asegura que el enemigo se mantenga dentro de los límites de la mitad superior en el eje Y
        if (nuevoY < alturaLimiteSuperior) nuevoY = alturaLimiteSuperior;
        if (nuevoY > Gdx.graphics.getHeight() - spr.getHeight()) nuevoY = Gdx.graphics.getHeight() - spr.getHeight();

        // Actualiza la posición del sprite
        spr.setPosition(nuevoX, nuevoY);
    }

    @Override
    public void manejarRebote(){

    }

    @Override
    public void manejarDisparo(PantallaJuego juego){
        // Si es tiempo de disparar
        if (tiempoDisparo <= 0) {
            int numProyectiles = 8; // Número de proyectiles a disparar en un círculo
            int velocidadProyectil = 4; // Velocidad del proyectil

            // Genera los proyectiles en un patrón circular
            for (int i = 0; i < numProyectiles; i++) {
                float angulo = i * (360.0f / numProyectiles); // Ángulo espaciado uniformemente
                Proyectil proyectil = new Proyectil(
                    spr.getX() + spr.getWidth() / 2, // Posición x inicial del proyectil
                    spr.getY() + spr.getHeight() / 2, // Posición y inicial del proyectil
                    velocidadProyectil,
                    angulo, // Ángulo de disparo
                    txProyectil // Textura del proyectil
                );

                // Añade el proyectil a la pantalla de juego
                juego.agregarProyectil(proyectil);
            }

            // Reproduce el sonido de disparo (si es necesario)
            // A PROGRAMAR soundDisparo.play();

            // Reinicia el tiempo para el próximo disparo
            tiempoDisparo = intervaloDisparo;
        }

        // Disminuye el tiempo para el próximo disparo
        //tiempoDisparo -= Gdx.graphics.getDeltaTime();
        if(tiempoDisparo > 0) tiempoDisparo--;
    }
}
