import java.io.* ;
import java.net.* ;
import java.util.Random;

/**
 * Clase Servidor. 
 * Se encarga de generar los Procedimientos que usaremos para implementar 
 * los métodos de control usados para el Servidor de la aplicación.
 *
 * @author Odei
 * @version 18.03.2016
 */
public class Servidor extends Thread {   
    /**
     * Variable cadena usada para almacenar el Record al arrancar la aplicación.
     */
    protected static String record;
    
    /**
     * Variable entera usada para almacenar el Puerto de Conexión a la escucha.
     */
    protected static final int Puerto = 1800;
    
    /**
     * Variable de tipo cadena usada para almacenar el HOST de Conexión.
     */
    protected static final String HOST = "localhost";

    /**
     * Variable de tipo cadena usada para almacenar la Ruta del equipo 
     * Servidor donde esta almacenado el fichero que contiene el Record.
     */
    protected static final String ruta = "src/recursos/";
    
    /**
     * Constructor de la Clase Servidor.
     * Genera e inicializa los elementos utilizados para gestionar la
     * ejecución del Juego haciendo las veces de Servidor.
     */
    public Servidor() {
        Servidor_Interfaz interfaz = new Servidor_Interfaz();                   // Lanzamos una Instancia de la Interfaz Gráfica para el Servidor
        record = leerRecord();                                                  // y leemos el Record actual paracomprobar posteriormente si se ha batido
    }
    
    /**
     * Método que simula la lógica de la aplicación.
     * Realiza un bucle infinito donde se atienden las peticiones
     * enviadas desde los Clientes y se procesan las mismas.
     */
    @Override
    public void run(){
        try {
            ServerSocket skServidor = new ServerSocket(Puerto);                 // Iniciamos la escucha del servidor en el puerto preestablecido
            while (true) {                                                      // Repetimos indefinidamente la escucha de peticiones hasta que cerremos la Interfaz
                try (Socket sCliente = skServidor.accept()) {                   // Esperamos a que se conecte un cliente y creamos un nuevo socket para el mismo
                    String rec = leerRecord();
                    if (Integer.parseInt(rec) > Integer.parseInt(record)) {     // Si el Record actual es diferente al Record obtenido al arrancar la aplicación
                        Servidor_Interfaz.taMensajes.setText(Servidor_Interfaz.
                            taMensajes.getText()+"Nuevo Record: \t"+rec+"\n");  // mostramos un mensaje en la Interfaz del Servidor 
                        record = rec;                                           // y reasignamos de nuevo dicho Record
                    }
                    InputStream is = sCliente.getInputStream();                 // Obtenemos el flujo de entrada mandado por el Cliente
                    DataInputStream flujo_entrada = new DataInputStream(is);
                    String dadosJugador = flujo_entrada.readUTF();              // Y lo almacenamos para usarlo posteriormente
                    Servidor_Interfaz.taMensajes.setText(Servidor_Interfaz.
                        taMensajes.getText()+"Tirada Jugador: \t"+dadosJugador+"\n");
                    String dadosBanca = tirarDados();                           // Generamos una tirada para la anca (Servidor) 
                    OutputStream os = sCliente.getOutputStream();               // Creamos flujo de salida para enviar al cliente
                    DataOutputStream flujo_salida = new DataOutputStream(os);
                    flujo_salida.writeUTF(dadosBanca);                          // Y le enviamos el resultado de dicha tirada al Jugador
                    Servidor_Interfaz.taMensajes.setText(Servidor_Interfaz.
                        taMensajes.getText()+"Tirada Banca: \t"+dadosBanca+"\n");
                    sCliente.close();                                           // Cerramos el socket
                } catch(Exception e) {
                    Servidor_Interfaz.taMensajes.setText(e.getMessage());       // Capturando cualquier posible problema producido
                }
            }
        } catch(IOException e) {
            if (e.getMessage().equals("Address already in use: JVM_Bind")) {
                Servidor_Interfaz.taMensajes.setText("El Servidor ya está arrancado");
            } else {
                Servidor_Interfaz.taMensajes.setText(e.getMessage());
            }
        }
    }
    
    /**
     * Método usado para obtener el Record actual.
     * Devuelve una cadena tras leer el fichero que almacena el Record.
     *
     * @return aux String: cadena con el resultado obtenido tras leer el fichero
     */
    protected static String leerRecord() {
        String aux = "";
        try {
            BufferedReader br = 
                new BufferedReader(new FileReader(ruta + "record.txt"));        // Obtenemos los datos contenidos dentro del fichero donde se almancena el Record         
            aux = br.readLine();                                                // y leemos su contenido
        } catch (IOException ex) {
            Servidor_Interfaz.taMensajes.setText(ex.getMessage());
        }
        return aux;                                                             // devolviéndolo en forma de cadena
    }
    
    /**
     * Método usado para generar una tirada doble de Dados aleatoria.
     * Genera 2 números aleatorios entre y los formatea en forma de cadena.
     *
     * @return String: cadena formateada con los datos de las 2 tiradas
     */
    protected static String tirarDados() {
        int d1 = new Random().nextInt(6) + 1;                                   // Lanzamos los Dados generando números aleatorios entre 1 y 6 
        int d2 = new Random().nextInt(6) + 1;
        return d1+"*"+d2;                                                       // y devolvemos una cadena formateada con el resultado
    }
    
    /**
     * Método Principal de la Clase Servidor.
     * Lanza un Hilo del Programa llamando al Constructor.
     * 
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        new Servidor().start();        
    }
}