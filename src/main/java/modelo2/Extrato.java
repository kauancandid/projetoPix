package modelo2;

public class Extrato {
    private String conta_id;
    private String value_string;

    public Extrato(String conta_id, String value_string) {
        this.conta_id = conta_id;
        this.value_string = value_string;
    }

    public String getConta_id() {
        return conta_id;
    }

    public void setConta_id(String conta_id) {
        this.conta_id = conta_id;
    }

    public String getValue_string() {
        return value_string;
    }

    public void setValue_string(String value_string) {
        this.value_string = value_string;
    }
}
