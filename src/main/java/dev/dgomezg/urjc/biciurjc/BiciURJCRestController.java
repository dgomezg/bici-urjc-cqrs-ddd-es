package dev.dgomezg.urjc.biciurjc;

import dev.dgomezg.urjc.biciurjc.bike.Bike;
import dev.dgomezg.urjc.biciurjc.bike.BikeRepository;
import dev.dgomezg.urjc.biciurjc.user.User;
import dev.dgomezg.urjc.biciurjc.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.UUID;

@RestController
public class BiciURJCRestController {

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/bikes")
    public String registerBike(@RequestBody Bike bike) {
        if (bike.getBikeId() == null) {
            bike.setBikeId(UUID.randomUUID().toString());
        }
        bikeRepository.save(bike);
        return bike.getBikeId();
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
    public void rentBike(@PathVariable String bikeId, @RequestBody User user) {
        bikeRepository.findById(bikeId).ifPresent( b ->
           userRepository.findById(user.getUserId()).ifPresent( u -> {
               b.setStatus(Bike.BikeStatus.RENTED);
               b.setRentedBy(u);
               bikeRepository.save(b);
           })
        );

        /* Code before is equivalent to this longer version
        Optional<Bike> bike = bikeRepository.findById(bikeId);
        Optional<User> user = userRepository.findById(userId);

        if (bike.isPresent() && user.isPresent()) {
            Bike b = bike.get();
            b.setStatus(Bike.BikeStatus.RENTED);
            b.setRentedBy(user.get());
             bikeRepository.save(b);
         }
         */
    }

    @PutMapping("/bikes/{bikeId}/return")
    public void returnBike(@PathVariable String bikeId, @RequestParam String location){
        bikeRepository.findById(bikeId).ifPresent(b -> {
                b.setStatus(Bike.BikeStatus.AVAILABLE);
                b.setLocation(location);
                b.setRentedBy(null);
                bikeRepository.save(b);
        });
    }

    @PostMapping("/users")
    public String registerUser(@RequestBody User user) {
        if (user.getUserId() == null) {
            user.setUserId(UUID.randomUUID().toString());
        }
        userRepository.save(user);
        return user.getUserId();
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

}
