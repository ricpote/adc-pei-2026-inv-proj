package pt.unl.fct.di.adc.firstwebapp.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.unl.fct.di.adc.firstwebapp.dto.BaseRequest;
import pt.unl.fct.di.adc.firstwebapp.dto.BaseResponse;
import pt.unl.fct.di.adc.firstwebapp.dto.LogoutData;
import pt.unl.fct.di.adc.firstwebapp.service.ServiceRegistry;

@Path("/logout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LogoutResource {

    @POST
    public BaseResponse<?> logout(BaseRequest<LogoutData> request) {
        return BaseResponse.success(
                ServiceRegistry.userService().logout(
                        request.getToken(),
                        request.getInput().getUsername()));
    }
}