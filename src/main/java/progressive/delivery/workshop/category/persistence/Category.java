package progressive.delivery.workshop.category.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import progressive.delivery.workshop.dish.persistence.Dish;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;

@Entity
@Table(name = "category")
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Category extends PanacheEntity {
    @NaturalId
    @Column(nullable = false, unique = true)
    String name;

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    Set<Dish> dishes = new HashSet<>();

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

    public static Category add(final String name) {
        final var category = Category.builder()
                .name(name)
                .build();

        category.persist();

        return category;
    }
}
