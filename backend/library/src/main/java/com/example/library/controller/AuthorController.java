package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {

        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @GetMapping
    public Page<Author> searchAuthors(@RequestParam Map<String, String> params) {
        return authorService.searchAuthors(params);
    }
}
