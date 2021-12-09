package com.meuprojeto.meuprimeiroprojeto;

import com.google.gson.Gson;
import dao2.ExtratoDAO;
import dao2.PixDAO;
import dao2.UsuarioDAO;
import modelo2.Extrato;
import modelo2.Pix;
import modelo2.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ProjetoControle {

    @PostMapping(value = "/addUsuarioBD", consumes = "application/json", produces = "application/json")
    public ResponseEntity inserirUsuarioBD(@RequestBody String content) {
        Gson g = new Gson();
        System.out.println(content);
        Usuario user = (Usuario) g.fromJson(content, Usuario.class);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        boolean confirmou = new UsuarioDAO().inserir(user);
        if (confirmou)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/atualizarUsuarioBD", consumes = "application/json", produces = "application/json")
    public ResponseEntity atualizarUsuarioBD(@RequestBody String content) {
        Gson g = new Gson();
        System.out.println(content);
        Usuario user = (Usuario) g.fromJson(content, Usuario.class);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        boolean confirmou = new UsuarioDAO().atualizar(user);
        if (confirmou)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/addPixBD", consumes = "application/json", produces = "application/json")
    public ResponseEntity cadastroPix(@RequestBody String content) {
        Gson g = new Gson();
        System.out.println(content);
        Pix pix = (Pix) g.fromJson(content, Pix.class);
        System.out.println(pix.getPix_key());
        System.out.println(pix.getCpf_id());

        boolean confirmou = new PixDAO().cadastrarPix(pix);
        if (confirmou)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/addExtratoBD", consumes = "application/json", produces = "application/json")
    public ResponseEntity cadastroExtrato(@RequestBody String content) {
        Gson g = new Gson();
        System.out.println(content);
        Extrato ext = (Extrato) g.fromJson(content, Extrato.class);
        System.out.println(ext.getConta_id());
        System.out.println(ext.getValue_string());

        boolean confirmou = new ExtratoDAO().cadastrarExtrato(ext);
        if (confirmou)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/deleteuserBD", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> DeletarUsuarioBD(@RequestBody Usuario user) {
        UsuarioDAO dao = new UsuarioDAO();

        if (dao.excluir(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/deletePixBD", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> DeletarPixBD(@RequestBody Usuario user) {
        PixDAO dao = new PixDAO();

        if (dao.excluirPix(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/BuscarUsuarioBD", consumes = "application/json", produces = "application/json")
    public String BuscarUserBD(@RequestBody Usuario user) {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario userPrint = dao.buscar(user);
        Gson g = new Gson();
        return g.toJson(userPrint);
    }

    @PostMapping(value = "/autenticarUserBD", consumes = "application/json", produces = "application/json")
    public Usuario AltenticarUserBD(@RequestBody Usuario user) {
        UsuarioDAO dao = new UsuarioDAO();
        return dao.autenticar(user);
    }

    @PostMapping(value = "/transacaoPix", consumes = "application/json", produces = "application/json")
    public String transacaoPix(@RequestBody Map<String, String> json) {
        PixDAO dao = new PixDAO();
        Usuario usuario = new Usuario();
        usuario.setCpf(json.get("cpf"));
        usuario.setSenha(json.get("senha"));
        String userPrint = dao.transferenciaPix(usuario, json.get("chave"), Integer.parseInt(json.get("valor")));
        Gson g = new Gson();
        return g.toJson(userPrint);
    }

    @PostMapping(value = "/depositando", consumes = "application/json", produces = "application/json")
    public String depositando(@RequestBody Map<String, String> json) {
        Usuario usuario = new Usuario();
        usuario.setCpf(json.get("cpf"));
        usuario.setSenha(json.get("senha"));
        String userPrint = new UsuarioDAO().depositar(usuario, Integer.parseInt(json.get("valor")));
        Gson g = new Gson();
        return g.toJson(userPrint);
    }

    @PostMapping(value = "/emitirExtrato", consumes = "application/json", produces = "application/json")
    public String emitirExtrato(@RequestBody Usuario usuario) {
        ExtratoDAO dao = new ExtratoDAO();
        List<Extrato> user = dao.emitirExtrato(usuario);
        Gson g = new Gson();
        StringBuilder resposta = new StringBuilder();

        for (Extrato extrato : user) {
            resposta.append("\n").append(extrato.getValue_string());
        }
        return resposta.toString();
    }
}