package puppy.code.Patrones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Patrones.Enemigo;
import puppy.code.Enemigos.EnemigoBasico1;
import puppy.code.Enemigos.EnemigoBasico2;
import puppy.code.Enemigos.EnemigoBasico3;
import puppy.code.Patrones.OleadaBuilder;
import puppy.code.Patrones.OleadaProducto;

import java.util.HashSet;

public class OleadaConcretoBuilder implements OleadaBuilder {
    private OleadaProducto producto;
    private HashSet<Integer> posicionesX;

    public OleadaConcretoBuilder() {
        this.producto = new OleadaProducto();
        this.posicionesX = new HashSet<>();
    }

    @Override
    public void configurarNivel(int nivel) {
        producto.setNivel(nivel);
    }

    @Override
    public void agregarEnemigoBasico1(int cantidad) {
        agregarEnemigos(cantidad, "Basico1");
    }

    @Override
    public void agregarEnemigoBasico2(int cantidad) {
        agregarEnemigos(cantidad, "Basico2");
    }

    @Override
    public void agregarEnemigoBasico3(int cantidad) {
        agregarEnemigos(cantidad, "Basico3");
    }

    private void agregarEnemigos(int cantidad, String tipoEnemigo) {
        for (int i = 0; i < cantidad; i++) {
            int x = calcularX();
            int y = Gdx.graphics.getHeight();
            float tiempoEspera = i * 1.5f;

            Enemigo enemigo = null;
            switch (tipoEnemigo) {
                case "Basico1":
                    enemigo = new EnemigoBasico1(x, y, tiempoEspera);
                    break;
                case "Basico2":
                    enemigo = new EnemigoBasico2(x, y, tiempoEspera);
                    break;
                case "Basico3":
                    enemigo = new EnemigoBasico3(x, y, tiempoEspera);
                    break;
            }
            if (enemigo != null) {
                producto.agregarEnemigo(enemigo);
            }
        }
    }

    private int calcularX() {
        int x;
        do {
            x = MathUtils.random(0, Gdx.graphics.getWidth());
        } while (posicionesX.contains(x));
        posicionesX.add(x);
        return x;
    }

    @Override
    public OleadaProducto getProducto() {
        return producto;
    }
}
