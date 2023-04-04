package progressive.delivery.workshop.featureflag.adjust.service;

/**
 * Flagek valószínűségének beállítása.
 */
public interface FeatureFlagAdjust {
    /**
     * Beállítja a megadott nevű flag valószínűségét a kapott értékre.
     * @param name a beállítandó flag neve
     * @param percentage a kívánt valószínűség
     */
    void to(String name, int percentage);
}
