package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Reservation;
import com.example.library.model.enums.Status;
import com.example.library.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;
    
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation fullfillReservation(Long id){
            try {
                Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("reservation not found"));
                reservation.setStatus(Status.Fullfilled);

                String message = "Knjiga '" + reservation.getBook().getTitle() + "' je sada dostupna za posudbu";
                notificationService.sendNotification(reservation.getMember(), message);


                return reservationRepository.save(reservation);
            } catch (Exception e) {
                System.err.println("Greška prilikom izvršenja rezervacije: " + e.getMessage());
                throw e;
            }
        }


}
