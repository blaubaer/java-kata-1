package org.echocat.kata.java.part1.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import net.jcip.annotations.Immutable;
import org.echocat.kata.java.part1.model.Author.Builder;
import org.echocat.repo4j.entity.Entity;
import org.echocat.repo4j.entity.IdentifiedEntity;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static java.util.Optional.empty;
import static org.echocat.repo4j.util.OptionalUtils.ofNonnull;
import static org.echocat.repo4j.util.OptionalUtils.valueOf;

@Immutable
@JsonDeserialize(builder = Builder.class)
@JsonAutoDetect(
    fieldVisibility = NONE,
    setterVisibility = NONE,
    getterVisibility = NONE,
    isGetterVisibility = NONE,
    creatorVisibility = NONE
)
public class Author extends IdentifiedEntity.Base<EmailAddress> {

    @Nonnull
    public static Builder author() {
        return new Builder();
    }

    @Nonnull
    private final String firstName;
    @Nonnull
    private final String lastName;

    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    protected Author(@Nonnull Builder builder) {
        super(Author.class, valueOf(builder.emailAddress, "emailAddress"));
        firstName = valueOf(builder.firstName, "firstName");
        lastName = valueOf(builder.lastName, "firstName");
    }

    @JsonProperty("email")
    @Nonnull
    public EmailAddress emailAddress() {
        return id();
    }

    @JsonProperty("firstName")
    @Nonnull
    public String firstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    @Nonnull
    public String lastName() {
        return lastName;
    }

    @JsonPOJOBuilder
    public static class Builder implements Entity.Builder<Author, Builder> {

        private Optional<EmailAddress> emailAddress = empty();
        private Optional<String> firstName = empty();
        private Optional<String> lastName = empty();

        @Nonnull
        @Override
        public Builder with(@Nonnull Author base) {
            return withEmailAddress(base.emailAddress())
                .withFirstName(base.firstName())
                .withLastName(base.lastName())
                ;
        }

        @Nonnull
        @JsonProperty(value = "email", required = true)
        public Builder withEmailAddress(@Nonnull EmailAddress input) {
            emailAddress = ofNonnull(input, "emailAddress");
            return this;
        }

        @Nonnull
        @JsonProperty(value = "firstname", required = true)
        public Builder withFirstName(@Nonnull String input) {
            firstName = ofNonnull(input, "firstName");
            return this;
        }

        @Nonnull
        @JsonProperty(value = "lastname", required = true)
        public Builder withLastName(@Nonnull String input) {
            lastName = ofNonnull(input, "lastName");
            return this;
        }

        @Nonnull
        @Override
        public Author build() {
            return new Author(this);
        }

    }

}
