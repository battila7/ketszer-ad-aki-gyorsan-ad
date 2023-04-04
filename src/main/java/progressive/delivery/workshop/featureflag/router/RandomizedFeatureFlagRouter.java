package progressive.delivery.workshop.featureflag.router;

import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.featureflag.persistence.FeatureFlag;

@ApplicationScoped
@Slf4j
public class RandomizedFeatureFlagRouter implements FeatureFlagRouter {
    @Inject
    Random random;

    @Override
    public boolean isOn(final String name) {
        return FeatureFlag.findByNameOptional(name)
                .map(FeatureFlag::getPercentage)
                .map(percentage -> {
                    final int threshold = random.nextInt(100);

                    final boolean isOn = threshold < percentage;

                    log.info("Flag {} evaluated to {} ({} < {})", name, isOn, threshold, percentage);

                    return isOn;
                })
                .orElse(false);
    }
}
