package dev.dgomezg.urjc.biciurjc;

import com.github.javafaker.Faker;
import dev.dgomezg.urjc.biciurjc.query.bikes.BikeInfo;
import dev.dgomezg.urjc.biciurjc.query.user.UserInfo;
import io.netty.util.internal.ThreadLocalRandom;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/activity-simulator")
public class SimulatorController {

    private final BiciURJCRestController controller;

    public SimulatorController(BiciURJCRestController controller) {
        this.controller = controller;
    }

    @PostMapping("/users-n-bikes")
    public void generateBikesAndUsers(@RequestParam int numUsers, @RequestParam int numBikes) {
        Faker generator = new Faker();
        Stream.generate(() ->
                        new BiciURJCRestController.UserRegistration(generator.name().username(), generator.name().fullName()))
                .limit(numUsers)
                .forEach(controller::registerUser);

        Stream.generate(() ->
                        new BiciURJCRestController.BikeRegistration(UUID.randomUUID().toString(), generator.address().city()))
                .limit(numBikes)
                .forEach(controller::registerBike);
    }


    @PostMapping("/bike-rentals")
    public void simulateActivity(@RequestParam int numOfRentals) throws ExecutionException, InterruptedException {
        List<BikeInfo> bikeInfos = controller.listBikes().get();
        List<UserInfo> userInfos = controller.listUsers().get();

        Faker generator = new Faker();
        ThreadLocalRandom.current().ints(numOfRentals, 0, userInfos.size() -1)
                .forEach(n -> {
                            UserInfo u = userInfos.get(n);
                            BikeInfo bike = bikeInfos.get(ThreadLocalRandom.current().nextInt(0, bikeInfos.size()) -1);
                            controller.rentBike(bike.getBikeId(), new BiciURJCRestController.UserRegistration(u.getUserId(), u.getFullName()));
                            controller.returnBike(bike.getBikeId(), generator.address().city());
                        }
                );
    }
}
