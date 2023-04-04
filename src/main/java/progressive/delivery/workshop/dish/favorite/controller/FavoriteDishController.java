package progressive.delivery.workshop.dish.favorite.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.mapstruct.Mapper;
import progressive.delivery.workshop.dish.favorite.service.FavoriteDish;

@Path("/dish/favorite")
@Slf4j
public class FavoriteDishController {
    @Inject
    FavoriteDish favoriteDish;

    @Inject
    SecurityIdentity identity;

    @Inject
    Mapper mapper;

    @GET
    @Authenticated
    @Tag(name = "Dish")
    @Operation(summary = "Gets the favorite dish for the user.")
    @APIResponse(responseCode = "404", description = "No favorite dish found.")
    @APIResponse(
            responseCode = "200",
            description = "Successful retrieval.",
            content =
                    @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = FavoriteDishResponse.class)))
    public Response get() {
        final String account = identity.getPrincipal().getName();

        log.info("Getting favorite dish for user {}", account);

        final Optional<FavoriteDishResponse> dishOptional =
                favoriteDish.ofAccount(account).map(mapper::map);

        if (dishOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(dishOptional.get()).build();
    }

    record FavoriteDishResponse(String name, int wishCount) {}

    @org.mapstruct.Mapper
    interface Mapper {
        FavoriteDishResponse map(FavoriteDish.Dish dish);
    }
}
