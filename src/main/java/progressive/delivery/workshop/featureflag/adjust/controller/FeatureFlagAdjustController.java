package progressive.delivery.workshop.featureflag.adjust.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import progressive.delivery.workshop.account.persistence.Account;
import progressive.delivery.workshop.featureflag.adjust.service.FeatureFlagAdjust;

@Path("/feature-flag/{name}/adjust")
@Slf4j
public class FeatureFlagAdjustController {
    @Inject
    FeatureFlagAdjust featureFlagAdjust;

    @POST
    @RolesAllowed(Account.Role.ADMIN)
    @Tag(name = "Feature Flag")
    @Operation(summary = "Adjusts a feature flag.")
    @APIResponse(responseCode = "200", description = "Successfully set.")
    public Response adjust(@PathParam("name") final String name, final Request request) {
        featureFlagAdjust.to(name, request.percentage);

        return Response.ok().build();
    }

    record Request(int percentage) {}
}
