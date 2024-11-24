package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Patrones.EstrategiaDisparo;
import puppy.code.Proyectiles.Bullet;

import java.util.ArrayList;
import java.util.List;

public class DisparoBasico implements EstrategiaDisparo {
    private int tiempoDisparo = 0;
    private int intervaloDisparo = 15;
    private int scoreDeActivacion = 0;

    public DisparoBasico() {}

    @Override
    public void disparar(PantallaJuego juego, Nave4 nave) {
        if (tiempoDisparo <= 0 && !nave.estaHerido() && Gdx.input.isKeyPressed(Input.Keys.Z)) {
            // Generar las balas centrales por defecto
            List<Bullet> balasGeneradas = new ArrayList<>();
            balasGeneradas.add(new Bullet(nave.getSprite().getX() + nave.getSprite().getWidth() / 2 - 5, nave.getSprite().getY() + nave.getSprite().getHeight(), 0, 3, nave.getTexture()));

            // Agregar todas las balas al juego
            for (Bullet bala : balasGeneradas) {
                juego.agregarBala(bala);
            }

            // Reproducir el sonido de disparo
            nave.sonidoDisparo();

            // Establecer el tiempo de espera para el siguiente disparo
            tiempoDisparo = intervaloDisparo;
        }

        // Decrementar el tiempo de espera entre disparos
        if (tiempoDisparo > 0) {
            tiempoDisparo--;
        }
    }

    @Override
    public int obtenerScore(){
        return scoreDeActivacion;
    }
}
