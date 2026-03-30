package pt.unl.fct.di.adc.firstwebapp.dto;

public class BaseResponse<T> {

    private String status;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("success", data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}