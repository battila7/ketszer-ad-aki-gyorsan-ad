package progressive.delivery.workshop.dish.recommend.service.favorite;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.dish.favorite.service.FavoriteDish;
import progressive.delivery.workshop.dish.recommend.service.DishRecommendation;
import progressive.delivery.workshop.dish.recommend.service.flagging.FlaggingDishRecommendation;

/**
 * Ajánlás-implementáció, mely mindig a felhasználó kedvenc ételét adja
 * vissza.
 */
@ApplicationScoped
@FlaggingDishRecommendation.Original
@Slf4j
public class FavoriteDishRecommendation implements DishRecommendation {
    @Inject
    FavoriteDish favoriteDish;

    @Inject
    Mapper mapper;

    @Override
    public Optional<Recommendation> forAccount(final String account) {
        log.info("Getting recommendation for user based on favorite dish: {}", account);

        return favoriteDish.ofAccount(account).map(mapper::map);
    }

    @org.mapstruct.Mapper
    interface Mapper {
        Recommendation map(FavoriteDish.Dish dish);
    }
}
