package progressive.delivery.workshop.dish.recommend.service;

import java.util.Optional;

/**
 * A következő rendeléshez fogást ajánló osztály.
 */
public interface DishRecommendation {
    /**
     * Meghatároz egy ajánlott fogást a megadott felhasználó számára.
     * <p>
     * Bizonyos esetekben nem lehetséges fogást ajánlani, ekkor üres
     * Optional fog visszaadni.
     *
     * @param account a felhasználó, akinek ajánlani szeretnénk
     * @return az ajánlott fogás
     */
    Optional<Recommendation> forAccount(String account);

    record Recommendation(String name) {}
}
