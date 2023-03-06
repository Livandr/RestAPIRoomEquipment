package com.technobel.restapiroomequipment.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "room")
@Getter @Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private boolean teacherOnly;

    @OneToMany(mappedBy = "room")
    private Set<Request> reservation;

    @ManyToMany
    @JoinTable(name = "classroom-equipment",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id"))
    private Set<Equipment> equipments = new LinkedHashSet<>();

//    public Integer getNumber() {
//    }
}
