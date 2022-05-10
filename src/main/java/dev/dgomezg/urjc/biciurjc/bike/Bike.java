package dev.dgomezg.urjc.biciurjc.bike;

import dev.dgomezg.urjc.biciurjc.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Bike {

    public enum BikeStatus {AVAILABLE, RENTED}

    @Id
    private String bikeId;

    private String location = "Unknown";
    private BikeStatus status = BikeStatus.AVAILABLE;

    @OneToOne
    @JoinColumn(name = "rented_by_user_id", nullable = true)
    private User rentedBy;

    public Bike() {
    }

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

    public BikeStatus getStatus() {
        return status;
    }

    public void setStatus(BikeStatus status) {
        this.status = status;
    }

    public void setRentedBy(User rentedBy) {
        this.rentedBy = rentedBy;
    }

    public User getRentedBy() {
        return rentedBy;
    }

}
