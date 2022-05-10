package dev.dgomezg.urjc.biciurjc.query.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserInfo {

    @Id
    private String userId;
    private String fullName;
    private String bikeRented;

    public UserInfo() {
    }

    public UserInfo(String userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;
    }

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

    public String getBikeRented() {
        return bikeRented;
    }

    public void setBikeRented(String bikeRented) {
        this.bikeRented = bikeRented;
    }
}
