package puppy.code;

import puppy.code.Patrones.Enemigo;
import puppy.code.Patrones.OleadaConcretoBuilder;
import puppy.code.Patrones.OleadaDirector;

import java.util.List;

public class Oleadas {
    private int nivelOleada;

    public Oleadas() {
        this.nivelOleada = 1; // Inicia en la primera oleada
    }

    public List<Enemigo> generarOleada() {
        OleadaDirector director = new OleadaDirector();
        OleadaConcretoBuilder builder = new OleadaConcretoBuilder();

        director.setBuilder(builder);
        return director.construirOleada(nivelOleada).getEnemigos();
    }

    public void incrementarNivel() {
        nivelOleada++;
    }
}
