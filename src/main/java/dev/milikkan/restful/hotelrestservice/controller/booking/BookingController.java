package dev.milikkan.restful.hotelrestservice.controller.booking;

import dev.milikkan.restful.hotelrestservice.controller.room.RoomController;
import dev.milikkan.restful.hotelrestservice.entity.Booking;
import dev.milikkan.restful.hotelrestservice.exception.BookingNotFoundException;
import dev.milikkan.restful.hotelrestservice.exception.RoomNotFoundException;
import dev.milikkan.restful.hotelrestservice.repository.BookingRepository;
import dev.milikkan.restful.hotelrestservice.repository.RoomRepository;
import dev.milikkan.restful.hotelrestservice.resource.BookingResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rooms/{roomId}/bookings", produces = "application/hal+json")
public class BookingController {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public BookingController(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<BookingResource>> all(@PathVariable Long roomId) {
        List<BookingResource> collection = roomRepository.findById(roomId)
                .map(room -> room.getBookings()
                        .stream()
                        .map(BookingResource::new)
                        .collect(Collectors.toList())
                ).orElseThrow(() -> new RoomNotFoundException(roomId));

        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        CollectionModel<BookingResource> body = CollectionModel.of(collection, Link.of(uriString, "self"));

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResource> get(@PathVariable Long roomId, @PathVariable Long bookingId) {
        return roomRepository.findById(roomId)
                .map(room -> room.getBookings()
                        .stream()
                        .filter(booking -> booking.getId().equals(bookingId))
                        .findAny()
                        .map(booking -> ResponseEntity.ok(new BookingResource(booking)))
                        .orElseThrow(() -> new BookingNotFoundException(bookingId))
                ).orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @PostMapping
    public ResponseEntity<BookingResource> post(@PathVariable Long roomId, @RequestBody Booking input) {
        return roomRepository.findById(roomId)
                .map(room -> {
                    if (RoomController.isAvailable(room, input.getCheckIn(), input.getCheckOut())) {
                        Booking booking = bookingRepository.save(
                                new Booking(room, input.getCustomerName(), input.getCustomerPhone(),
                                        input.getCheckIn(), input.getCheckOut(), input.getPricePerNight())
                        );

                    URI uri = MvcUriComponentsBuilder.fromController(this.getClass()).path("/{bookingId}")
                            .buildAndExpand(booking.getRoom().getId(), booking.getId()).toUri();

                    return ResponseEntity.created(uri).body(new BookingResource(booking));
                    } else {
                        throw new IllegalArgumentException("Room is not available for the supplied dates");
                    }
                }).orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResource> put(@PathVariable Long roomId, @PathVariable Long bookingId, @RequestBody Booking input) {
        return roomRepository.findById(roomId)
                .map(room -> {
                    if (RoomController.isAvailable(room, input.getCheckIn(), input.getCheckOut())) {
                        Booking booking = bookingRepository.save(
                                new Booking(bookingId, room, input.getCustomerName(),
                                        input.getCustomerPhone(), input.getCheckIn(),
                                        input.getCheckOut(), input.getPricePerNight()));
                        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                        return ResponseEntity.created(uri).body(new BookingResource(booking));
                    } else {
                        throw new IllegalArgumentException("Room is not available for the supplied dates");
                    }
                }).orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> delete(@PathVariable Long roomId, @PathVariable Long bookingId) {
        return roomRepository.findById(roomId)
                .map(room -> room.getBookings()
                        .stream()
                        .filter(booking -> booking.getId().equals(bookingId))
                        .findAny()
                        .map(booking -> {
                            bookingRepository.deleteById(booking.getId());
                            return ResponseEntity.noContent().build();
                        })
                        .orElseThrow(() -> new BookingNotFoundException(bookingId))
                ).orElseThrow(() -> new RoomNotFoundException(roomId));
    }
}
