package org.echocat.kata.java.part1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import net.jcip.annotations.Immutable;
import org.echocat.kata.java.part1.model.Magazine.Builder;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.echocat.repo4j.util.OptionalUtils.ofNonnull;
import static org.echocat.repo4j.util.OptionalUtils.valueOf;

@Immutable
@JsonDeserialize(builder = Builder.class)
public class Magazine extends Publication {

    @Nonnull
    public static Builder magazine() {
        return new Builder();
    }

    @Nonnull
    private final LocalDate publishedAt;

    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    protected Magazine(@Nonnull Builder builder) {
        super(Magazine.class, builder);
        publishedAt = valueOf(builder.publishedAt, "publishedAt");
    }

    @Nonnull
    @JsonProperty("publishedAt")
    public LocalDate publishedAt() {
        return publishedAt;
    }

    @JsonPOJOBuilder
    public static class Builder extends Publication.Builder<Builder> {

        @Nonnull
        private Optional<LocalDate> publishedAt = empty();

        @Nonnull
        public Builder with(@Nonnull Magazine base) {
            return super.with(base)
                .withPublishedAt(base.publishedAt())
                ;
        }

        @JsonProperty(value = "publishedAt", required = true)
        @Nonnull
        public Builder withPublishedAt(@Nonnull LocalDate value) {
            publishedAt = ofNonnull(value, "publishedAt");
            return thisInstance();
        }

        @Nonnull
        @Override
        public Magazine build() {
            return new Magazine(this);
        }

    }

}
