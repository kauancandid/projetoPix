package dao2;

import modelo2.Extrato;
import modelo2.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExtratoDAO {


    public ExtratoDAO() {
    }

    public boolean cadastrarExtrato(Extrato extrato) {
        String sql = "INSERT INTO extrato(conta_id,value_string) VALUES(?,?)";

        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, extrato.getConta_id());
            pst.setString(2, extrato.getValue_string());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        return retorno;
    }

    public List<Extrato> emitirExtrato(Usuario usuario) {
        String sql = "SELECT * FROM extrato where conta_id=?";
        List<Extrato> retorno = new ArrayList<Extrato>();

        PreparedStatement pst = Conexao.getPreparedStatement(sql);

        try {
            assert pst != null;
            pst.setString(1, usuario.getCpf());
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Extrato item = new Extrato(res.getString("conta_id"), res.getString("value_string"));
                retorno.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
