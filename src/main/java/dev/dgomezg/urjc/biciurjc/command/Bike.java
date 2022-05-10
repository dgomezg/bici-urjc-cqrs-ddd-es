package dev.dgomezg.urjc.biciurjc.command;

import dev.dgomezg.urjc.biciurjc.coreapi.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

@Aggregate
public class Bike {

    @AggregateIdentifier
    private String bikeId;
    private String rentedBy;

    public Bike() {
    }

    @CommandHandler
    public Bike(RegisterBikeCommand command) {
        AggregateLifecycle.apply(new BikeCreatedEvent(command.getBikeId(), command.getLocation()));
    }

    @CommandHandler
    public void on(RentBikeCommand command){
        Assert.state(rentedBy == null, "Can't rent a bike that is already rented by another user");
        AggregateLifecycle.apply(new BikeRentedEvent(command.getBikeId(), command.getUserId()));
    }

    @CommandHandler
    public void on(ReturnBikeCommand command) {
        Assert.state(rentedBy != null, "Can't return a bike that is not rented");
        AggregateLifecycle.apply(new BikeReturnedEvent(command.getBikeId(), command.getLocation() ));
    }

    @EventSourcingHandler
    public void handle(BikeCreatedEvent event) {
        this.bikeId = event.getBikeId();
    }

    @EventSourcingHandler
    public void handle(BikeReturnedEvent event) {
        this.rentedBy = null;
    }

    @EventSourcingHandler
    public void handle(BikeRentedEvent event) {
        this.rentedBy = event.getUserId();
    }
}
