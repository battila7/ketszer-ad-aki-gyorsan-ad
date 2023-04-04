package progressive.delivery.workshop.wish.create.service;

import java.util.List;
import progressive.delivery.workshop.wish.persistence.Wish;

/**
 * Létrehoz egy új rendelést.
 */
public interface WishCreate {
    /**
     * Létrehoz egy új, a megadott felhasználóhoz tartozó rendelést. A rendelés
     * a megadott nevű fogásokat fogja tartalmazni.
     * @param creationData a rendelés létrehozásához szükséges adatok
     * @return az új rendelés adatai
     */
    CreatedWish create(CreationData creationData);

    record CreationData(String account, List<String> dishes) {}

    record CreatedWish(String uid) {}

    @org.mapstruct.Mapper
    interface Mapper {
        CreatedWish map(Wish wish);
    }
}
