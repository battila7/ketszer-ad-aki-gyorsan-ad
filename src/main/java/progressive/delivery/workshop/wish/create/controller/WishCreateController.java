package progressive.delivery.workshop.wish.create.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import progressive.delivery.workshop.wish.create.service.WishCreate;

@Path("/wish")
@Slf4j
public class WishCreateController {
    @Inject
    WishCreate wishCreate;

    @Inject
    SecurityIdentity identity;

    @POST
    @Authenticated
    @Tag(name = "Wish")
    @Operation(summary = "Creates a new wish.")
    @APIResponse(
            responseCode = "200",
            description = "Successful creation.",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Request.class)))
    public Response create(@Valid final Request request) {
        final String account = identity.getPrincipal().getName();

        log.info("Creating wish for user {}", account);

        wishCreate.create(new WishCreate.CreationData(account, request.dishes()));

        return Response.ok().build();
    }

    record Request(@NotEmpty List<String> dishes) {}
}
