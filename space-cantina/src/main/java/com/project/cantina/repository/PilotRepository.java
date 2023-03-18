package com.project.cantina.repository;

import com.project.cantina.entity.PilotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PilotRepository extends JpaRepository<PilotEntity, UUID> {

  Optional<PilotEntity> findByName(String name);
}
