import java.io.*;
import java.net.*;

/**
 * Clase Cliente. 
 * Se encarga de generar los Procedimientos que usaremos para implementar 
 * los métodos de control usados para el Cliente de la aplicación.
 *
 * @author Odei
 * @version 18.03.2016
 */
public class Cliente {    
    /**
     * Variable entera estática usada para almacenar el número de tiradas 
     * que el Jugador lanzará durante la partida como Cliente.
     */
    protected static int tiradas;
    
    /**
     * Variable Socket estática usada para almacenar la Conexión que el
     * Cliente utilizará para enlazar con el Servidor durante la partida.
     */
    protected static Socket sCliente;    
    
    /**
     * Constructor de la Clase Cliente.
     * Genera e inicializa los elementos utilizados para gestionar la
     * ejecución del Jugador haciendo las veces de Cliente.
     */
    public Cliente() {
        tiradas = 3;                                                            // Asignamos inicialmente el número de tiradas a 3
        Cliente_Interfaz interfaz = new Cliente_Interfaz();                     // Lanzamos una Instancia de la Interfaz Gráfica para el Cliente
        Cliente_Interfaz.tfRecord.setText(Servidor.leerRecord());               // Obtenemos el Record actual y lo asignamos a su campo correspondiente
    }
    
   /**
     * Método usado para procesar la Jugada del Cliente conectandose con el 
     * Servidor y manteniéndolo operativo tantas veces como tiradas existan.
     * 
     * @param dadosJugador String: variable con la Tirada de Dados del Jugador
     */
    protected static void procesarJugada(String dadosJugador) {
        if (tiradas-- > 0) {                                                    // Mientras existan tiradas
            try {
                sCliente = new Socket(Servidor.HOST, Servidor.Puerto);          // Nos conectamos al Servidor por el puerto preestablecido
                OutputStream os = sCliente.getOutputStream();
                DataOutputStream flujo_salida = new DataOutputStream(os);
                flujo_salida.writeUTF(dadosJugador);                            // Y la mandamos mediante un flujo de salida la Tirada realizada
                int d1 = Integer.parseInt(dadosJugador.substring(0,1));
                int d2 = Integer.parseInt(dadosJugador.substring(2));           // capturamos los números obtenidos 
                Cliente_Interfaz.cambiarDados(1, d1, d2);                       // y cambiamos las imágenes de los dados a mostrar
                Cliente_Interfaz.taMensajes.setText(Cliente_Interfaz.taMensajes.
                    getText()+(3-tiradas)+" Tirada Jugador:  "+dadosJugador+"\n");
                Cliente_Interfaz.tfRecord.setText(Servidor.leerRecord());       // Actualizamos el Record por si ha cambiado
                Cliente_Interfaz.generarAudio();                                // y generamos un sonido para la tirada de Dados
                InputStream is = sCliente.getInputStream();
                DataInputStream flujo_entrada = new DataInputStream(is);
                String dadosBanca = flujo_entrada.readUTF();                    // Capturamos el flujo de datos enviado desde el Servidor con su tirada
                d1 = Integer.parseInt(dadosBanca.substring(0,1));
                d2 = Integer.parseInt(dadosBanca.substring(2)); 
                Cliente_Interfaz.cambiarDados(2, d1, d2);                       // y los mostramos en pantalla como en el caso anterior
                Cliente_Interfaz.taMensajes.setText(Cliente_Interfaz.taMensajes.
                    getText()+(3-tiradas)+" Tirada Banca: \t  "+dadosBanca+"\n");
                if (tiradas == 0) {                                             // Si no quedan tiradas
                    calcularGanador();                                          // calculamos quien es el ganador
                    sCliente.close();                                           // Cerramos el socket cuando no existan tiradas
                }
            } catch(IOException | NumberFormatException e) {                    // Capturamos cualquier excepción y la mostramos dado el caso
                if (e.getMessage().equals("Connection refused: connect")) {
                    Cliente_Interfaz.taMensajes.setText("El Servidor no está arrancado");
                } else {
                    Cliente_Interfaz.taMensajes.setText(e.getMessage());
                } tiradas++;                                                    // Si se da algún error reestablecemos la tirada para que no cuente
            }
        }
    }
    
    /**
     * Método usado para calcular quién es el ganador de la Partida (comparando
     * los puntos totales del Jugador y de la Banca) y mostrar un mensaje.
     */
    protected static void calcularGanador() {
        int ju = Integer.parseInt(Cliente_Interfaz.tfConJug.getText());         // Obtenemos puntuación final del Jugador
        int ba = Integer.parseInt(Cliente_Interfaz.tfConBan.getText());         // Obtenemos puntuación final de la Banca
        if (ju > ba) {                                                          // Si el Jugador tiene más puntos que la Banca
            Cliente_Interfaz.taMensajes.setText(Cliente_Interfaz.taMensajes.
                    getText()+"\n¡¡Enhorabuena has Ganado el Juego!!");         // mostramos su mensaje correspondiente
            comprobarRecord(ju);                                                // y comprobamos si hay un nuevo Record
        } else if (ju < ba) {                                                   // Si el Jugador no tiene más puntos que la Banca
            Cliente_Interfaz.taMensajes.setText(Cliente_Interfaz.taMensajes.
                    getText()+"\nLo Sentimos. Has Perdido el Juego");           // mostramos su mensaje correspondiente
        } else {                                                                // Y si el Jugador tiene los mismos puntos que la Banca
            Cliente_Interfaz.taMensajes.setText(Cliente_Interfaz.taMensajes.
                    getText()+"\nVaya Vaya. Has Empatado el Juego");            // mostramos su mensaje correspondiente
        }
    }
        
    /**
     * Método usado para comprobar si el Jugador ha batido el Record actual
     * comparando el valor valor recibido con el almacenado como Record.
     *
     * @param n int: variable con la puntuación final del Jugador
     */
    protected static void comprobarRecord(int n) {
        if (n > Integer.parseInt(Cliente_Interfaz.tfRecord.getText())) {        // Si el valor es mayor al actual
            try {
                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(Servidor.ruta + "record.txt"));          // Creamos/Sobrescribimos un fichero
                bw.write(String.valueOf(n));                                    // con el valor del Record nuevo y lo guardamos
                bw.flush();
                Cliente_Interfaz.taMensajes.setText(Cliente_Interfaz.taMensajes.
                        getText()+"\n¡¡Has Batido el Record con "+n+" Puntos!!"
                        + "\n¡¡Tu Puntuación quedará Registrada!!");
                Cliente_Interfaz.tfRecord.setText(n+"");                        // Mostrando un Aviso al Jugador
            } catch (IOException ex) {
                Cliente_Interfaz.taMensajes.setText(ex.getMessage());           // Capturando cualquier posible problema producido
            }
        }
    }
    
    /**
     * Método Principal de la Clase Cliente.
     * Lanza una Instancia del Programa llamando al Constructor.
     * 
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main( String[] args ) {
        Cliente cliente = new Cliente();                                        // Creamos una Instancia del Cliente
    }
}