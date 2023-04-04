package progressive.delivery.workshop.category.persistence;

import static java.util.Objects.isNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import progressive.delivery.workshop.dish.persistence.Dish;

/**
 * Fogásokat csoportosító kategória (vagyis, az annak megfelelő entitás).
 */
@Entity
@Table(name = "category")
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Category extends PanacheEntity {
    /**
     * A kategória egyedi neve.
     */
    @NaturalId
    @Column(nullable = false, unique = true)
    String name;

    /**
     * A kategóriába tartozó fogások.
     */
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    Set<Dish> dishes = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (isNull(obj) || !(obj instanceof Category)) {
            return false;
        }

        return Objects.equals(getName(), ((Category) obj).getName());
    }

    /**
     * Létrehoz egy új kategóriát a megadott névvel.
     *
     * @param name a kategória neve
     * @return az új kategória
     */
    public static Category add(final String name) {
        final var category = Category.builder().name(name).build();

        category.persist();

        return category;
    }
}
