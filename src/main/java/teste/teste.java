package teste;

import dao2.UsuarioDAO;
import modelo.Usuario0;

public class teste {
    public static void main(String[] args) {
        teste.listarTeste();
    }

    public static void listarTeste() {

        Usuario0 use = new Usuario0("123.456.789-12", "123456");
        UsuarioDAO dao = new UsuarioDAO();
    }
}
