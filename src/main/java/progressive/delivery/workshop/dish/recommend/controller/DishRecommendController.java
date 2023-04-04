package progressive.delivery.workshop.dish.recommend.controller;

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
import progressive.delivery.workshop.dish.recommend.service.DishRecommendation;

@Path("/dish/recommend")
@Slf4j
public class DishRecommendController {
    @Inject
    DishRecommendation dishRecommendation;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    Mapper mapper;

    @GET
    @Authenticated
    @Tag(name = "Dish")
    @Operation(summary = "Gets the recommended dish for the user.")
    @APIResponse(responseCode = "404", description = "No recommended dish found.")
    @APIResponse(
            responseCode = "200",
            description = "Successful retrieval.",
            content =
                    @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = RecommendationResponse.class)))
    public Response recommend() {
        final String account = securityIdentity.getPrincipal().getName();

        log.info("Getting recommendation for user {}", account);

        final Optional<RecommendationResponse> response =
                dishRecommendation.forAccount(account).map(mapper::map);

        if (response.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(response.get()).build();
    }

    record RecommendationResponse(String name) {}

    @org.mapstruct.Mapper
    interface Mapper {
        RecommendationResponse map(DishRecommendation.Recommendation recommendation);
    }
}
