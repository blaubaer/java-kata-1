package org.echocat.kata.java.part1.model;

import org.echocat.repo4j.matching.Query;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class PublicationQuery implements Query<Publication>, Predicate<Publication> {

    @Nonnull
    public static PublicationQuery publicationQuery() {
        return new PublicationQuery();
    }

    @Nonnull
    private Optional<Set<Isbn>> isbns = empty();
    @Nonnull
    private Optional<Set<EmailAddress>> authorEmailAddresses = empty();

    @Nonnull
    public PublicationQuery withIsbns(@Nullable Isbn... values) {
        isbns = ofNullable(values).map(Set::of);
        return this;
    }

    @Nonnull
    public PublicationQuery withIsbns(@Nullable Collection<Isbn> values) {
        isbns = ofNullable(values).map(Set::copyOf);
        return this;
    }

    @Nonnull
    public PublicationQuery withAuthorEmailAddresses(@Nullable EmailAddress... values) {
        authorEmailAddresses = ofNullable(values).map(Set::of);
        return this;
    }

    @Nonnull
    public PublicationQuery withAuthorEmailAddresses(@Nullable Collection<EmailAddress> values) {
        authorEmailAddresses = ofNullable(values).map(Set::copyOf);
        return this;
    }

    @Nonnull
    public Optional<Set<Isbn>> isbns() {
        return isbns;
    }

    @Nonnull
    public Optional<Set<EmailAddress>> authorEmailAddresses() {
        return authorEmailAddresses;
    }

    @Override
    public boolean test(@Nullable Publication candidate) {
        return candidate != null
            && testIsbns(candidate)
            && testAuthorEmailAddresses(candidate)
            ;
    }

    protected boolean testIsbns(@Nonnull Publication candidate) {
        return isbns()
            .map(values -> values.contains(candidate.isbn()))
            .orElse(true);
    }

    protected boolean testAuthorEmailAddresses(@Nonnull Publication candidate) {
        return authorEmailAddresses()
            .map(values -> values.stream()
                .anyMatch(value -> candidate.authorEmails().contains(value))
            )
            .orElse(true);
    }

}
