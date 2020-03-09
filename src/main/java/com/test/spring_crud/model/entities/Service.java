package com.test.spring_crud.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "services")
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(
            cascade = {CascadeType.MERGE},
            mappedBy = "serviceSet",
            targetEntity = ServicePoint.class
    )
    private Set<ServicePoint> servicePoints = new HashSet<>();
}
