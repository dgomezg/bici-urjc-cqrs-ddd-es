package dev.dgomezg.urjc.biciurjc.query.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findUserInfoByBikeRented(String bikeId);

}
