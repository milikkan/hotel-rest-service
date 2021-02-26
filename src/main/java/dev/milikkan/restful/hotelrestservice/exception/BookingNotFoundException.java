package dev.milikkan.restful.hotelrestservice.exception;

public class BookingNotFoundException extends RuntimeException {
    private final Long id;

    public BookingNotFoundException(Long id) {
        super("Booking #" + id + " could not be found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
