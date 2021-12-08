/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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


public class UsuarioDAO {
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    public UsuarioDAO() {

    }

    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO cliente(name,cpf,email,banco,senha,saldo) VALUES(?,?,?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, usuario.getName());
            pst.setString(2, usuario.getCpf());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getBanco());
            pst.setString(5, usuario.getSenha());
            pst.setString(6, usuario.getSaldo());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        return retorno;
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

    public boolean atualizar(Usuario usuario) {
        String sql = "UPDATE cliente set name=?,cpf=?, email=?, banco=?, senha=?, saldo=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, usuario.getName());
            pst.setString(2, usuario.getCpf());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getBanco());
            pst.setString(5, usuario.getSenha());
            pst.setString(6, usuario.getSaldo());
            if (pst.executeUpdate() > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        return retorno;
    }

    public boolean excluir(Usuario usuario) {
        String sql = "DELETE FROM cliente where cpf=?";
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

    public List<Usuario> listar() {
        String sql = "SELECT * FROM cliente";
        List<Usuario> retorno = new ArrayList<Usuario>();

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Usuario item = new Usuario();
                item.setName(res.getString("name"));
                item.setCpf(res.getString("cpf"));
                item.setEmail(res.getString("email"));
                item.setBanco(res.getString("banco"));
                item.setSenha(res.getString("senha"));
                item.setSaldo(res.getString("saldo"));
                retorno.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


    public Usuario buscar(Usuario usuario) {
        String sql = "SELECT * FROM cliente where cpf=?";
        Usuario retorno = null;

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, usuario.getCpf());
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                retorno = new Usuario();
                retorno.setName(res.getString("name"));
                retorno.setCpf(res.getString("cpf"));
                retorno.setEmail(res.getString("email"));
                retorno.setBanco(res.getString("banco"));
                retorno.setSenha(res.getString("senha"));
                retorno.setSaldo(res.getString("saldo"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return retorno;
    }

    /**
     * @param usuario
     * @return
     */
    public Usuario autenticar(Usuario usuario) {

        String sql = "SELECT * FROM cliente where senha=?";
        Usuario retorno = null;

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, usuario.getSenha());

            if (usuario.getSenha().length() >= 6) {
                ResultSet res = pst.executeQuery();

                if (res.next()) {
                    retorno = new Usuario();
                    System.out.printf("Você foi autenticado");
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
            System.out.println("Senha incorreta, verifique se ela tem 6 digitos!");
        }
        return retorno;
    }
}