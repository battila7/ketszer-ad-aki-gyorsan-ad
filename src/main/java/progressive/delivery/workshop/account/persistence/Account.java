package progressive.delivery.workshop.account.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Egy felhasználói hozzáférést reprezentáló adatbázisentitás.
 *
 * <p>
 * Minden felhasználó rendelkezik egy mesterséges azonosítóval, melyet DB elsődleges kulcsként használunk,
 * (surrogate primary key), továbbá egy felhasználónévvel, mely az üzleti szintű azonosítást biztosítja
 * (business key).
 *
 * @see <a href="https://quarkus.io/guides/hibernate-orm-panache">Simplified Hibernate ORM with Panache</a>
 */
@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Account extends PanacheEntity {
    /**
     * A felhasználó egyedi neve az alkalmazáson belül.
     */
    @NaturalId
    @Column(nullable = false, unique = true)
    String username;

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (isNull(obj) || !(obj instanceof Account)) {
            return false;
        }

        return Objects.equals(getUsername(), ((Account) obj).getUsername());
    }
}
