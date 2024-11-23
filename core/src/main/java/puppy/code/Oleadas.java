package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Patrones.Enemigo;
import puppy.code.Enemigos.EnemigoBasico1;
import puppy.code.Enemigos.EnemigoBasico2;
import puppy.code.Enemigos.EnemigoBasico3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Oleadas {
    private int nivelOleada;
    private HashSet<Integer> posicionesX = new HashSet<>();

    // Constructor
    public Oleadas() {
        nivelOleada = 1; // Inicia en la primera oleada
    }

    public List<Enemigo> generarOleada() {
        List<Enemigo> enemigos = new ArrayList<>();
        int totalEnemigos = Math.min(1 + nivelOleada / 2, 7);
        int numBasico1 = Math.max(1, totalEnemigos / 3);
        int numBasico2 = Math.max(1, totalEnemigos / 3);
        int numBasico3 = totalEnemigos - numBasico1 - numBasico2;

        int temporizador = 0;
        int contadorBasico1 = 0, contadorBasico2 = 0, contadorBasico3 = 0;

        // Agregar enemigos de manera intercalada hasta completar la cantidad de enemigos de cada tipo
        while (contadorBasico1 < numBasico1 || contadorBasico2 < numBasico2 || contadorBasico3 < numBasico3) {
            if (contadorBasico1 < numBasico1) {
                enemigos.add(new EnemigoBasico1(calcularX(), Gdx.graphics.getHeight(), temporizador * 1.5f));
                contadorBasico1++;
                temporizador++;
            }
            if (contadorBasico2 < numBasico2) {
                enemigos.add(new EnemigoBasico2(calcularX(), Gdx.graphics.getHeight(), temporizador * 1.5f));
                contadorBasico2++;
                temporizador++;
            }
            if (contadorBasico3 < numBasico3) {
                enemigos.add(new EnemigoBasico3(calcularX(), Gdx.graphics.getHeight(), temporizador * 1.5f));
                contadorBasico3++;
                temporizador++;
            }
        }
        // Se retornan los enemigos
        return enemigos;
    }

    // Metodo que aumenta el nivel de la oleada
    public void incrementarNivel() {
        nivelOleada++;
    }

    // Metodo que calcula la posicion X de los enemigos
    private int calcularX(){
        int x;
        do{
            x = MathUtils.random(0, Gdx.graphics.getWidth());
        } while(posicionesX.contains(x));
        posicionesX.add(x);
        return x;
    }
}
