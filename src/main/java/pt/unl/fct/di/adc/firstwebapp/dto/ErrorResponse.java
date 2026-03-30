package pt.unl.fct.di.adc.firstwebapp.dto;

public class ErrorResponse {

    private String status;
    private String data;

    public ErrorResponse() {
    }

    public ErrorResponse(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}