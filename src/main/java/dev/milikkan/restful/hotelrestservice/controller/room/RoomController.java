package dev.milikkan.restful.hotelrestservice.controller.room;

import dev.milikkan.restful.hotelrestservice.entity.Booking;
import dev.milikkan.restful.hotelrestservice.entity.Room;
import dev.milikkan.restful.hotelrestservice.exception.RoomNotFoundException;
import dev.milikkan.restful.hotelrestservice.repository.RoomRepository;
import dev.milikkan.restful.hotelrestservice.resource.RoomResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/rooms", produces = "application/hal+json")
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<RoomResource>> all(
            @RequestParam(name = "checkIn", required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy")
                    Optional<LocalDate> checkIn,
            @RequestParam(name = "checkOut", required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy")
                    Optional<LocalDate> checkOut,
            @RequestParam(name = "numberOfPeople", required = false)
                    Optional<Integer> numberOfPeople
            )
    {
        int people = numberOfPeople.orElse(1);
        LocalDate start = checkIn.orElse(null);
        LocalDate end = checkOut.orElse(null);

        List<RoomResource> collection = roomRepository
                .findAll()
                .stream()
                .filter(room -> room.getCapacity() >= people)
                .filter(room -> isAvailable(room, start, end))
                .map(RoomResource::new)
                .collect(Collectors.toList());

        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toString();

        CollectionModel<RoomResource> body = CollectionModel.of(collection, Link.of(uriString, "self"));
        return ResponseEntity.ok(body);
    }

    public static boolean isAvailable(Room room, LocalDate start, LocalDate end) {
        if (start == null || end == null) return true;
        List<Booking> bookings = room.getBookings();
        for (Booking booking : bookings) {
            if (start.isBefore(booking.getCheckIn())) {
                if (!end.isBefore(booking.getCheckIn())) {
                    return false;
                }
            } else {
                if (!start.isAfter(booking.getCheckOut())) {
                    return false;
                }
            }
        }
        return true;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResource> get(@PathVariable Long roomId) {
        return roomRepository
                .findById(roomId)
                .map(room -> ResponseEntity.ok(new RoomResource(room)))
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @PostMapping
    public ResponseEntity<RoomResource> post(@RequestBody Room input) {
        Room room = roomRepository.save(input);

        URI uri = MvcUriComponentsBuilder.fromController(this.getClass())
                .path("/{roomId}").buildAndExpand(room.getId()).toUri();

        return ResponseEntity.created(uri).body(new RoomResource(room));
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResource> put(@PathVariable Long roomId, @RequestBody Room input) {
        Room room = new Room(input, roomId);
        roomRepository.save(room);
        RoomResource resource = new RoomResource(room);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(uri).body(resource);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> delete(@PathVariable Long roomId) {
        return roomRepository.findById(roomId)
                .map(room -> {
                    roomRepository.deleteById(roomId);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new RoomNotFoundException(roomId));
    }
}
