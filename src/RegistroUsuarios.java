import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RegistroUsuarios {
    public JPanel registroPanel;
    private JLabel registro_label;
    private JTextField usuario_in;
    private JPasswordField password_in;
    private JLabel nombreUsuario_label;
    private JLabel password_label;
    private JButton guardarButton;
    private JButton verButton;
    private JButton iniciarSesiónButton;
    //archivo definido
    public String filePath = "usuarios.dat";
    //array de usuarios
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    //para acceder al array desde otra clase
    public ArrayList<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }

    public RegistroUsuarios() throws FileNotFoundException {
        //carga los datos al archivo para que no se borren los ya registrados
        cargarUsuarios();

        //accion del boton mostrar para ver y ocultar la contraseña
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
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuario_in.getText();
                //transforma a String el JPasswordField que por defecto es de tipo char
                String password = new String(password_in.getPassword());

                //verificar si un usuario ya existe
                boolean usuarioExistente = false;
                for(Usuario user:listaUsuarios){
                    if(user.getUsuario().equals(usuario)){
                        usuarioExistente = true;
                        break;
                    }
                }
                //si el usuario si existe
                if(usuarioExistente){
                    JOptionPane.showMessageDialog(null,"El nombre de usuario ya existe!","Usuario existente",JOptionPane.ERROR_MESSAGE);
                }
                //si el usuario no existe
                else{
                    //guarda datos en cada objeto del arreglo
                    Usuario nuevoUsuario = new Usuario(usuario,password);
                    //guarda cada objeto en el ArrayList
                    listaUsuarios.add(nuevoUsuario);
                    //escritura del objeto en el archivo
                    try(
                            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))
                    ){
                        objectOut.writeObject(listaUsuarios);
                        JOptionPane.showMessageDialog(null,"Usuario registrado correctamente!","Usuario registrado",JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,"Error al registrar usuario!","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }

                //borrar campos
                usuario_in.setText(null);
                password_in.setText(null);

                //carga y muestra todos los usuarios registrados (no necesario)
                //cargarUsuarios();
            }
        });
        //muestra la ventana de Login de usuario
        iniciarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarLogin();
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("RegistroUsuarios");
        frame.setContentPane(new RegistroUsuarios().registroPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(345,185);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    //metodo para mostrar ventana de login
    public void mostrarLogin(){
        JFrame frame = new JFrame("LoginUsuarios");
        frame.setContentPane(new LoginUsuarios().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(315,185);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    //metodo para cargar datos en el archivo
    public void cargarUsuarios(){
        //archivo como objeto de la clase File
        File file = new File(filePath);
        //si el archivo existe lo lee y muestra lo ya registrado
        if(file.exists()){
            try(
                    ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filePath))
            ){
                listaUsuarios = (ArrayList<Usuario>) objectIn.readObject();

                //System.out.println("\t--Usuarios registrados: "+listaUsuarios.size()); //numero de usuario registrados
                //recorre y muestra valores del arreglo - no necesario
                /*for(Usuario usuario:listaUsuarios){
                    System.out.println(usuario.toString());
                }*/

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Error al cargar");
            }
        }
        //si el archivo no existe crea uno nuevo
        else{
            try(
                    ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))
            ){
                listaUsuarios = new ArrayList<>();
                objectOut.writeObject(listaUsuarios);
            } catch (IOException ex) {
                System.out.println("Error al crear archivo");
            }
        }
    }
}
