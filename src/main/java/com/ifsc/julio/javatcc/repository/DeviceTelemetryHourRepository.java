package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryHourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.UUID;

@Repository
public interface DeviceTelemetryHourRepository extends JpaRepository<DeviceTelemetryHourEntity, UUID>, DeviceTelemetryHourRepositoryCustom {

    @Query(value = "SELECT COUNT(*) FROM device_telemetry_hour WHERE date = :date AND id_station = :stationId", nativeQuery = true)
    Integer countByDate(@Param("date") Date date, @Param("stationId") UUID stationId);
}
