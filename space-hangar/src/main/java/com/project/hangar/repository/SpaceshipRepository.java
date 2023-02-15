package com.project.hangar.repository;

import com.project.hangar.entity.SpaceshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipEntity, UUID> {

}
