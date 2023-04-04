package progressive.delivery.workshop.dummydata;

import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.account.persistence.Account;
import progressive.delivery.workshop.category.persistence.Category;
import progressive.delivery.workshop.dish.persistence.Dish;
import progressive.delivery.workshop.wish.persistence.Wish;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

/**
 * Az alkalmazás által használt adatbázist előkészítő osztály.
 *
 * <p>
 * Feltölti az adatbázist különféle, a funkcionalitás kipróbálását
 * előségítő tesztadattal.
 */
@ApplicationScoped
@Slf4j
public class DummyDataLoader {
    @Transactional
    public void onStartup(@Observes StartupEvent event) {
        log.info("Setting up dummy data.");

        Account.deleteAll();
        Category.deleteAll();
        Dish.deleteAll();
        Wish.deleteAll();

        final var bob = Account.add("bob", "password");

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
    }
}
