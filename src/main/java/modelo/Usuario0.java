package modelo;

public class Usuario0 {


    private String cpf;
    private String senha;

    public Usuario0(String senha, String cpf){
        this.cpf = cpf;
        this.senha = senha;
    }

    public Usuario0() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
