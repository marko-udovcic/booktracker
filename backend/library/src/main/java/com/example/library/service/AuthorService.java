package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.specification.GenericSpecification;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }

    private Sort getSort(Map<String,String> params){
        String sortBy = params.getOrDefault("sortBy","name");
        String sortOrder = params.getOrDefault("sortOrder","asc");

        return sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }

    public Page<Author> searchAuthors(Map<String, String> params){
        int pageSize = Integer.parseInt(params.getOrDefault("size","10"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pageNumber","0"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(params));

        Specification<Author> spec = GenericSpecification.dynamicSearch(params,Author.class);

        Page<Author> authorsPage = authorRepository.findAll(spec,pageable);

        if (authorsPage.hasContent()) {
            return authorsPage;
        }
        return null;
    }



}
