package progressive.delivery.workshop.dummydata;

import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.account.persistence.Account;
import progressive.delivery.workshop.category.persistence.Category;
import progressive.delivery.workshop.dish.favorite.service.FavoriteDish;
import progressive.delivery.workshop.dish.persistence.Dish;
import progressive.delivery.workshop.dish.recommend.service.flagging.FlaggingDishRecommendation;
import progressive.delivery.workshop.featureflag.persistence.FeatureFlag;
import progressive.delivery.workshop.wish.persistence.Wish;

/**
 * Az alkalmazás által használt adatbázist előkészítő osztály.
 *
 * <p>
 * Feltölti az adatbázist különféle, a funkcionalitás kipróbálását
 * előségítő tesztadattal. A feltöltést követően rendelkezünk majd
 * egy "bob" nevű (és "pasword" jelszavú) felhasználóval, akinek kedves
 * étele a "kirántott hús rizsavú", melyet egy alkalommal rendelt.
 */
@ApplicationScoped
@Slf4j
public class DummyDataLoader {
    @Inject
    FavoriteDish favoriteDish;

    @Transactional
    public void onStartup(@Observes StartupEvent event) {
        log.info("Setting up dummy data.");

        Account.deleteAll();
        Category.deleteAll();
        Dish.deleteAll();
        Wish.deleteAll();
        FeatureFlag.deleteAll();

        Account.add("admin", "admin", Account.Role.ADMIN);

        final var bob = Account.add("bob", "password", Account.Role.CUSTOMER);

        final var rantottHusok = Category.add("rántott húsok");

        final var kirantottHusRizsavu = Dish.add("kirántott hús rizsavú");
        kirantottHusRizsavu.addCategory(rantottHusok);
        kirantottHusRizsavu.persist();
        final var wienerSchnitzel = Dish.add("wiener schnitzel");
        wienerSchnitzel.addCategory(rantottHusok);
        wienerSchnitzel.persist();
        final var tonkatsu = Dish.add("tonkatsu");
        tonkatsu.addCategory(rantottHusok);
        tonkatsu.persist();

        final var wish = Wish.add(bob);
        wish.addDish(kirantottHusRizsavu);
        wish.persist();

        FeatureFlag.add(FlaggingDishRecommendation.FLAG_NAME);

        // Sanity check, hogy a tesztadatok helyesen lettek-e feltöltve.
        // Azaz, tényleg létezik-e bob és az ő kedves kis étele.
        assert favoriteDish.ofAccount(bob.getUsername()).get().name().equals(kirantottHusRizsavu.getName());
    }
}
