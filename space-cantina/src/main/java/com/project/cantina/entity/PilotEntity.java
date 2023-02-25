package com.project.cantina.entity;

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
@Table(name = "pilots")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class PilotEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", nullable = false, unique = true, columnDefinition = "text")
  private String name;

  @Column(name = "species", nullable = false, columnDefinition = "text")
  private String species;

  @Column(name = "profession", nullable = false, columnDefinition = "text")
  private String profession;

  @Column(name = "weapons", nullable = true, columnDefinition = "text[]")
  @Type(type = "list-array")
  private List<String> weapons;
}
