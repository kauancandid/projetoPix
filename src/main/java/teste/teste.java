package teste;

import dao2.ExtratoDAO;
import modelo2.Extrato;
import modelo2.Usuario;

import java.util.List;

public class teste {
    public static void main(String[] args) {
        teste.listarTeste();
    }

    public static void listarTeste() {

        Usuario user = new Usuario();
        user.setCpf("123.123.123-12");

        List<Extrato> extratos;
        extratos = new ExtratoDAO().emitirExtrato(user);

        for (Extrato u : extratos) {
            System.out.println(u.getValue_string());
        }
    }
}
