package progressive.delivery.workshop.util.random;

import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class RandomProvider {
    @ApplicationScoped
    @Produces
    public Random random() {
        return new Random();
    }
}
