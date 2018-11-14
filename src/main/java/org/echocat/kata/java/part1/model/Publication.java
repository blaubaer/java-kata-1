package org.echocat.kata.java.part1.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.jcip.annotations.Immutable;
import org.echocat.repo4j.entity.Entity;
import org.echocat.repo4j.entity.IdentifiedEntity;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static java.util.Optional.empty;
import static org.echocat.repo4j.util.OptionalUtils.ofNonnull;
import static org.echocat.repo4j.util.OptionalUtils.valueOf;

@Immutable
@JsonAutoDetect(
    fieldVisibility = NONE,
    setterVisibility = NONE,
    getterVisibility = NONE,
    isGetterVisibility = NONE,
    creatorVisibility = NONE
)
public class Publication extends IdentifiedEntity.Base<Isbn> {

    @Nonnull
    private final String title;
    @Nonnull
    private final Set<EmailAddress> authorEmails;

    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    protected Publication(@Nonnull Class<? extends Publication> baseType, Builder<?> builder) {
        super(baseType, valueOf(builder.isbn, "isbn"));
        title = valueOf(builder.title, "title");
        authorEmails = Set.copyOf(builder.authorEmails);
    }

    @JsonProperty("isbn")
    @Nonnull
    public Isbn isbn() {
        return id();
    }

    @JsonProperty("title")
    @Nonnull
    public String title() {
        return title;
    }

    @JsonProperty("authors")
    @Nonnull
    public Set<EmailAddress> authorEmails() {
        return authorEmails;
    }

    protected abstract static class Builder<B extends Builder<B>> implements Entity.Builder<Publication, B> {

        @Nonnull
        private Optional<Isbn> isbn = empty();
        @Nonnull
        private Optional<String> title = empty();
        @Nonnull
        private Set<EmailAddress> authorEmails = Set.of();

        @Nonnull
        @Override
        public B with(@Nonnull Publication base) {
            return withIsbn(base.isbn())
                .withTitle(base.title())
                .withAuthorEmails(base.authorEmails())
                ;
        }

        @Nonnull
        @JsonProperty(value = "isbn", required = true)
        public B withIsbn(@Nonnull Isbn value) {
            isbn = ofNonnull(value, "value");
            return thisInstance();
        }

        @Nonnull
        @JsonProperty(value = "title", required = true)
        public B withTitle(@Nonnull String value) {
            title = ofNonnull(value, "title");
            return thisInstance();
        }

        @JsonProperty(value = "authors", required = true)
        @Nonnull
        public B withAuthorEmails(@Nonnull Collection<EmailAddress> values) {
            authorEmails = Set.copyOf(values);
            return thisInstance();
        }

        @Nonnull
        protected B thisInstance() {
            //noinspection unchecked
            return (B) this;
        }

    }

}
