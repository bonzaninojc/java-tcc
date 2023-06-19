package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.entity.DeviceTelemetryDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DeviceTelemetryDayRepository extends JpaRepository<DeviceTelemetryDayEntity, UUID>, DeviceTelemetryDayRepositoryCustom {
}
