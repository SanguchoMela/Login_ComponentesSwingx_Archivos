import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class LoginUsuarios {
    public JPanel loginPanel;
    private JTextField usuario_in;
    private JButton logInButton;
    private JLabel usuario_label;
    private JLabel clave_label;
    private JLabel main_label;
    private JPasswordField password_in;
    private JButton registrarseButton;
    private JButton verButton;

    public LoginUsuarios() {
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //recoge datos de la interfaz
                String usuario = usuario_in.getText();
                String password = new String(password_in.getPassword());
                //verifica credenciales
                try {
                    veficarCredenciales(usuario,password);
                    if(veficarCredenciales(usuario,password) == false){
                        JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrectos!","Error",JOptionPane.ERROR_MESSAGE);
                        usuario_in.setText(null);
                        password_in.setText(null);
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //muestra la ventana de registro de usuarios
                mostrarVentanaRegistro();
            }
        });
        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char echoChar = password_in.getEchoChar();
                if(echoChar == '\u2022'){
                    password_in.setEchoChar((char)0);
                } else {
                    password_in.setEchoChar('\u2022');
                }
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("LoginUsuarios");
        frame.setContentPane(new LoginUsuarios().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(315,185);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    //metodo para mostrar ventana de inicio - bienvenida
    public void mostrarVentanaInicio(String nombreUsuario){
        JFrame frame = new JFrame("MuestraUsuario");
        MuestraUsuario muestraUsuario = new MuestraUsuario();
        muestraUsuario.setNombreUser(nombreUsuario);
        frame.setContentPane(muestraUsuario.mostrarUsuario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(200,100);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    //metodo para mostrar ventana de registro
    public void mostrarVentanaRegistro(){
        JFrame frame = new JFrame("RegistroUsuarios");
        try {
            frame.setContentPane(new RegistroUsuarios().registroPanel);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(345,185);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    //metodo de verificacion de credenciales
    private boolean veficarCredenciales (String usuario, String password) throws FileNotFoundException {
        //carga la lista de usuarios del archivo
        RegistroUsuarios registroUsuarios = new RegistroUsuarios();
        registroUsuarios.cargarUsuarios();

        Usuario usuarioVerificado = null;

        //bucle que recorre el registro y verifica
        for (Usuario user: registroUsuarios.getListaUsuarios()){
            if(user.getUsuario().equals(usuario) && user.getPassword().equals(password)){
                usuarioVerificado = user;
                mostrarVentanaInicio(user.getUsuario());
                //usuario y contraseña correctos
                return true;
            }
        }
        //usuario o contraseña incorrectos
        return false;
    }
}
