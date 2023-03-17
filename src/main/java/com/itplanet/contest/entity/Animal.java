package com.itplanet.contest.entity;

import com.itplanet.contest.entity.enumeration.Gender;
import com.itplanet.contest.entity.enumeration.LifeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "Animals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Animal extends BaseEntity {
    @ManyToMany
    @JoinTable(
            name = "animal_types",
            joinColumns = {@JoinColumn(name = "animal_id")},
            inverseJoinColumns = {@JoinColumn(name = "type_id")}
    )
    Set<Type> animalTypes = new HashSet<>();
    Float weight;
    Float length;
    Float height;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Enumerated(EnumType.STRING)
    LifeStatus lifeStatus;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "chipping_date_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    OffsetDateTime chippingDateTime;
    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    Account chipperId;
    @ManyToOne
    @JoinColumn(name = "fk_location_id")
    Location chippingLocationId;
    @ManyToMany(mappedBy = "animal")
    List<Visited> visited = new ArrayList<>();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    OffsetDateTime deathDateTime;
    public Animal(Set<Type> animalTypes,
                  Float weight,
                  Float length,
                  Float height,
                  Gender gender,
                  Account chipperId,
                  Location chippingLocationId){
        this.animalTypes = animalTypes;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.gender = gender;
        this.lifeStatus = LifeStatus.ALIVE;
        this.chippingDateTime = OffsetDateTime.now();
        this.chipperId = chipperId;
        this.chippingLocationId = chippingLocationId;
        this.deathDateTime = null;
    }

    public void addType(Type type){
        animalTypes.add(type);
    }
}
