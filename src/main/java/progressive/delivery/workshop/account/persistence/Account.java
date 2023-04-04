package progressive.delivery.workshop.account.persistence;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Optional;

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
@UserDefinition
@Table(name = "account")
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
    @Username
    @Column(nullable = false, unique = true)
    String username;

    @Password
    @Column(nullable = false)
    String password;

    @Roles
    String role;

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

    /**
     * Létrehoz egy új felhasználót a megadott névvel és jelszóval.
     *
     * @param username a felhasználó neve
     * @param password a felhasználó jelszava
     * @return a létrehozott felhasználó
     */
    public static Account add(final String username, final String password) {
        Account account = Account.builder()
                .username(username)
                .password(BcryptUtil.bcryptHash(password))
                .role("user")
                .build();

        account.persist();

        return account;
    }

    /**
     * Megkeresi a megadott névvel rendelkező felhasználót.
     *
     * @param username a keresett felhasználó neve
     * @return a keresett felhasználó, vagy {@code Optinal.empty()}, ha nem található
     */
    public static Optional<Account> findByUsernameOptional(final String username) {
        return find("username", username).firstResultOptional();
    }
}
