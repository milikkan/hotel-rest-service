package dev.milikkan.restful.hotelrestservice.exception;

public class RoomNotFoundException extends RuntimeException {
    private final Long id;

    public RoomNotFoundException(Long id) {
        super("Room #" + id + " could not be found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
