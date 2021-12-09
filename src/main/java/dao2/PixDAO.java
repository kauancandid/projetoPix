package dao2;

import modelo2.Extrato;
import modelo2.Pix;
import modelo2.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PixDAO {

    public PixDAO() {

    }

    public boolean cadastrarPix(Pix pix) {
        String sql = "INSERT INTO pix(pix_key,cpf_id) VALUES(?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);

        try {
            pst.setString(1, pix.getPix_key());
            pst.setString(2, pix.getCpf_id());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        return retorno;
    }

    public boolean excluirPix(Usuario usuario) {
        String sql = "DELETE FROM pix where cpf_id=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);

        try {
            pst.setString(1, usuario.getCpf());
            if (pst.executeUpdate() > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        return retorno;
    }

    public Pix buscarPix(Pix pix) {
        String sql = "SELECT * FROM pix where pix_key=?";
        Pix retorno = null;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);

        try {
            pst.setString(1, pix.getPix_key());
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                retorno = new Pix();
                retorno.setPix_key(res.getString("pix_key"));
                retorno.setCpf_id(res.getString("cpf_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public boolean atualizarSaldoPix(Usuario usuario) {
        String sql = "UPDATE cliente set saldo=? WHERE cpf=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);

        try {
            pst.setString(1, usuario.getSaldo());
            pst.setString(2, usuario.getCpf());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        return retorno;
    }

    public Usuario autenticar(Usuario usuario) {
        String sql = "SELECT * FROM cliente where senha=? AND cpf=?";
        Usuario retorno = null;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);

        try {
            assert pst != null;
            pst.setString(1, usuario.getSenha());
            pst.setString(2, usuario.getCpf());

            if (usuario.getSenha().length() >= 6) {
                ResultSet res = pst.executeQuery();

                if (res.next()) {
                    retorno = new Usuario();
                    System.out.printf("Usuario foi autenticado");
                    retorno.setName(res.getString("name"));
                    retorno.setCpf(res.getString("cpf"));
                    retorno.setEmail(res.getString("email"));
                    retorno.setBanco(res.getString("banco"));
                    retorno.setSenha(res.getString("senha"));
                    retorno.setSaldo(res.getString("saldo"));
                }

            } else {
                System.out.println("Senha incorreta, verifique se ela tem 6 digitos!");
            }
        } catch (SQLException ex) {
            System.out.println("Usuario não foi autenticado!");
        }
        return retorno;
    }

    public String transferenciaPix(Usuario trans1, String pix, int transacao) {
        Usuario user = autenticar(trans1);
        String extrato = "";

        if (user != null) {
            System.out.println("Usuario autenticado");
            int saldoTrans1 = Integer.parseInt(user.getSaldo());

            if (saldoTrans1 >= transacao && transacao <= 1000) {
                Pix obj = new Pix();
                obj.setPix_key(pix);
                obj = buscarPix(obj);
                Usuario aux = new Usuario();
                aux.setCpf(obj.getCpf_id());
                Usuario usuarioTrans = new UsuarioDAO().buscar(aux);

                if (usuarioTrans != null) {
                    int saldoUsuaioTrans = Integer.parseInt(usuarioTrans.getSaldo());
                    saldoTrans1 = saldoTrans1 - transacao;
                    saldoUsuaioTrans = saldoUsuaioTrans + transacao;

                    user.setSaldo(String.valueOf(saldoTrans1));
                    usuarioTrans.setSaldo(String.valueOf(saldoUsuaioTrans));

                    atualizarSaldoPix(user);
                    atualizarSaldoPix(usuarioTrans);

                    Extrato extratoTrans1 = new Extrato(user.getCpf(), ("Valor da transação: " + transacao + " / " + " para " + usuarioTrans.getName() + " cpf: " + usuarioTrans.getCpf() + " banco: " + usuarioTrans.getBanco()));
                    Extrato extratoUsuarioTrans = new Extrato(usuarioTrans.getCpf(), ("Valor recebido da transação: " + transacao + " / " + " de " + user.getName() + " cpf: " + user.getCpf() + "banco: " + user.getBanco()));
                    ExtratoDAO ext = new ExtratoDAO();
                    ext.cadastrarExtrato(extratoTrans1);
                    ext.cadastrarExtrato(extratoUsuarioTrans);
                    extrato = extratoTrans1.getValue_string();
                }

            } else {
                System.out.printf("SALDO INSIFUCIENTE ou a Transação excede 1000 mil reais!!");
            }

        } else {
            System.out.println("USUARIO NÃO FOI AUTENTICADO");
        }

        return "Transação Concluida com Sucesso " + extrato;
    }
}
