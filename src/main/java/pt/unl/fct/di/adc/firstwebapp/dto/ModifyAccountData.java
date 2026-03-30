package pt.unl.fct.di.adc.firstwebapp.dto;

import java.util.Map;

public class ModifyAccountData {

    private String username;
    private Map<String, String> attributes;

    public ModifyAccountData() {
    }

    public ModifyAccountData(String username, Map<String, String> attributes) {
        this.username = username;
        this.attributes = attributes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}