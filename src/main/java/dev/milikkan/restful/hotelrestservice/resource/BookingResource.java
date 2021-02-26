package dev.milikkan.restful.hotelrestservice.resource;

import dev.milikkan.restful.hotelrestservice.controller.booking.BookingController;
import dev.milikkan.restful.hotelrestservice.controller.room.RoomController;
import dev.milikkan.restful.hotelrestservice.entity.Booking;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class BookingResource extends RepresentationModel<BookingResource> {

    private final Booking booking;

    public BookingResource(Booking booking) {
        this.booking = booking;
        Long bookingId = booking.getId();
        Long roomId = booking.getRoom().getId();
        add(Link.of(String.valueOf(bookingId), "booking-id"));
        add(linkTo(methodOn(BookingController.class).all(roomId)).withRel("bookings"));
        add(linkTo(methodOn(RoomController.class).get(roomId)).withRel("room"));
        add(linkTo(methodOn(BookingController.class).get(roomId, bookingId)).withSelfRel());
    }

    public Booking getBooking() {
        return booking;
    }
}
