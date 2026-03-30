package pt.unl.fct.di.adc.firstwebapp.dto;

public class DeleteAccountData {

    private String username;

    public DeleteAccountData() {
    }

    public DeleteAccountData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}