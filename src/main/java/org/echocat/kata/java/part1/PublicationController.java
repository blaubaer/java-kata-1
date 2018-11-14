package org.echocat.kata.java.part1;

import org.echocat.kata.java.part1.model.Isbn;
import org.echocat.kata.java.part1.model.Publication;
import org.echocat.kata.java.part1.model.PublicationRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static org.echocat.kata.java.part1.model.PublicationQuery.publicationQuery;

@RestController
@RequestMapping("/publications")
public class PublicationController {

    @Nonnull
    private final PublicationRepository publicationRepository;

    public PublicationController(
        @Nonnull PublicationRepository publicationRepository
    ) {
        this.publicationRepository = publicationRepository;
    }

    @RequestMapping("/")
    public Stream<? extends Publication> finalAllPublications() {
        return publicationRepository.findBy(publicationQuery());
    }

    @RequestMapping("/{isbn}")
    public Publication findPublicationByItsIsbn(@PathVariable("isbn") @Nonnull Isbn isbn) {
        return publicationRepository.getOneBy(publicationQuery()
            .withIsbns(isbn)
        );
    }

    @RequestMapping("/sorted")
    public Stream<? extends Publication> findAllPublicationsSortedByTitle() {
        return publicationRepository.findBy(publicationQuery())
            .sorted(comparing(Publication::title));
    }

}
