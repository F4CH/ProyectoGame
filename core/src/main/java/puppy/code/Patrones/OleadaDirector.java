package puppy.code.Patrones;

public class OleadaDirector {
    private OleadaBuilder builder;

    public void setBuilder(OleadaBuilder builder) {
        this.builder = builder;
    }

    public OleadaProducto construirOleada(int nivel) {
        builder.configurarNivel(nivel);

        // Calcular la cantidad total y distribuir entre tipos
        int totalEnemigos = Math.min(1 + nivel / 2, 7);
        int numBasico1 = Math.max(1, totalEnemigos / 3);
        int numBasico2 = Math.max(1, totalEnemigos / 3);
        int numBasico3 = totalEnemigos - numBasico1 - numBasico2;

        // Delegar al Builder
        builder.agregarEnemigoBasico1(numBasico1);
        builder.agregarEnemigoBasico2(numBasico2);
        builder.agregarEnemigoBasico3(numBasico3);

        return builder.getProducto();
    }
}
