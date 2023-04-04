package progressive.delivery.workshop.wish.create.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import progressive.delivery.workshop.account.persistence.Account;
import progressive.delivery.workshop.dish.persistence.Dish;
import progressive.delivery.workshop.wish.persistence.Wish;

@ApplicationScoped
@Slf4j
public class DefaultWishCreate implements WishCreate {
    @Inject
    Mapper mapper;

    @Override
    @Transactional
    public CreatedWish create(final CreationData creationData) {
        log.info("Creating wish for user {}", creationData.account());

        final var wish =
                Wish.add(Account.findByUsernameOptional(creationData.account()).get());

        Dish.streamAllWithName(creationData.dishes()).forEach(wish::addDish);

        wish.persist();

        return mapper.map(wish);
    }
}
