import java.awt.Color;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase Cliente_Interfaz que extiende de JFrame e implementa ActionListener. 
 * Se encarga de generar la Interfaz que usaremos para implementar los métodos
 * de control usados para el Cliente de la aplicación.
 *
 * @author Odei
 * @version 18.03.2016
 */
public class Cliente_Interfaz extends JFrame implements ActionListener {
    /**
     * Variable usada para mostrar la imágen del primer dado del Jugador.
     */
    protected static JLabel dadoJug1;
    
    /**
     * Variable usada para mostrar la imágen del segundo dado del Jugador.
     */
    protected static JLabel dadoJug2;
    
    /**
     * Variable usada para mostrar la imágen del primer dado de la Banca.
     */
    protected static JLabel dadoBan1;
    
    /**
     * Variable usada para mostrar la imágen del segundo dado de la Banca.
     */
    protected static JLabel dadoBan2;
    
    /**
     * Variable usada para mostrar el contador de puntos del Jugador.
     */
    protected static TextField tfConJug;
    
    /**
     * Variable usada para mostrar el contador de puntos de la Banca.
     */
    protected static TextField tfConBan;
    
    /**
     * Variable usada para mostrar el Record actual de puntos del Juego.
     */
    protected static TextField tfRecord;
    
    /**
     * Variable usada para mostrar los eventos producidos durante la partida.
     */
    protected static TextArea taMensajes;
    
    /**
     * Variable botón usada para lanzar las tiradas que el Jugador realiza.
     */
    protected JButton btnTirar;
        
    /**
     * Constructor de la Interfaz Gráfica implementada para el Cliente.
     * Genera e inicializa la Interfaz y los elementos utilizados
     * para visualizar de forma interactiva la ejecución de la aplicación.
     */
    public Cliente_Interfaz() {
        JPanel panel = new JPanel(null);                                        // Creamos un panel para dibujar la interfaz gráfica
        JLabel lbTit = new JLabel("Jugador");                                   // Agregamos etiquetas, botones, y demás elementos a la Interfaz
        JLabel lbJug = new JLabel("Total Jugador");
        JLabel lbBan = new JLabel("Total Banca");
        JLabel lbRec = new JLabel("Record");
        dadoJug1 = new JLabel(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/dado1.gif")));     // Asignamos las imágenes iniciales de los Dados
        dadoJug2 = new JLabel(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/dado1.gif")));
        dadoBan1 = new JLabel(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/dado2.gif")));
        dadoBan2 = new JLabel(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/dado2.gif")));
        tfConJug = new TextField("0");
        tfConBan = new TextField("0");
        tfRecord = new TextField();
        taMensajes = new TextArea("Pulsa el Botón para Jugar");
        try {
            btnTirar = new JButton("");
            btnTirar.setIcon(new ImageIcon(ImageIO.read(getClass().
                    getResource("recursos/boton.png"))));                       // Le ponemos al botón una imágen de fondo
        } catch (IOException ex) { 
            taMensajes.setText(ex.getMessage()); 
        }      
        panel.add(lbTit).setBounds(147, 5, 80, 20);
        panel.add(dadoJug1).setBounds(00, 15, 100, 100);                        // Y posicionamos en el panel los elementos
        panel.add(dadoJug2).setBounds(50, 55, 100, 100);
        panel.add(dadoBan1).setBounds(195, 15, 100, 100);
        panel.add(dadoBan2).setBounds(245, 55, 100, 100);
        panel.add(lbRec).setBounds(150, 35, 80, 20);
        panel.add(tfRecord).setBounds(151, 55, 40, 20);
        panel.add(btnTirar).setBounds(140, 80, 65, 65);
        panel.add(lbJug).setBounds(25, 155, 80, 20);
        panel.add(tfConJug).setBounds(35, 180, 60, 20);
        panel.add(lbBan).setBounds(240, 155, 80, 20);
        panel.add(tfConBan).setBounds(245, 180, 60, 20);
        panel.add(taMensajes).setBounds(35, 210, 270, 80);
        lbTit.setForeground(Color.blue);
        lbJug.setForeground(Color.blue);                                        // Cambiamos el color del texto de algunos recursos
        lbBan.setForeground(Color.black);
        lbRec.setForeground(Color.red);
        btnTirar.addActionListener((ActionListener) this);
        tfConJug.setEnabled(false);                                             // Imposibilitamos la edición de los campos de texto y demás
        tfConBan.setEnabled(false);
        tfRecord.setEnabled(false);
        taMensajes.setEditable(false);

        JFrame frame = new JFrame("Juego de Dados");                            // Creamos JFrame y le ponemos título
        frame.add(panel);                                                       // agregando el panel previamente creado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
                Cliente.class.getResource("recursos/client.png")));             // Le ponemos una imágen de icono a la ventana
        frame.setSize(350, 340);                                                // y le asignamos tamaño y demás parámetros
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
 
   /**
     * Creamos un escuchador de eventos para capturar las opciones
     * utilizadas durante la ejecución de la Interfaz.
     * 
     * @param evt ActionEvent: evento lanzado por el Jugador
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if(evt.getSource()instanceof JButton) {                                 // Si el evento es de tipo JButton
            if (Cliente.tiradas > 0) {                                          // y el Cliente tiene tiradas
                if(taMensajes.getText().equals("Pulsa el Botón para Jugar") || 
                   taMensajes.getText().equals("El Servidor no está arrancado")){ 
                    taMensajes.setText("");                                     // vaciamos el contenedor de mensajes si contiene el mensaje inicial
                }
                Cliente.procesarJugada(Servidor.tirarDados());                  // y realizamos una jugada tirando los dados y procesando los resultados
            } else {
                 taMensajes.setText("No quedan más tiradas");                   // Si no quedan tiradas mostramos un mensaje
            }
        } //else if(evt.getActionCommand()=="") { }
    }
        
   /**
     * Método usado para cambiar las imágenes que simulan el lanzamiento
     * de los Dados y su resultado obtenido.
     * 
     * @param valor int: variable usada para diferenciar que Dados cambiar
     * @param d1 int: variable con la puntuación del Dado 1 a mostrar
     * @param d2 int: variable con la puntuación del Dado 2 a mostrar
     */
    protected static void cambiarDados(int valor, int d1, int d2) {
        if (valor == 1) {                                                       // Si el valor es 1 cambiamos los Dados del Jugador
            dadoJug1.setIcon(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/jug/"+d1+".png")));// asignando una imágen correspondiente al valor contenido en las variables
            dadoJug2.setIcon(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/jug/"+d2+".png")));
            tfConJug.setText((Integer.parseInt(tfConJug.getText())+d1+d2)+"");  // y asignamos su correspondiente suma en el campo especificado para ello
        } else {                                                                // Si el valor es diferente de 1 cambiamos los Dados de la Banca
            dadoBan1.setIcon(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/ban/"+d1+".png")));
            dadoBan2.setIcon(new ImageIcon(
                Cliente_Interfaz.class.getResource("recursos/ban/"+d2+".png")));
            tfConBan.setText((Integer.parseInt(tfConBan.getText())+d1+d2)+"");
        }
    }
    
    /**
     * Método usado para generar un Sonido que simula el lanzamiento de 
     * Dados que se produce cada vez que un Jugador pulsa el botón.
     */
    protected static void generarAudio() {
        try {
            Clip audio = AudioSystem.getClip();                                 // Creamos un objeto Clip para reproducir 
            audio.open(AudioSystem.getAudioInputStream(
                    Cliente_Interfaz.class.getResource("recursos/tirada.wav")));// un audio cada vez que se realiza una tirada
            audio.start();                                                      // y lo ejecutamos una vez
        }catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
            Cliente_Interfaz.taMensajes.setText(e.toString());                  // Capturando cualquier posible problema producido
        }
    }
}