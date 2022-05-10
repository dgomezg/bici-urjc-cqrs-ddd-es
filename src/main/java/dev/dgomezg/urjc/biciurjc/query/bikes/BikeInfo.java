package dev.dgomezg.urjc.biciurjc.query.bikes;

import dev.dgomezg.urjc.biciurjc.coreapi.BikeStatus;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BikeInfo {

    @Id
    private String bikeId;
    private String location;
    private BikeStatus status;
    private String rentedByUser;

    public BikeInfo() {
    }

    public BikeInfo(String bikeId, String location, BikeStatus status) {
        this.bikeId = bikeId;
        this.location = location;
        this.status = status;
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

    public String getRentedByUser() {
        return rentedByUser;
    }

    public void setRentedByUser(String rentedByUser) {
        this.rentedByUser = rentedByUser;
    }
}
