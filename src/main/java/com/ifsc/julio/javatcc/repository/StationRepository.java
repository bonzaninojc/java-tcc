package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StationRepository extends JpaRepository<StationEntity, UUID>, StationRepositoryCustom {
}
