package com.ifsc.julio.javatcc.repository;

import com.ifsc.julio.javatcc.entity.LostDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface LostDataRepository extends JpaRepository<LostDataEntity, UUID> {
}
