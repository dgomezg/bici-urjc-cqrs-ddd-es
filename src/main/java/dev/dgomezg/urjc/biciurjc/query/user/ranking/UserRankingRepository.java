package dev.dgomezg.urjc.biciurjc.query.user.ranking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRankingRepository extends JpaRepository<UserRanking, String> {

    List<UserRanking> findTop10ByOrderByNumOfRentalsDesc();

    List<UserRanking> findAllByOrderByNumOfRentals();
}
