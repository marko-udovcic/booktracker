package com.example.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "publication_year")
    private int publicationYear;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = true)
    private Author author;
    private Long quantity;
}
