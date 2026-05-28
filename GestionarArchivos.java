import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public void guardarEnTxt(String rutaArchivo, String contenido, boolean adjuntar) {
    BufferedWriter writer = null;

    try {
        writer = new BufferedWriter(new FileWriter(rutaArchivo, adjuntar));
        writer.write(contenido);
        writer.newLine();

        System.out.println("Datos guardados exitosamente.");

    } catch (IOException e) {
        System.err.println("Ocurrió un error al escribir: " + e.getMessage());

    } finally {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Error al intentar cerrar el archivo: " + e.getMessage());
            }
        }
    }
}

public void leerYMostrarTxt(String rutaArchivo) {
        
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            
            System.out.println("--- Contenido del archivo ---");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("-----------------------------");
            
        } catch (IOException e) {
            System.err.println("Ocurrió un error al leer: " + e.getMessage());
            
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error al intentar cerrar el archivo: " + e.getMessage());
                }
            }
        }
    }