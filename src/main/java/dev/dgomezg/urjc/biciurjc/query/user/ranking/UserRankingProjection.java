package dev.dgomezg.urjc.biciurjc.query.user.ranking;

import dev.dgomezg.urjc.biciurjc.coreapi.BikeRentedEvent;
import dev.dgomezg.urjc.biciurjc.coreapi.Top10UsersQuery;
import dev.dgomezg.urjc.biciurjc.coreapi.UserCreatedEvent;
import dev.dgomezg.urjc.biciurjc.coreapi.UserRankingQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class UserRankingProjection {

    private final UserRankingRepository repository;

    public UserRankingProjection(UserRankingRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(UserCreatedEvent event) {
        repository.save(new UserRanking(event.getUserId()));
    }

    @EventHandler
    public void on(BikeRentedEvent event, @Timestamp Instant time) {
        UserRanking userRanking = repository.findById(event.getUserId())
                .orElseThrow(IllegalStateException::new);
        userRanking.addRental(new BikeRental(event.getBikeId(), time));
        userRanking.setNumOfRentals(userRanking.getNumOfRentals()+1);
        repository.save(userRanking);
    }

    @QueryHandler
    public List<UserRanking> handle(Top10UsersQuery query) {
        return repository.findTop10ByOrderByNumOfRentalsDesc();
    }

    @QueryHandler
    public UserRanking handle(UserRankingQuery query) {
        return repository.findById(query.getUserId()).get();
    }

}
