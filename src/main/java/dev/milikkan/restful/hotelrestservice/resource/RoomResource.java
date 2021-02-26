package dev.milikkan.restful.hotelrestservice.resource;

import dev.milikkan.restful.hotelrestservice.controller.booking.BookingController;
import dev.milikkan.restful.hotelrestservice.controller.room.RoomController;
import dev.milikkan.restful.hotelrestservice.entity.Room;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class RoomResource extends RepresentationModel<RoomResource> {

    private final Room room;

    public RoomResource(Room room) {
        this.room = room;
        Long id = room.getId();
        add(linkTo(RoomController.class).withRel("rooms"));
        add(linkTo(methodOn(BookingController.class).all(id)).withRel("bookings"));
        add(linkTo(methodOn(RoomController.class).get(id)).withSelfRel());
    }

    public Room getRoom() {
        return room;
    }
}
