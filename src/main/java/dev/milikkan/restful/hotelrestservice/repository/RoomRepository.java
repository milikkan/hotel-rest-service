package dev.milikkan.restful.hotelrestservice.repository;

import dev.milikkan.restful.hotelrestservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
