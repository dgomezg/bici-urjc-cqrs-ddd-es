package dev.dgomezg.urjc.biciurjc.query.user.ranking;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserRanking {

    @Id
    private String userId;
    private int numOfRentals = 0;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private List<BikeRental> rental = new ArrayList<>();

    public UserRanking() {
    }

    public UserRanking(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNumOfRentals() {
        return numOfRentals;
    }

    public void setNumOfRentals(int numOfRentals) {
        this.numOfRentals = numOfRentals;
    }

    public List<BikeRental> getRental() {
        return rental;
    }

    public void setRental(List<BikeRental> rental) {
        this.rental = rental;
    }

    public void addRental(BikeRental newRental) {
        this.rental.add(newRental);
    }
}
