package dev.dgomezg.urjc.biciurjc;

import dev.dgomezg.urjc.biciurjc.bike.Bike;
import dev.dgomezg.urjc.biciurjc.bike.BikeRepository;
import dev.dgomezg.urjc.biciurjc.coreapi.*;
import dev.dgomezg.urjc.biciurjc.user.User;
import dev.dgomezg.urjc.biciurjc.user.UserRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Future;

@RestController
public class BiciURJCRestController {

    private final CommandGateway commandGateway;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    UserRepository userRepository;

    public BiciURJCRestController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/bikes")
    public Future<Void> registerBike(@RequestBody BikeRegistration bike) {
        return commandGateway.send(
                new RegisterBikeCommand(
                        bike.getBikeId()!=null?bike.getBikeId():UUID.randomUUID().toString(),
                        bike.getLocation(),
                        BikeStatus.AVAILABLE
                )
        );
    }

    @GetMapping("/bikes")
    public Collection<Bike> getBikes() {
        return bikeRepository.findAll();
    }

    @GetMapping("/bikes/{bikeId}")
    public Bike getBike(@PathVariable String bikeId)  {
        return bikeRepository.findById(bikeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bike " + bikeId + " not found"));
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
    public Collection<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userId + " not found"));
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
        @NotEmpty
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
