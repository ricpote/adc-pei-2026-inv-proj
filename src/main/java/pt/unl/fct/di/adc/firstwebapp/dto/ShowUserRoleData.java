package pt.unl.fct.di.adc.firstwebapp.dto;

public class ShowUserRoleData {

    private String username;

    public ShowUserRoleData() {
    }

    public ShowUserRoleData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}