package com.itplanet.contest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "Types",
        uniqueConstraints = {@UniqueConstraint(name = "types_type_unique_constraint", columnNames = "type")}
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Type extends BaseEntity{
    private String type;
}
