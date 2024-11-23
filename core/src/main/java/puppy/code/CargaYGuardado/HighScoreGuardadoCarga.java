package puppy.code.CargaYGuardado;

import java.io.*;

public class HighScoreGuardadoCarga {
    private static final String nombre_archivo = "highscore.txt";

    public static int cargarHighScore(){
        int highScore = 0;
        File file = new File(nombre_archivo);
        if(file.exists()){
            try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                String linea = reader.readLine();
                if (linea != null){
                    highScore = Integer.parseInt(linea.trim());
                }
            }catch (IOException | NumberFormatException e){
                e.printStackTrace();
            }
        }
        return highScore;
    }

    public static void guardarHighScore(int highScore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombre_archivo))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
