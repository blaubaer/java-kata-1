package org.echocat.kata.java.part1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import net.jcip.annotations.Immutable;
import org.echocat.kata.java.part1.model.Book.Builder;
import org.echocat.repo4j.util.OptionalUtils;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.echocat.repo4j.util.OptionalUtils.valueOf;

@Immutable
@JsonDeserialize(builder = Builder.class)
public class Book extends Publication {

    @Nonnull
    public static Builder book() {
        return new Builder();
    }

    @Nonnull
    private final String description;

    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    protected Book(@Nonnull Builder builder) {
        super(Book.class, builder);
        description = valueOf(builder.description, "description");
    }

    @Nonnull
    @JsonProperty("description")
    public String description() {
        return description;
    }

    @JsonPOJOBuilder
    public static class Builder extends Publication.Builder<Builder> {

        @Nonnull
        private Optional<String> description = empty();

        @Nonnull
        public Builder with(@Nonnull Book base) {
            return super.with(base)
                .withDescription(base.description())
                ;
        }

        @Nonnull
        @JsonProperty(value = "description", required = true)
        public Builder withDescription(@Nonnull String value) {
            description = OptionalUtils.ofNonnull(value, "description");
            return thisInstance();
        }

        @Nonnull
        @Override
        public Book build() {
            return new Book(this);
        }

    }

}
