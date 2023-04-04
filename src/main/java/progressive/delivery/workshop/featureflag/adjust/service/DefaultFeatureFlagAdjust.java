package progressive.delivery.workshop.featureflag.adjust.service;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.featureflag.persistence.FeatureFlag;

@ApplicationScoped
@Slf4j
public class DefaultFeatureFlagAdjust implements FeatureFlagAdjust {
    @Override
    @Transactional
    public void to(final String name, final int percentage) {
        log.info("Setting percentage of flag {} to {}", name, percentage);

        FeatureFlag.findByNameOptional(name).ifPresent(featureFlag -> {
            featureFlag.setPercentage(percentage);
            featureFlag.persist();
        });
    }
}
