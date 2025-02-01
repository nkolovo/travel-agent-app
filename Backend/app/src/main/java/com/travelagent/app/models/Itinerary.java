package com.travelagent.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "Itinerary")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate createdDate;
    private LocalDate dateSold;
    private String reservationNumber;
    private String leadName;
    private int numTravelers;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int tripPrice;
    private String status;
    private boolean docsSent;

    @Lob
    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;

    private String imageType; // Stores MIME type (e.g., image/png, image/jpeg)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Date> dates;

    public Itinerary() {
    }

    // Getters and Setters
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}