package com.example.library.model;

import com.example.library.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="member_id")
    private Member member;
    private String bookTitle;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)  // cant edit column after
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name ="book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Pending;
}
