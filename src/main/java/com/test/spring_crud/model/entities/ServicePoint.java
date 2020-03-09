package com.test.spring_crud.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicePoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            targetEntity = Service.class
    )
    @JoinTable(
            name = "SERVICE_POINTS_SERVICES",
            joinColumns = @JoinColumn(name = "SP_ID"),
            inverseJoinColumns = @JoinColumn(name = "SERVICE_ID")
    )
    private Set<Service> serviceSet = new HashSet<>();

    private String name;

    @Column(nullable = false)
    private String address;

    public void setServiceSet(Set<Service> serviceSet) {
        this.serviceSet = serviceSet;
        serviceSet.forEach(service -> service.getServicePoints().add(this));
    }
}
