package dev.milikkan.restful.hotelrestservice.bootstrap;

import dev.milikkan.restful.hotelrestservice.entity.Booking;
import dev.milikkan.restful.hotelrestservice.entity.Room;
import dev.milikkan.restful.hotelrestservice.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DummyLoader implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public DummyLoader(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        roomRepository.save(new Room(2, 100, null));
        roomRepository.save(new Room(2, 120, null));
        roomRepository.save(new Room(3, 150, null));
        roomRepository.save(new Room(4, 170, null));
    }
}
