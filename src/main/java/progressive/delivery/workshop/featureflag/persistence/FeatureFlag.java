package progressive.delivery.workshop.featureflag.persistence;

import static java.util.Objects.isNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
import org.hibernate.annotations.NaturalId;

/**
 * Egy feature flaget reprezentáló entitás.
 */
@Entity
@Table(name = "feature_flag")
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FeatureFlag extends PanacheEntity {
    /**
     * A flag egyedi neve.
     */
    @NaturalId
    @Column(nullable = false, unique = true)
    String name;

    /**
     * Annak valószínűsége, hogy a flag igazra (bekapcsoltra) értékelődik ki.
     * <p>
     * 0 és 100 közötti egész szám. 0 (vagy annál kisebb érték) esetén mindig kikapcsolt,
     * 100 (vagy annál nagyobb érték) esetén mindig bekapcsolt lesz a flag.
     */
    @Column(nullable = false)
    int percentage;

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (isNull(obj) || !(obj instanceof FeatureFlag)) {
            return false;
        }

        return Objects.equals(getName(), ((FeatureFlag) obj).getName());
    }

    /**
     * Létrehoz egy új flaget a megadott névvel és 0 valószínűséggel.
     *
     * @param name a flag neve
     * @return az új flag
     */
    public static FeatureFlag add(final String name) {
        final var featureFlag = FeatureFlag.builder().name(name).percentage(0).build();

        featureFlag.persist();

        return featureFlag;
    }

    public static Optional<FeatureFlag> findByNameOptional(final String name) {
        return find("name", name).firstResultOptional();
    }
}
