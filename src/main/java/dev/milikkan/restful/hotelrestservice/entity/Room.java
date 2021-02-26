package dev.milikkan.restful.hotelrestservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;

    private double price;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    // no -arg constructor
    public Room() {
    }

    // all-args constructor
    public Room(int capacity, double price, List<Booking> bookings) {
        this.capacity = capacity;
        this.price = price;
        this.bookings = bookings;
    }

    public Room(Room room, Long id) {
        this.id = id;
        this.capacity = room.getCapacity();
        this.price = room.getPrice();
        this.bookings = room.getBookings();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
