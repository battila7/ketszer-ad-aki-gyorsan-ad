package progressive.delivery.workshop.dish.favorite;

import static java.util.Objects.isNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.account.persistence.Account;
import progressive.delivery.workshop.wish.persistence.Wish;

/**
 * Egy borzalmas megoldás, mely a memóriába tölti egy felhasználó összes rendelését.
 * Míg ez megfelelő implementáció kisebb adathalmazokhoz, addig a valóságban ezt inkább
 * az adatbázisra érdemes bízni.
 */
@ApplicationScoped
@Slf4j
public class InefficientFavoriteDish implements FavoriteDish {
    @Inject
    Mapper mapper;

    @Override
    public Optional<Dish> ofAccount(String name) {
        log.info("Computing favorite dish of account {}", name);

        return Account.findByUsernameOptional(name).flatMap(this::computeFavoriteDishOfAccount);
    }

    private Optional<Dish> computeFavoriteDishOfAccount(final Account account) {
        final var wishedDishes = Wish.streamWishesOfAccount(account)
                .flatMap(wish -> wish.getDishes().stream())
                .collect(Collectors.toMap(Function.identity(), dish -> 1, Integer::sum));

        progressive.delivery.workshop.dish.persistence.Dish mostWished = null;
        int maxWishes = 0;

        for (final var entry : wishedDishes.entrySet()) {
            if (entry.getValue() > maxWishes) {
                mostWished = entry.getKey();
                maxWishes = entry.getValue();
            }
        }

        if (isNull(mostWished)) {
            log.info("No favorite dish for {}", account.getUsername());
            return Optional.empty();
        }

        log.info("Favorite dish of {} is {} ({} times)", account.getUsername(), mostWished.getName(), maxWishes);
        return Optional.of(mapper.map(mostWished, maxWishes));
    }
}
