# HOTEL REST SERVICE

This is a demo RESTful web service that was created as a hobby project. 
Application simply depicts a one-to-many relationship between hotel **room** and **booking.**  

This API is fully RESTful as it supports HATEOAS. Incoming JSON responses include available endpoints as a seperate *links* object. 

Some dummy data is loaded when the app fires up. 

You can use *Postman* or other tools to try endpoints and play with HTTP methods.

You can check the database by visitinh localhost:8080/h2-console. Just click OK to see the H2 web interface.

### Setup

Nothing special. You can use `mvnw` wrapper that is included in the repo, or your own `mvn` command if you have it on your system.  

Just use `./mvnw spring-boot:run` and wait for the Spring Boot application to compile and run.

### Tools Used
- Spring Web
- Spring Data JPA
- Spring HATEOAS
- H2 in-memory db

### Available Endpoints
*You can also traverse available resources by using the response payload*
- **GET /rooms** (get all rooms, you can search rooms using `Query Strings` as the search params. Check `RoomController.java` class and look at `all()` method there)
- **GET /rooms/{roomId}** (return spesific room)
- **POST /rooms** (save a new room)
- **PUT /rooms/{roomId}** (update specific room)
- **DELETE /rooms/{roomId}** (delete specific room)


- **GET /rooms/{roomId}/bookings** (get all bookings for a room)
- **GET /rooms/{roomId}/bookings/{bookingId}** (get specific booking for a room)
- **POST /rooms/{roomId}/bookings** (save new booking for a room)
- **PUT /rooms/{roomId}/bookings/{bookingId}** (update specific booking for a room)
- **DELETE /rooms/{roomId}/bookings/{bookingId}** (delete specific booking for a room)




