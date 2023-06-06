package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.entity.LostDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface LostDataRepository extends JpaRepository<LostDataEntity, UUID> {

    @Query(value = "SELECT * FROM lost_data ld WHERE ld.id_station = :stationId", nativeQuery = true)
    List<LostDataEntity> findByStationId(@Param("stationId") UUID stationId);

}
