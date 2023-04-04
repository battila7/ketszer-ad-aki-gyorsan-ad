package progressive.delivery.workshop.account.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import static java.util.Objects.isNull;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Account extends PanacheEntity {
    @NaturalId
    @Column(nullable = false, unique = true)
    String username;

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (isNull(obj) || !(obj instanceof Account)) {
            return false;
        }

        return Objects.equals(getUsername(), ((Account) obj).getUsername());
    }
}
