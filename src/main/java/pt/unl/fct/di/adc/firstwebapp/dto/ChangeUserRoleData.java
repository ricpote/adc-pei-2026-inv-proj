package pt.unl.fct.di.adc.firstwebapp.dto;

public class ChangeUserRoleData {

    private String username;
    private String newRole;

    public ChangeUserRoleData() {
    }

    public ChangeUserRoleData(String username, String newRole) {
        this.username = username;
        this.newRole = newRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewRole() {
        return newRole;
    }

}