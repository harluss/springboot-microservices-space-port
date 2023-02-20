package com.project.hangar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spaceships")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class SpaceshipEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", nullable = false, columnDefinition = "text")
  private String name;

  @Column(name = "class", nullable = false, columnDefinition = "text")
  @JsonProperty("class")
  private String classType;

  @Column(name = "max_speed", nullable = false, columnDefinition = "integer")
  private Integer maxSpeed;

  @Column(name = "payload", nullable = false, columnDefinition = "integer")
  private Integer payload;

  @Column(name = "crew", nullable = true, columnDefinition = "uuid[]")
  @Type(type = "list-array")
  private List<UUID> crew;

  @Column(name = "armament", nullable = true, columnDefinition = "text[]")
  @Type(type = "list-array")
  private List<String> armament;
}
