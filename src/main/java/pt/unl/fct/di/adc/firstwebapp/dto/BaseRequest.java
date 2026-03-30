package pt.unl.fct.di.adc.firstwebapp.dto;

import pt.unl.fct.di.adc.firstwebapp.model.AuthToken;

public class BaseRequest<T> {

    private T input;
    private AuthToken token;

    public BaseRequest() {
    }

    public BaseRequest(T input, AuthToken token) {
        this.input = input;
        this.token = token;
    }

    public T getInput() {
        return input;
    }

    public void setInput(T input) {
        this.input = input;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "input=" + input +
                ", token=" + token +
                '}';
    }
}
