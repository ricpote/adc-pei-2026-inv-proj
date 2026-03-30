package pt.unl.fct.di.adc.firstwebapp.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.unl.fct.di.adc.firstwebapp.dto.BaseRequest;
import pt.unl.fct.di.adc.firstwebapp.dto.BaseResponse;
import pt.unl.fct.di.adc.firstwebapp.dto.DeleteAccountData;
import pt.unl.fct.di.adc.firstwebapp.service.ServiceRegistry;

@Path("/deleteaccount")
public class DeleteAccountResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BaseResponse<?> deleteAccount(BaseRequest<DeleteAccountData> request) {
        return BaseResponse.success(
                ServiceRegistry.userService().deleteAccount(
                        request.getToken(),
                        request.getInput().getUsername()));
    }

}
