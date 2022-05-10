package dev.dgomezg.urjc.biciurjc.query.bikes;

import dev.dgomezg.urjc.biciurjc.coreapi.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BikeInfoProjection {

    private final BikeInfoRepository repository;

    public BikeInfoProjection(BikeInfoRepository bikeInfoRepository) {
        this.repository = bikeInfoRepository;
    }

    @EventHandler
    public void on(BikeCreatedEvent event){
        BikeInfo bikeInfo = new BikeInfo();
        bikeInfo.setBikeId(event.getBikeId());
        bikeInfo.setLocation(event.getLocation());
        bikeInfo.setStatus(BikeStatus.AVAILABLE);
        repository.save(bikeInfo);
    }

    @EventHandler
    public void on(BikeRentedEvent event) {
        BikeInfo bikeInfo = repository.getById(event.getBikeId());
        bikeInfo.setStatus(BikeStatus.RENTED);
        bikeInfo.setRentedByUser(event.getUserId());
        repository.save(bikeInfo);
    }

    @EventHandler
    public void on(BikeReturnedEvent event) {
        BikeInfo bikeInfo = repository.getById(event.getBikeId());
        bikeInfo.setRentedByUser(null);
        bikeInfo.setStatus(BikeStatus.AVAILABLE);
        bikeInfo.setLocation(event.getLocation());
    }

    @QueryHandler
    public List<BikeInfo> handleQuery(AllBikesQuery query) {
        return repository.findAll();
    }

    @QueryHandler
    public BikeInfo handle(BikeQuery query) {
        return repository.getById(query.getBikeId());
    }

}
