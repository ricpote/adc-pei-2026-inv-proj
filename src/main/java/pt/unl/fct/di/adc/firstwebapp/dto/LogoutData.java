package pt.unl.fct.di.adc.firstwebapp.dto;

public class LogoutData {

    private String username;

    public LogoutData() {
    }

    public LogoutData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}