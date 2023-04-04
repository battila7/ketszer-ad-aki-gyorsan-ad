package progressive.delivery.workshop.dish.persistence;

import static java.util.Objects.isNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import progressive.delivery.workshop.category.persistence.Category;

/**
 * Egy fogást reprezentáló entitást.
 */
@Entity
@Table(name = "dish")
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Dish extends PanacheEntity {
    /**
     * A fogás egyedi neve, például "pörkölt".
     */
    @NaturalId
    @Column(nullable = false, unique = true)
    String name;

    /**
     * A kategóriák, melyekbe ez a fogás tartozik.
     */
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "dish_category",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    @Builder.Default
    Set<Category> categories = new HashSet<>();

    public void addCategory(final Category category) {
        categories.add(category);
        category.getDishes().add(this);
    }

    public void removeCategory(final Category category) {
        categories.remove(category);
        category.getDishes().remove(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (isNull(obj) || !(obj instanceof Dish)) {
            return false;
        }

        return Objects.equals(getName(), ((Dish) obj).getName());
    }

    /**
     * Létrehoz egy új fogást a megadott névvel.
     *
     * @param name a fogás neve
     * @return az új fogás
     */
    public static Dish add(final String name) {
        final var dish = Dish.builder().name(name).build();

        dish.persist();

        return dish;
    }

    public static Stream<Dish> streamAllWithName(final Collection<String> names) {
        return find("name in :names", Parameters.with("names", names)).stream();
    }
}
