package progressive.delivery.workshop.dish.recommend.service.similar;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.dish.favorite.service.FavoriteDish;
import progressive.delivery.workshop.dish.persistence.Dish;
import progressive.delivery.workshop.dish.recommend.service.DishRecommendation;
import progressive.delivery.workshop.dish.recommend.service.flagging.FlaggingDishRecommendation;

/**
 * Ajánló-implementáció, mely mindig a felhasználó kedvenc ételéhez hasonló
 * (azzal megegyező kategóriában levő) fogást próbál ajánlani.
 */
@ApplicationScoped
@FlaggingDishRecommendation.New
@Slf4j
public class SimilarDishRecommendation implements DishRecommendation {
    @Inject
    FavoriteDish favoriteDish;

    @Inject
    Mapper mapper;

    @Override
    @Transactional
    public Optional<Recommendation> forAccount(final String account) {
        log.info("Getting recommendation for user based on dish similarity: {}", account);

        final Optional<Dish> favoriteDishOptional =
                favoriteDish.ofAccount(account).map(FavoriteDish.Dish::name).flatMap(Dish::findByNameOptional);

        if (favoriteDishOptional.isEmpty()) {
            return Optional.empty();
        }

        final var favoriteDish = favoriteDishOptional.get();

        return favoriteDish.getCategories().stream()
                .flatMap(category -> category.getDishes().stream())
                .findAny()
                .map(mapper::map);
    }

    @org.mapstruct.Mapper
    interface Mapper {
        Recommendation map(Dish dish);
    }
}
