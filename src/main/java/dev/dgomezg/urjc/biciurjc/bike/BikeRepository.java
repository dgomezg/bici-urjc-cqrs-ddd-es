package dev.dgomezg.urjc.biciurjc.bike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, String> {
}
