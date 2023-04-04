package progressive.delivery.workshop.wish.persistence;

import static java.util.Objects.isNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import progressive.delivery.workshop.account.persistence.Account;
import progressive.delivery.workshop.dish.persistence.Dish;

/**
 * Rendeléseket reprezentáló entitás.
 *
 * <p>
 * Lényegében egy kapcsolólótábla a felhasználók és az ételek között, hiszen
 * esetünkben ugyanazon étel egy rendelésben csak egyszer szerepelhet.
 */
@Entity
@Table(name = "wish")
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Wish extends PanacheEntity {
    /**
     * A rendelés egyedi azonosítója, melyet az API-on keresztül
     * is elérhetővé teszünk, szemben az elsődleges kulccsal.
     */
    @NaturalId
    String uid;

    /**
     * A rendelést leadó felhasználó.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    Account account;

    /**
     * A rendelésben szereplő ételek.
     *
     * <p>
     * Egyszerűsítés: egy rendelésben egy étel csak egyszer szerepelhet.
     */
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    @ToString.Exclude
    @Builder.Default
    Set<Dish> dishes = new HashSet<>();

    public void addDish(final Dish dish) {
        dishes.add(dish);
    }

    public void removeDish(final Dish dish) {
        dishes.remove(dish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (isNull(obj) || !(obj instanceof Wish)) {
            return false;
        }

        return Objects.equals(getUid(), ((Wish) obj).getUid());
    }

    /**
     * Létrehoz egy új rendelést, melyet a megadott felhasználó adott le.
     *
     * @param account a rendelést leadó felhasználó
     * @return az új rendelés
     */
    public static Wish add(final Account account) {
        final var wish = Wish.builder()
                .uid(UUID.randomUUID().toString())
                .account(account)
                .build();

        wish.persist();

        return wish;
    }

    /**
     * Visszaadja a megadott felhasználó rendeléseit.
     *
     * @param account a felhasználó
     * @return a felhasználó rendelései
     */
    public static Stream<Wish> streamWishesOfAccount(final Account account) {
        return stream("account", account);
    }
}
