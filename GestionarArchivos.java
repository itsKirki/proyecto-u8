import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GestionarArchivos {
    private BufferedReader entrada;
    private PrintWriter salida;

    public void abrirArchivo(String rutaArchivo, String modo) {
        try {
            File archivo = new File(rutaArchivo);

            if (modo.equalsIgnoreCase("escribir")) {

                if (archivo.getParentFile() != null) {
                    archivo.getParentFile().mkdirs();
                }

                if (!archivo.exists()) {
                    archivo.createNewFile();
                }

                salida = new PrintWriter(new FileWriter(archivo, true));

            } else if (modo.equalsIgnoreCase("leer")) {
                if (!archivo.exists()) {
                    System.out.println("Error: El archivo que intentas leer no existe.");
                    return;
                }
                entrada = new BufferedReader(new FileReader(archivo));
            }
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    public void agregarLinea(String lineaDeTexto) {
        if (salida != null) {
            salida.println(lineaDeTexto);
            System.out.println("Registro guardado en el archivo exitosamente.");
        } else {
            System.err.println("Error: El archivo no se ha abierto. Llama a abrirArchivo() primero.");
        }
    }

    public void imprimirArchivo() {
        if (entrada != null) { 
            try {
                String linea = entrada.readLine();
                while (linea != null) {
                    System.out.println(linea);
                    linea = entrada.readLine();
                }
            } catch (IOException e) { 
                System.err.println("Error al imprimir el archivo: " + e.getMessage());
            }
        } else {
            System.err.println("Error: El archivo no se ha abierto para lectura. Llama a abrirArchivo(ruta, 'leer') primero.");
        }
    }

    public void cerrarArchivo() {
        if (salida != null) {
            salida.close();
            System.out.println("Archivo cerrado correctamente.");
        }
    }
}