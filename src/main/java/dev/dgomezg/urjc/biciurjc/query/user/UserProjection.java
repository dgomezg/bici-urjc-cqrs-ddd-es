package dev.dgomezg.urjc.biciurjc.query.user;

import dev.dgomezg.urjc.biciurjc.coreapi.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProjection {

    private final UserInfoRepository repository;

    public UserProjection(UserInfoRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(UserCreatedEvent event){
        UserInfo userInfo = new UserInfo(event.getUserId(), event.getFullName());
        repository.save(userInfo);
    }

    @EventHandler
    public void on(BikeRentedEvent event) {
        repository.findById(event.getUserId())
                .ifPresent(userInfo -> {
                    userInfo.setBikeRented(event.getBikeId());
                    repository.save(userInfo);
                });
    }

    @EventHandler
    public void on(BikeReturnedEvent event) {
        repository.findUserInfoByBikeRented(event.getBikeId())
                .ifPresent(bikeInfo -> {
                    bikeInfo.setBikeRented(null);
                    repository.save(bikeInfo);
                });
    }

    @QueryHandler
    public List<UserInfo> handle(AllUsersQuery query) {
        return repository.findAll();
    }

    @QueryHandler
    public UserInfo handle(UserQuery query) {
        return repository.getById(query.getUserId());
    }

}
