
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class GestionarArchivos {

    private BufferedReader entrada;
    private PrintWriter salida;

    public void abrirArchivo(String rutaArchivo, String modo) {
        try {
            File archivo = new File(rutaArchivo);

            if (modo.equalsIgnoreCase("escribir")) {

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

    public void borrarLinea(String rutaArchivo, String lineaAEliminar) {
        File archivoOriginal = new File(rutaArchivo);
        File archivoTemporal = new File("data/temp.txt");
        boolean encontrado = false;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivoOriginal));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivoTemporal));
            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length < 2) partes = linea.trim().split("\\s{2,}");
                if (partes.length > 0 && partes[0].trim().equals(lineaAEliminar.trim())) {
                    encontrado = true;
                    continue;
                }
                bufferedWriter.write(linea);
                bufferedWriter.newLine();
            }

            if (encontrado) {
                System.out.println("Registro encontrado y eliminado del archivo.");
            } else {
                System.out.println("Registro no encontrado en el archivo.");
            }

            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }

        try {
            Files.delete(Path.of(rutaArchivo));
            Files.move(
                    archivoTemporal.toPath(),
                    archivoOriginal.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            System.err.println("Error al reemplazar el archivo: " + e.getMessage());
        }
    }

    public boolean actualizarLinea(String rutaArchivo, String lineaAActualizar, String nuevaLinea) {
        File archivoOriginal = new File(rutaArchivo);
        File archivoTemporal = new File("data/temp.txt");
        boolean encontrado = false;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(archivoOriginal));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivoTemporal));
            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length < 2) partes = linea.trim().split("\\s{2,}");
                if (partes.length > 0 && partes[0].trim().equals(lineaAActualizar.trim())) {
                    bufferedWriter.write(String.format("%-8s", partes[0].trim()) + "|" + nuevaLinea);
                    encontrado = true;
                } else {
                    bufferedWriter.write(linea);
                }
                bufferedWriter.newLine();
            }

            if (encontrado) {
                System.out.println("Registro encontrado y actualizado en el archivo.");
            } else {
                System.out.println("Registro no encontrado en el archivo.");
            }

            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }

        try {
            Files.delete(Path.of(rutaArchivo));
            Files.move(
                    archivoTemporal.toPath(),
                    archivoOriginal.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            System.err.println("Error al reemplazar el archivo: " + e.getMessage());
        }
        return encontrado;
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
            System.err.println(
                    "Error: El archivo no se ha abierto para lectura. Llama a abrirArchivo(ruta, 'leer') primero.");
        }
    }

    public void cerrarArchivo() {
        try {
            if (salida != null) {
                salida.close();
                salida = null;
            }
            if (entrada != null) {
                entrada.close();
                entrada = null;
            }
        } catch (IOException e) {
            System.err.println("Error al cerrar los flujos de archivos: " + e.getMessage());
        }
    }
}
