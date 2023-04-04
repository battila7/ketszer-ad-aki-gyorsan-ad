package progressive.delivery.workshop.dish.favorite.service;

import java.util.Optional;

/**
 * Meghatározza egy felhasználó kedvenc ételét a korábbi rendelései
 * alapján.
 */
public interface FavoriteDish {
    /**
     * Visszaadja a felhasználó kedvenc ételét.
     *
     * @param name a felhasználó neve
     * @return a felhasználó kedvenc étele avagy egy üres Optional, ha ilyen
     * névvel nincs felhasználó vagy pedig nem rendelt sosem
     */
    Optional<Dish> ofAccount(String name);

    /**
     * A kedvenc étel adatai.
     *
     * @param name az étel neve
     * @param wishCount hány rendelésben szerepelt az étel
     */
    record Dish(String name, int wishCount) {}

    @org.mapstruct.Mapper
    interface Mapper {
        Dish map(progressive.delivery.workshop.dish.persistence.Dish dish, int wishCount);
    }
}
