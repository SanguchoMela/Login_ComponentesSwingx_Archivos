import javax.swing.*;

public class MuestraUsuario {
    public JPanel mostrarUsuario;
    private JLabel inicio_label;
    private JLabel bienvenida_label;
    private JLabel nombreUser_set;

    //setear nombre de usuario en bienvenida
    public void setNombreUser(String nombreUser){
        nombreUser_set.setText(nombreUser);
    }

    //constructor vacio
    public MuestraUsuario(){}

    public static void main(String[] args) {
        JFrame frame = new JFrame("MuestraUsuario");
        frame.setContentPane(new MuestraUsuario().mostrarUsuario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(200,100);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}
