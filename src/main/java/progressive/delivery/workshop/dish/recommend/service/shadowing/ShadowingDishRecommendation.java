package progressive.delivery.workshop.dish.recommend.service.shadowing;

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

/**
 * A shadowing technikát implementáló osztály, mely egy tartalmazott light
 * és dark implementációt is meghív az ajánlás meghatározásához.
 * <p>
 * A dark válasz logolva lesz, majd eldobva, míg a light válasz logolva és
 * visszaadva a hívó számára.
 */
@ApplicationScoped
@Default
@Slf4j
public class ShadowingDishRecommendation implements DishRecommendation {
    /**
     * CDI Qualifier annotáció, mely arra szolgál, hogy a light traffic-ot
     * kiszolgáló DiskRecommendation implementációt jelöljük.
     * <p>
     * Erre azért van szükség, mivel valamilyen módon jeleznünk kell a CDI
     * számára, hogy ahol "DishRecommendation" implementációt kérünk, oda
     * pontosan mit is kell beinjektálni.
     */
    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Light {}

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Dark {}

    @Inject
    @Light
    DishRecommendation light;

    @Inject
    @Dark
    DishRecommendation dark;

    @Override
    public Optional<Recommendation> forAccount(final String account) {
        final var lightResponse = light.forAccount(account);
        final var darkResponse = dark.forAccount(account);

        log.info("Shadowing dish recommendation for user: {} Light {} Dark {}", account, lightResponse, darkResponse);

        return lightResponse;
    }
}
