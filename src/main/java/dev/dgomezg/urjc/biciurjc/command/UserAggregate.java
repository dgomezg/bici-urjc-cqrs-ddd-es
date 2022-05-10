package dev.dgomezg.urjc.biciurjc.command;

import dev.dgomezg.urjc.biciurjc.coreapi.RegisterUserCommand;
import dev.dgomezg.urjc.biciurjc.coreapi.UserCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String userId;

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        AggregateLifecycle.apply(new UserCreatedEvent(command.getUserId(), command.getFullName()));
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        this.userId = event.getUserId();
    }

}
