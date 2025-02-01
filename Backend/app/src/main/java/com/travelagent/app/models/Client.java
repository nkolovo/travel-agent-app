package com.travelagent.app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Itinerary> itineraries;
}
