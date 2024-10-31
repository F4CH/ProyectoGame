package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Enemigos.Enemigo;
import puppy.code.Enemigos.EnemigoBasico1;
import puppy.code.Enemigos.EnemigoBasico2;
import puppy.code.Enemigos.EnemigoBasico3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Oleadas {
    private int nivelOleada;
    private HashSet<Integer> posicionesX = new HashSet<>();

    public Oleadas() {
        nivelOleada = 1; // Inicia en la primera oleada
    }

    public List<Enemigo> generarOleada() {
        List<Enemigo> enemigos = new ArrayList<>();
        int numEnemigosBasico1 = Math.min(3 + nivelOleada / 2, 10);
        int numEnemigosBasico2 = Math.max(0, nivelOleada - 5) / 3;
        int numEnemigosBasico3 = Math.max(0, nivelOleada - 10) / 5;

        // Crea y añade enemigos de tipo básico 1
        for (int i = 0; i < numEnemigosBasico1; i++) {
            enemigos.add(new EnemigoBasico1(calcularX(), Gdx.graphics.getHeight(), i));
        }

        // Crea y añade enemigos de tipo básico 2
        for (int i = 0; i < numEnemigosBasico2; i++) {
            enemigos.add(new EnemigoBasico2(calcularX(), Gdx.graphics.getHeight(), i));
        }

        // Crea y añade enemigos de tipo básico 3
        for (int i = 0; i < numEnemigosBasico3; i++) {
            enemigos.add(new EnemigoBasico3(calcularX(), Gdx.graphics.getHeight(), i));
        }

        return enemigos;
    }

    public void incrementarNivel() {
        nivelOleada++;
    }

    private int calcularX(){
        int x;
        do{
            x = MathUtils.random(0, Gdx.graphics.getWidth());
        } while(posicionesX.contains(x));
        posicionesX.add(x);
        return x;
    }
}
