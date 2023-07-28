import java.io.Serializable;

public class Usuario implements Serializable {
    //
    private static final long serialVersionUID = 1L;
    private String usuario;
    private String password;

    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "Usuario: "+ usuario + "\nContraseña: "+ password + "\n";
    }
}
