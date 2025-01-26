package com.example.library.controller;


import com.example.library.model.Book;
import com.example.library.model.Reservation;
import com.example.library.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = reservationService.createReservation(reservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @PutMapping("/{id}/fullfill")
    public ResponseEntity<Reservation> fullfillReservation(@PathVariable Long id){
        Reservation updatedReservation = reservationService.fullfillReservation(id);
        return ResponseEntity.ok(updatedReservation);
    }

}
