package com.itplanet.contest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visited extends BaseEntity{
    @ManyToOne
    Animal animal;
    @ManyToOne
    Location location;
    OffsetDateTime dateTimeOfVisitLocationPoint;
}
