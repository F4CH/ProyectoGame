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
        /*List<Enemigo> enemigos = new ArrayList<>();
        int numEnemigosBasico1 = Math.min(3 + nivelOleada / 2, 10);
        int numEnemigosBasico2 = Math.max(0, nivelOleada - 5) / 3;
        int numEnemigosBasico3 = Math.max(0, nivelOleada - 10) / 5;

        // Crea y añade enemigos de tipo básico 1
        for (int i = 0; i < numEnemigosBasico1; i++) {
            enemigos.add(new EnemigoBasico1(calcularX(), Gdx.graphics.getHeight(), i * 2));
        }

        // Crea y añade enemigos de tipo básico 2
        for (int i = 0; i < numEnemigosBasico2; i++) {
            enemigos.add(new EnemigoBasico2(calcularX(), Gdx.graphics.getHeight(), i * 2));
        }

        // Crea y añade enemigos de tipo básico 3
        for (int i = 0; i < numEnemigosBasico3; i++) {
            enemigos.add(new EnemigoBasico3(calcularX(), Gdx.graphics.getHeight(), i * 2));
        }

        return enemigos;*/
        List<Enemigo> enemigos = new ArrayList<>();
        int temporizador = 0;

        // Calcular el número de cada tipo de enemigo de forma progresiva y equilibrada
        int numEnemigosBasico1 = Math.min(3 + nivelOleada / 3, 10);  // Limitar el crecimiento a un máximo de 10
        int numEnemigosBasico2 = Math.min(1 + nivelOleada / 7, 5);   // El tipo 2 es el menos común y más difícil
        int numEnemigosBasico3 = Math.min(1 + nivelOleada / 5, 3);   // Se introduce lentamente

        // Variables para contar los enemigos generados de cada tipo
        int countBasico1 = 0;
        int countBasico2 = 0;
        int countBasico3 = 0;

        // Generar enemigos intercalando tipos
        while (countBasico1 < numEnemigosBasico1 || countBasico2 < numEnemigosBasico2 || countBasico3 < numEnemigosBasico3) {
            // Generar enemigo de tipo 1 si aún no se alcanzó el límite
            if (countBasico1 < numEnemigosBasico1) {
                enemigos.add(new EnemigoBasico1(calcularX(), Gdx.graphics.getHeight(), temporizador * 1.5f));
                countBasico1++;
                temporizador++;
            }

            // Generar enemigo de tipo 2 si aún no se alcanzó el límite
            if (countBasico2 < numEnemigosBasico2) {
                enemigos.add(new EnemigoBasico2(calcularX(), Gdx.graphics.getHeight(), temporizador * 2));
                countBasico2++;
                temporizador++;
            }

            // Generar enemigo de tipo 3 si aún no se alcanzó el límite
            if (countBasico3 < numEnemigosBasico3) {
                enemigos.add(new EnemigoBasico3(calcularX(), Gdx.graphics.getHeight(), temporizador * 2.5f));
                countBasico3++;
                temporizador++;
            }
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
