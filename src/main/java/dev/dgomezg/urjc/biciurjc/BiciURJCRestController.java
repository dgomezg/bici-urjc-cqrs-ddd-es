package dev.dgomezg.urjc.biciurjc;

import dev.dgomezg.urjc.biciurjc.coreapi.*;
import dev.dgomezg.urjc.biciurjc.query.bikes.BikeInfo;
import dev.dgomezg.urjc.biciurjc.query.user.UserInfo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
public class BiciURJCRestController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public BiciURJCRestController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/bikes")
    public Future<Void> registerBike(@RequestBody BikeRegistration bike) {
        return commandGateway.send(
                new RegisterBikeCommand(
                        bike.getBikeId()!=null?bike.getBikeId():UUID.randomUUID().toString(),
                        bike.getLocation()
                )
        );
    }

    @GetMapping("/bikes")
    public CompletableFuture<List<BikeInfo>> getBikes() {
        return queryGateway.query(new AllBikesQuery(), ResponseTypes.multipleInstancesOf(BikeInfo.class));
    }

    @GetMapping("/bikes/{bikeId}")
    public CompletableFuture<BikeInfo> getBike(@PathVariable String bikeId)  {
        return queryGateway.query(new BikeQuery(bikeId), ResponseTypes.instanceOf(BikeInfo.class));
    }

    @PutMapping("/bikes/{bikeId}/rental")
    public Future<Void> rentBike(@PathVariable String bikeId, @RequestBody UserRegistration user) {
        Assert.notNull(user.getUserId(), "The userId for the renter is mandatory");
        return commandGateway.send(new RentBikeCommand(bikeId, user.getUserId()));
    }

    @PutMapping("/bikes/{bikeId}/return")
    public Future<Void> returnBike(@PathVariable String bikeId, @RequestParam String location){
        Assert.notNull(location, "return location for the bike should be specified");
        return commandGateway.send(new ReturnBikeCommand(bikeId, location));
    }

    @PostMapping("/users")
    public Future<Void> registerUser(@RequestBody UserRegistration user) {
        return commandGateway.send(
                new RegisterUserCommand(
                        user.getUserId()!=null? user.getUserId() : UUID.randomUUID().toString(),
                        user.getFullName()
                ));
    }

    @GetMapping("/users")
    public CompletableFuture<List<UserInfo>> listUsers() {
        return queryGateway.query(new AllUsersQuery(), ResponseTypes.multipleInstancesOf(UserInfo.class));
    }

    @GetMapping("/users/{userId}")
    public CompletableFuture<UserInfo> getUser(@PathVariable String userId) {
        return queryGateway.query(new UserQuery(userId), ResponseTypes.instanceOf(UserInfo.class));
    }

    public static class BikeRegistration {
        private String bikeId;
        private String location = "Unknown";

        public String getBikeId() {
            return bikeId;
        }

        public void setBikeId(String bikeId) {
            this.bikeId = bikeId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public static class UserRegistration {
        private String userId;
        private String fullName;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
