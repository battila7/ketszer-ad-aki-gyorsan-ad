package progressive.delivery.workshop.featureflag.router;

/**
 * Feature flagek kiértékelése.
 */
public interface FeatureFlagRouter {
    /**
     * Kiértékeli, hogy a megadott nevű flag bekapcsolt állapotban van-e.
     * @param name a flag neve
     * @return a kiértékelés eredménye
     */
    boolean isOn(String name);
}
