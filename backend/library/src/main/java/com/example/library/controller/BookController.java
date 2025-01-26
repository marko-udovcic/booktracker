package com.example.library.controller;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.data.domain.Page;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    /*
    @GetMapping
    public Page<Book> getBooks(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               @RequestParam(name = "SortBy", defaultValue = "title") String sortBy,
                               @RequestParam(name = "SortOrder", defaultValue = "asc") String sortOrder
    ) {

        return bookService.getAll(page, size, sortBy, sortOrder);
    }*/
    @GetMapping
    public Page<Book> searchBooks(@RequestParam Map<String, String> params) {
        return bookService.searchBooks(params);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long bookId, @RequestBody Book updatedBook) {
            Book updated = bookService.updateBook(bookId, updatedBook);
            return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
