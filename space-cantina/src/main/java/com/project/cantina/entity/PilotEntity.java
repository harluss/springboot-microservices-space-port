package com.project.cantina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pilots")
public class PilotEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", nullable = false, columnDefinition = "text")
  private String name;

  @Column(name = "species", nullable = false, columnDefinition = "text")
  private String species;

  @Column(name = "profession", nullable = false, columnDefinition = "text")
  private String profession;

  @Column(name = "weapons", nullable = true, columnDefinition = "text[]")
  @ElementCollection
  private List<String> weapons;
}
