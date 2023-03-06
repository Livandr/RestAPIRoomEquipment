package com.technobel.restapiroomequipment.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "equipment")
@Getter @Setter
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "equipment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "equipments")
    private Set<Room> rooms = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "equipments")
    private Set<Request> reservations = new  LinkedHashSet<>();
}
