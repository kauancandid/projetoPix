package dao2;

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

    public String transferenciaPix(Usuario trans1, String pix, int transacao) {

        int saldoTrans1 = Integer.parseInt(trans1.getSaldo());
        if ( saldoTrans1 >= transacao) {
            System.out.printf("entrou");
            Pix obj = new Pix();
            obj.setPix_key(pix);
            obj = buscarPix(obj);
            Usuario aux = new Usuario();
            aux.setCpf(obj.getCpf_id());

            Usuario usuarioTrans = new UsuarioDAO().buscar(aux);
            if (usuarioTrans != null) {
                System.out.printf("entrou");

                int saldoUsuaioTrans = Integer.parseInt(usuarioTrans.getSaldo());
                saldoTrans1 = saldoTrans1 - transacao;
                saldoUsuaioTrans = saldoUsuaioTrans + transacao;

                trans1.setSaldo(String.valueOf(saldoTrans1));
                usuarioTrans.setSaldo(String.valueOf(saldoUsuaioTrans));


                atualizarSaldoPix(trans1);
                atualizarSaldoPix(usuarioTrans);
            }

        }else{
            System.out.printf("SALDO INSIFUCIENTE!!");
        }

        return "retorno";
    }
}
