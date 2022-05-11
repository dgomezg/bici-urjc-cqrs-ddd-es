package dev.dgomezg.urjc.biciurjc.query.user.ranking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class BikeRental {

    @Id @GeneratedValue
    private long bikeRentalId;

    private String bikeId;
    private Instant rented;

    public BikeRental() {
    }

    public BikeRental(String bikeId, Instant rented) {
        this.bikeId = bikeId;
        this.rented = rented;
    }

    public long getBikeRentalId() {
        return bikeRentalId;
    }

    public void setBikeRentalId(long bikeRentalId) {
        this.bikeRentalId = bikeRentalId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public Instant getRented() {
        return rented;
    }

    public void setRented(Instant rented) {
        this.rented = rented;
    }

}
