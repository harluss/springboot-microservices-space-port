package com.project.hangar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

  @Column(name = "name", unique = true, nullable = false, columnDefinition = "text")
  private String name;

  @Column(name = "class", nullable = false, columnDefinition = "text")
  @JsonProperty("class")
  private String classType;

  @Column(name = "payload", nullable = false, columnDefinition = "text")
  private Integer payload;

  @Column(name = "crew", nullable = false, columnDefinition = "text")
  private Integer crew;
}
