package com.example.library.service;

import com.example.library.exceptions.BookNotFoundException;
import com.example.library.exceptions.InvalidRequestException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.specification.GenericSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BookService {
    private final BookRepository bookRepository;

    private Sort getSort(Map<String, String> params) {
        String sortBy = params.getOrDefault("sortBy", "title");
        String sortOrder = params.getOrDefault("sortOrder", "asc");

        return sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }


    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException("Book with id: " + bookId + " not found"));
    }

    public Book updateBook(Long bookId, Book updatedBook) {

        if(updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()){
            throw new InvalidRequestException("Book title cannot be empty");
        }

        return bookRepository.findById(bookId)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setPublicationYear(updatedBook.getPublicationYear());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new BookNotFoundException("Book" + bookId + " not found"));
    }

    /*
    public Page<Book> getAll(int page, int size, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sortByAndOrder);
        Page<Book> booksPage = bookRepository.findAll(pageable);

        if (booksPage.hasContent()) {
            return booksPage;
        }
        return null;
    }
    */
    public Page<Book> searchBooks(Map<String, String> params) {
        int pageSize = Integer.parseInt(params.getOrDefault("size", "10"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pageNumber", "0"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(params));
        Specification<Book> spec = GenericSpecification.dynamicSearch(params, Book.class);

        Page<Book> booksPage = bookRepository.findAll(spec, pageable);

        if (booksPage.hasContent()) {
            return booksPage;
        }
        return null;
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).
                orElseThrow(() -> new BookNotFoundException("Book with id: " + bookId + " not found"));
        bookRepository.delete(book);
    }
}
