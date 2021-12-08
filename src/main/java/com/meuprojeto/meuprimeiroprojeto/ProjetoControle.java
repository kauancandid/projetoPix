package com.meuprojeto.meuprimeiroprojeto;

import com.google.gson.Gson;
import dao2.PixDAO;
import dao2.UsuarioDAO;
import modelo.Usuario0;
import modelo2.Extrato;
import modelo2.Pix;
import modelo2.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjetoControle {

    @RequestMapping("/")
    public String welcome() {
        return "seja bem-vindo";
    }

    @GetMapping("/deuBom")
    public ResponseEntity verificarConectividade() {
        return new ResponseEntity(HttpStatus.OK);
    }


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

        boolean confirmou = new UsuarioDAO().cadastrarExtrato(ext);

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


    @PostMapping(value = "/jsonUsuario", produces = "application/json")
    public String getJsonUsuario(String content) {

        Usuario0 u = new Usuario0("rnsg", "1234");

        //Converter para Gson
        Gson g = new Gson();
        return g.toJson(u);

    }

    @PostMapping(value = "/BuscarUser", consumes = "application/json", produces = "application/json")
    public String BuscarUserBD(@RequestBody Usuario user) {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario userPrint = dao.buscar(user);
        Gson g = new Gson();
        return g.toJson(userPrint);
    }

    @PostMapping(value = "/autenticarUserBD", consumes = "application/json", produces = "application/json")
    public String AltenticarUserBD(@RequestBody Usuario user) {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario userPrint = dao.autenticar(user);
        Gson g = new Gson();
        return g.toJson(userPrint);
    }

    @PostMapping(value = "/transacaoPix", consumes = "application/json", produces = "application/json")
    public String transacaoPix(@RequestBody Usuario user) {
        PixDAO dao = new PixDAO();
        String userPrint = dao.transferenciaPix(user, "12345678", 100);
        Gson g = new Gson();
        return g.toJson(userPrint);
    }


}