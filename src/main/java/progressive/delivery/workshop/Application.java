package progressive.delivery.workshop;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Az alkalmazás belépési pontja.
 *
 * @see <a href="https://quarkus.io/guides/lifecycle">Quarkus Application Initializatiojn</a>
 */
@QuarkusMain
public class Application {
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
