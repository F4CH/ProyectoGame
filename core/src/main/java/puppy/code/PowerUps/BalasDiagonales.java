package puppy.code.PowerUps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import puppy.code.Nave4;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Patrones.EstrategiaDisparo;
import puppy.code.Proyectiles.Bullet;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.List;

public class BalasDiagonales implements EstrategiaDisparo {
    private int disparosRestantes;
    private int tiempoDisparo = 0;
    private int intervaloDisparo = 15;
    private int scoreDeActivacion = 300;

    public BalasDiagonales() {
        this.disparosRestantes = 60; // Número de disparos del power-up
    }

    @Override
    public void disparar(PantallaJuego juego, Nave4 nave) {
        // Validar si se puede disparar
        if (tiempoDisparo <= 0 && !nave.estaHerido() && Gdx.input.isKeyPressed(Input.Keys.Z)) {
            // Generar las balas extra
            List<Bullet> balasGeneradas = new ArrayList<>();

            if(disparosRestantes == 0) {
                nave.reestablecerDisparoBasico();
                refrescar();
            }

            if (disparosRestantes > 0) {
                Texture txBala = nave.getTexture();
                float x = nave.getSprite().getX() + nave.getSprite().getWidth() / 2;
                float y = nave.getSprite().getY() + nave.getSprite().getHeight();

                // Disparar dos balas adicionales a los lados
                balasGeneradas.add(new Bullet(x - 5, y, 0, 3, txBala));
                balasGeneradas.add(new Bullet(x - 25, y - 5, 0, 3, txBala));
                balasGeneradas.add(new Bullet(x + 15, y - 5, 0, 3, txBala));
                balasGeneradas.add(new Bullet(x - 25, y - 5, -5, 3, txBala));
                balasGeneradas.add(new Bullet(x + 15, y - 5, 5, 3, txBala));
                disparosRestantes--; // Reducir los disparos restantes
            }

            // Agregar todas las balas generadas al juego
            for (Bullet bala : balasGeneradas) {
                juego.agregarBala(bala);
            }

            // Reproducir el sonido de disparo
            nave.sonidoDisparo();

            // Establecer el tiempo de espera para el siguiente disparo
            tiempoDisparo = intervaloDisparo;
        }

        // Reducir el tiempo de espera entre disparos
        if (tiempoDisparo > 0) {
            tiempoDisparo--;
        }
    }

    @Override
    public int obtenerScore(){
        return scoreDeActivacion;
    }

    public void refrescar(){
        this.disparosRestantes = 60;
    }
}
