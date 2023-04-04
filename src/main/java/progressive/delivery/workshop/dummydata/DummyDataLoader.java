package progressive.delivery.workshop.dummydata;

import io.quarkus.runtime.StartupEvent;
import progressive.delivery.workshop.account.persistence.Account;

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
public class DummyDataLoader {
    @Transactional
    public void onStartup(@Observes StartupEvent event) {
        Account.deleteAll();

        Account.add("bob", "password");
    }
}
