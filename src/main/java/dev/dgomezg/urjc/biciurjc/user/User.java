package dev.dgomezg.urjc.biciurjc.user;

import dev.dgomezg.urjc.biciurjc.bike.Bike;

import javax.persistence.*;

@Entity
public class User {

    @Id
    private String userId;
    private String fullName;


    public User() {
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

}
