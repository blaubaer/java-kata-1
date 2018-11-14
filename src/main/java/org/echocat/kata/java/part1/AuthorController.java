package org.echocat.kata.java.part1;

import org.echocat.kata.java.part1.model.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static org.echocat.kata.java.part1.model.AuthorQuery.authorQuery;
import static org.echocat.kata.java.part1.model.PublicationQuery.publicationQuery;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Nonnull
    private final AuthorRepository authorRepository;
    @Nonnull
    private final PublicationRepository publicationRepository;

    public AuthorController(
        @Nonnull AuthorRepository authorRepository,
        @Nonnull PublicationRepository publicationRepository
    ) {
        this.authorRepository = authorRepository;
        this.publicationRepository = publicationRepository;
    }

    @RequestMapping("/")
    public Stream<? extends Author> finalAllAuthors() {
        return authorRepository.findBy(authorQuery());
    }

    @RequestMapping("/{email}")
    public Stream<? extends Author> finalAuthorByEmail(@PathVariable("email") @Nonnull EmailAddress authorEmailAddress) {
        return authorRepository.findBy(authorQuery()
            .withEmailAddresses(authorEmailAddress)
        );
    }

    @RequestMapping("/{email}/publications")
    public Stream<? extends Publication> findAllPublicationsOfAuthor(@PathVariable("email") @Nonnull EmailAddress authorEmailAddress) {
        return publicationRepository.findBy(publicationQuery()
            .withAuthorEmailAddresses(authorEmailAddress)
        );
    }

}
