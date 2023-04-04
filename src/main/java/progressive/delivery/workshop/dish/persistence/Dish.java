package progressive.delivery.workshop.dish.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import progressive.delivery.workshop.category.persistence.Category;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;

@Entity
@Table(name = "dish")
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Dish extends PanacheEntity {
    @NaturalId
    @Column(nullable = false, unique = true)
    String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "dish_category",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
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

    public static Dish add(final String name) {
        final var dish = Dish.builder()
                .name(name)
                .build();

        dish.persist();

        return dish;
    }
}
