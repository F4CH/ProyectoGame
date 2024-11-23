package puppy.code.Patrones;

public interface OleadaBuilder {
    void configurarNivel(int nivel);
    void agregarEnemigoBasico1(int cantidad);
    void agregarEnemigoBasico2(int cantidad);
    void agregarEnemigoBasico3(int cantidad);
    OleadaProducto getProducto();
}
