package dev.dgomezg.urjc.biciurjc.query.bikes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeInfoRepository extends JpaRepository<BikeInfo, String> {
}
