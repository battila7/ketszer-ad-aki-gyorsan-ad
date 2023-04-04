package progressive.delivery.workshop.dish.recommend.service.flagging;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Qualifier;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.dish.recommend.service.DishRecommendation;
import progressive.delivery.workshop.featureflag.router.FeatureFlagRouter;

/**
 * A feature flagging technikát implementáló osztály, mely egy eredeti és egy új
 * implementáció között választ.
 */
@ApplicationScoped
@Default
@Slf4j
public class FlaggingDishRecommendation implements DishRecommendation {
    /**
     * Az implementáció kiválasztásához használt flag neve.
     */
    public static final String FLAG_NAME = "similarity-based-dish-recommendation";

    /**
     * CDI Qualifier annotáció, mely arra szolgál, hogy az eredeti DishRecommendation
     * implementációt jelöljük.
     * <p>
     * Amennyiben a flag kikapcsolt állapotban van, akkor ezt az implementációt fogjuk
     * meghívni, és ennek az eredményét fogjuk visszaadni.
     */
    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Original {}

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface New {}

    @Inject
    @Original
    DishRecommendation original;

    @Inject
    @New
    DishRecommendation newOne;

    @Inject
    FeatureFlagRouter flagRouter;

    @Override
    public Optional<Recommendation> forAccount(final String account) {
        if (flagRouter.isOn(FLAG_NAME)) {
            return newOne.forAccount(account);
        } else {
            return original.forAccount(account);
        }
    }
}
