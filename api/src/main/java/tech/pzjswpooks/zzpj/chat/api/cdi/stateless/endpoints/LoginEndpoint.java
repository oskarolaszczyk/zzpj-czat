package tech.pzjswpooks.zzpj.chat.api.cdi.stateless.endpoints;

import tech.pzjswpooks.zzpj.chat.api.payloads.request.LoginRequestDTO;
import tech.pzjswpooks.zzpj.chat.api.payloads.response.JWTResponseDTO;
import tech.pzjswpooks.zzpj.chat.api.payloads.response.MessageResponseDto;
import tech.pzjswpooks.zzpj.chat.api.security.JwtUtils;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginEndpoint {

    private final IdentityStoreHandler identityStoreHandler;

    private final JwtUtils jwtUtils;

    @Inject
    public LoginEndpoint(IdentityStoreHandler identityStoreHandler, JwtUtils jwtUtils) {
        this.identityStoreHandler = identityStoreHandler;
        this.jwtUtils = jwtUtils;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(LoginRequestDTO loginRequestDTO) {
        CredentialValidationResult credentialValidationResult = identityStoreHandler.validate(loginRequestDTO.toCredential());
        if (credentialValidationResult.getStatus() != CredentialValidationResult.Status.VALID) {
            // TODO: 09.05.2021 Poprawić odpowiedź
            return Response.status(Response.Status.UNAUTHORIZED).entity(new MessageResponseDto("Login Password Incorrect")).build();
        }
        return Response.ok().entity(
                new JWTResponseDTO(credentialValidationResult.getCallerPrincipal().getName(),
                credentialValidationResult.getCallerGroups(),
                jwtUtils.generateJwtTokenForUser(credentialValidationResult.getCallerPrincipal().getName()))).build();
    }

}
