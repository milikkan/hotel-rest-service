package dev.milikkan.restful.hotelrestservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Room room;

    private String customerName;

    private String customerPhone;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;

    private double pricePerNight;

    // no-arg constructor
    public Booking() {
    }

    // custom constructor
    public Booking(Room room, String customerName, String customerPhone, LocalDate checkIn, LocalDate checkOut, double pricePerNight) {
        this.room = room;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pricePerNight = pricePerNight;
    }

    // all args constructor
    public Booking(Long id, Room room, String customerName, String customerPhone, LocalDate checkIn, LocalDate checkOut, double pricePerNight) {
        this(room, customerName, customerPhone, checkIn, checkOut, pricePerNight);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}
