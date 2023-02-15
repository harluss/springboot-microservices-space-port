package com.project.hangar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spaceships")
public class SpaceshipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", nullable = false, columnDefinition = "text")
  private String name;

  @Column(name = "payload", nullable = false, columnDefinition = "text")
  private Integer payload;

  @Column(name = "crew", nullable = false, columnDefinition = "text")
  private Integer crew;
}
