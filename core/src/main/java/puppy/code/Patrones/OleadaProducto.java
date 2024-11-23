package puppy.code.Patrones;

import puppy.code.Patrones.Enemigo;
import java.util.ArrayList;
import java.util.List;

public class OleadaProducto {
    private List<Enemigo> enemigos;
    private int nivel;

    public OleadaProducto() {
        this.enemigos = new ArrayList<>();
    }

    public void agregarEnemigo(Enemigo enemigo) {
        this.enemigos.add(enemigo);
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}
