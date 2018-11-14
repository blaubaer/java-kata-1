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

public class AuthorQuery implements Query<Author>, Predicate<Author> {

    @Nonnull
    public static AuthorQuery authorQuery() {
        return new AuthorQuery();
    }

    @Nonnull
    private Optional<Set<EmailAddress>> emailAddresses = empty();

    @Nonnull
    public AuthorQuery withEmailAddresses(@Nullable EmailAddress ... values) {
        emailAddresses = ofNullable(values).map(Set::of);
        return this;
    }

    @Nonnull
    public AuthorQuery withEmailAddresses(@Nullable Collection<EmailAddress> values) {
        emailAddresses = ofNullable(values).map(Set::copyOf);
        return this;
    }

    @Nonnull
    public Optional<Set<EmailAddress>> emailAddresses() {
        return emailAddresses;
    }

    @Override
    public boolean test(@Nullable Author candidate) {
        return candidate != null
            && testEmailAddresses(candidate)
            ;
    }

    protected boolean testEmailAddresses(@Nonnull Author candidate) {
        return emailAddresses()
            .map(values -> values.contains(candidate.emailAddress()))
            .orElse(true)
            ;
    }
}
