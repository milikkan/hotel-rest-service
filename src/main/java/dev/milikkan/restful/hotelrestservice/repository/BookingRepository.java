package dev.milikkan.restful.hotelrestservice.repository;

import dev.milikkan.restful.hotelrestservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
