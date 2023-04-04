package progressive.delivery.workshop.wish.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import progressive.delivery.workshop.account.persistence.Account;
import progressive.delivery.workshop.dish.persistence.Dish;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.isNull;

@Entity
@Table(name = "wish")
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Wish extends PanacheEntity {
    @NaturalId
    String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    Account account;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "order_dish",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
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

    public static Wish add(final Account account) {
        final var wish = Wish.builder()
                .uid(UUID.randomUUID().toString())
                .account(account)
                .build();

        wish.persist();

        return wish;
    }
}
